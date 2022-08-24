FROM quay.io/devfile/maven:3.8.1-openjdk-17-slim

WORKDIR /build

# Build dependency offline to streamline build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11-jdk
COPY --from=0 /build/target/openshift-pipeline-*.jar /app/target/app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/app/target/app.jar", "--server.port=8080" ]

