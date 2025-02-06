pipeline {
    agent any
    environment {
        AWS_ECS_CREDENTIALS = "aws-ecs-credentials"
        AWS_DOCKER_IMAGE_URL = "905418443792.dkr.ecr.us-east-1.amazonaws.com/spring-sample-app"
        AWS_ECS_CUSTER = "sample_cluster"
        AWS_ECS_TASK_DEF = "sample-app-service-task-def"
        AWS_ECS_SERVICE = "sample-app-service"
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
                    sh '''
                         $(aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 905418443792.dkr.ecr.us-east-1.amazonaws.com)
                         docker build -t ${AWS_DOCKER_IMAGE_URL}:latest .
                         docker push ${AWS_DOCKER_IMAGE_URL}:latest
                    '''
                }
            }

        }
        stage("Deploy") {
            steps {
                script {
                    sh '''
                        aws ecs update-service --cluster ${AWS_ECS_CUSTER} --service ${AWS_ECS_SERVICE} --force-new-deployment
                    '''
                }
            }

        }
    }
}