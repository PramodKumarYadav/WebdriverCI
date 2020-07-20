pipeline {
    agent any
    environment {
       REGISTRY = "registry.local:5000"
    }
    stages {
        stage('Verify') {
            steps {
                dir('ci') {
                    sh 'chmod +x ./00-verify.bat'
                    sh './ci/00-verify.bat'
                }
            }
        }
        stage('Build') {
            steps {
                dir('ci') {
                    sh 'chmod +x ./01-build.bat'
                    sh './ci/01-build.bat'
                }
            }
        }
        stage('Test') {
            steps {
                dir('ci') {
                    sh 'chmod +x ./02-test.bat'
                    sh './ci/02-test.bat'
                }
            }
        }
        stage('Push') {
            steps {
                dir('ci') {
                    sh 'chmod +x ./03-push.bat'
                    sh './ci/03-push.bat'
                    echo "Pushed web to http://$REGISTRY/v2/diamol/ch11-numbers-web/tags/list"
                    echo "Pushed api to http://$REGISTRY/v2/diamol/ch11-numbers-api/tags/list"
                }
            }
        }
    }
}