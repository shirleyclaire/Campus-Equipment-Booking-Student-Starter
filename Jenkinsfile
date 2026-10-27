pipeline {
    agent any

    stages {
        stage('Task 2: Build & Test') {
            steps {
                echo 'Running Maven Clean Test via Wrapper...'
                sh 'chmod +x mvnw'
                sh './mvnw clean test'
            }
        }

        stage('Task 2: Package JAR') {
            steps {
                echo 'Packaging Application JAR via Wrapper...'
                sh './mvnw clean package -DskipTests'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
