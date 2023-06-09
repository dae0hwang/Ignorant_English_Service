pipeline {
    agent any
    environment {
        registryCredential = 'DockerHub_IdPwd'
        dockerImage = ''
    }

    stages {
        // git에서 repository clone
        stage('Prepare') {
            steps {
                echo 'Clonning Repository'
                git url: 'https://github.com/dae0hwang/Ignorant_English_Service',
                        branch: 'master'
            }
        }

        // gradle build
        stage('Bulid Gradle') {
            agent any
            steps {
                echo 'Bulid Gradle'
                //이부분
                dir ('../aws-api/API') {
                    bat 'gradlew clean build'
                }
            }
        }
        // docker build
        stage('Bulid Docker') {
            agent any
            steps {
                echo 'Bulid Docker'
                //이부분
                dir ('../aws-api/API') {
                    script {
                        dockerImage = docker.build 'hwangdy/api:latest'
                    }
                }
            }
        }
        // docker push
        stage('Push Docker') {
            steps {
                echo 'Push Docker'
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}