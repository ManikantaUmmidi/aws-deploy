pipeline {
    agent any
    environment {
        AWS_KEY = "${aws_key}"
        AWS_SECRET = "${aws_secret}"
        EC2_IP = "54.221.63.185"
        APP_SSH_KEY = "/var/lib/jenkins/my-keypair.pem"
    }

    stages {
        stage("Code clone") {
            steps {
                git branch: 'main', url: 'https://github.com/ManikantaUmmidi/aws-deploy.git'
            }
        }

        stage("Build With Maven") {
            steps {
                sh 'mvn clean package'
            }

        }

        stage("Copy jar from jenkins") {

            steps {
                sh '''
                 scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -i /var/lib/jenkins/my-keypair.pem target/aws-deploy.jar ubuntu@$EC2_IP:/var/lib/jenkins

                '''
            }

        }

        stage("Deploy to AWS") {
            steps {
                sh '''
                    ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -i $APP_SSH_KEY ubuntu@$EC2_IP
                    nohup java -jar /var/lib/jenkins/aws-deploy.jar > app.log
                '''
            }
        }
    }
}
