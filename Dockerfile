FROM  openjdk:17-jdk-alpine

WORKDIR app

COPY target/aws-deploy.jar aws-deploy.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "aws-deploy.jar"]