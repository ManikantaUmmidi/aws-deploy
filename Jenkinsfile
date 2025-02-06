pipeline{

    agent any
        environment {

            AWS_ECS_CUSTER = "sample_cluster"
            AWS_ECS_SERVICE = "sample-app-service"
            AWS_REGISTRY_CREDENTIALS = "ecr:us-east-1:aws-ecs-credentials"
            AWS_DOCKER_IMAGE_URL = "905418443792.dkr.ecr.us-east-1.amazonaws.com/spring-sample-app"
            AWS_APP_REGISTRY = "https://905418443792.dkr.ecr.us-east-1.amazonaws.com"

        }

        stages {
            stage("Checkout") {
                steps {
                    git branch: 'main', url: 'https://github.com/ManikantaUmmidi/aws-deploy.git'
                }
            }
            stage("Build Code") {
                steps {
                    sh 'mvn clean install'
                }
             }

             stage("Build Docker") {
                 steps {
                     script {
                        dockerImage = docker.build(AWS_DOCKER_IMAGE_URL + ":$BUILD_NUMBER", "./")
                     }
                 }
             }

            stage("Upload to registry") {
                steps {
                    script {
                        docker.withRegistry(AWS_APP_REGISTRY, AWS_REGISTRY_CREDENTIALS) {
                            dockerImage.push("$BUILD_NUMBER")
                            dockerImage.push("latest")
                        }
                    }
                }
            }

            stage("Upload to registry") {
                steps {
                   withAWS(credentials: 'aws-ecs-credentials', region: 'us-east-1') {
                        sh "aws ecs update-service --cluster ${AWS_ECS_CUSTER} --service ${AWS_ECS_SERVICE} --force-new-deployment"
                        }
                    }
                }
        }

}