#!/bin/bash

echo "###### Start Create Pipeline OpenShift and Deploy Project Hello World! ######"
echo "###### Create a New Project pipeline-prod ######"
oc new-project pipelines-prod

echo "###### Apply Manifest Task ######"
oc create -f https://raw.githubusercontent.com/rafaellbarros/openshift-pipeline/develop/openshift/pipelines/01_apply_manifest_task.yaml

echo "###### Update Deployment Task ######"
oc create -f https://raw.githubusercontent.com/rafaellbarros/openshift-pipeline/develop/openshift/pipelines/02_update_deployment_task.yaml

echo "###### Create Pipeline Dev ######"
oc create -f https://raw.githubusercontent.com/rafaellbarros/openshift-pipeline/develop/openshift/pipelines/04_pipeline-prod.yaml

echo "###### Start Pipeline Build and Deploy ######"
sleep 3

tkn pipeline start build-and-deploy \
    -w name=shared-workspace,volumeClaimTemplateFile=https://raw.githubusercontent.com/rafaellbarros/openshift-pipeline/develop/openshift/pipelines/03_persistent_volume_claim.yaml \
    -p deployment-name=pipelines-hello-world-api \
    -p git-url=https://github.com/rafaellbarros/openshift-pipeline.git \
    -p IMAGE=image-registry.openshift-image-registry.svc:5000/pipelines-prod/pipelines-hello-world-api \
    --use-param-defaults

sleep 3

echo "###### Show Logs Pipeline ######"
tkn pipeline logs -f
echo "###### Show Route ######"
oc get route pipelines-hello-world-api --template='http://{{.spec.host}}'

echo "###### End ######"


