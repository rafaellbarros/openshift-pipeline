#! /usr/bin/env groovy

pipeline {

  agent {
    label 'maven'
  }

  stages {
    stage('Build') {
      steps {
        echo 'Building..'
        sh 'mvn clean package'
      }
    }
    stage('Create Container Image') {
      steps {
        echo 'Create Container Image..'
        script {
          openshift.withCluster() {
            openshift.withProject("dev-openshift") {
                def buildConfigExists = openshift.selector("bc", "openshift-pipeline").exists()

                if(!buildConfigExists){
                    openshift.newBuild("--name=openshift-pipeline", "--docker-image=registry.access.redhat.com/ubi8/openjdk-11", "--binary")
                }

                openshift.selector("bc", "openshift-pipeline").startBuild("--from-file=target/openshift-pipeline-0.0.1-SNAPSHOT.jar", "--follow")
            }
          }
        }
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying....'
        script {
          openshift.withCluster() { 
            openshift.withProject("dev-openshift") { 
              def deployment = openshift.selector("dc", "openshift-pipeline") 
              
              if(!deployment.exists()){ 
                openshift.newApp('openshift-pipeline', "--as-deployment-config").narrow('svc').expose() 
              } 
              
              timeout(5) { 
                openshift.selector("dc", "openshift-pipeline").related('pods').untilEach(1) { 
                  return (it.object().status.phase == "Running") 
                } 
              } 
            } 
          }
        }
      }
    }
  }
}