pipeline {
    agent any

    tools {
        maven 'Maven 3'
    }

    stages {
        stage('Task 2: Build & Test') {
            steps {
                echo 'Running Maven Clean Test...'
                sh 'mvn clean test'
            }
        }

        stage('Task 2: Package JAR') {
            steps {
                echo 'Packaging Application JAR...'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
    }
}
