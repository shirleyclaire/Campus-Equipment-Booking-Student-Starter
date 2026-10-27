pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'Java 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Task 2: Build & Test') {
            steps {
                echo 'Running Maven Clean Test...'
                sh 'mvn clean test'
            }
        }

        stage('Task 2: Package JAR') {
            steps {
                echo 'Packaging Spring Boot Application JAR...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Task 5: Docker Build') {
            steps {
                echo 'Building Docker Image...'
                sh 'docker build -t campus-equipment-booking:set-f .'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
