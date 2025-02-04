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
                 scp -i $APP_SSH_KEY target/aws-deploy.jar ubuntu@$EC2_IP:/home/ubuntu
                '''
            }

        }

        stage("Deploy to AWS") {
            steps {
                sh '''
                    ssh -i $APP_SSH_KEY ubuntu@$EC2_IP << EOF
                    pkill -f aws-deploy.jar || true
                    nohup java -jar /home/ubuntu/aws-deploy.jar > app.log 2>&1 & EOF
                '''
            }
        }
    }
}
