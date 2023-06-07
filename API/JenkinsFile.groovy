pipeline {
    agent any
    environment {
        ubuntuId = credentials('ubuntu_id')
        ubuntuPass = credentials('ubuntu_pass')
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
            post {
                success {
                    echo 'Successfully Cloned Repository'
                }
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }

        // gradle build
        stage('Bulid Gradle') {
            agent any
            steps {
                echo 'Bulid Gradle'
                //이부분
                dir ('../API/API') {
                    bat 'gradlew clean build'
                }
            }
            post {
                success {
                    echo "SuccessfulAZly Build gradle"
                }
                failure {
                    echo "Fail to build gradle"
                    error 'This pipeline stops here...'
                }
            }
        }

        // test
        stage('JUnit Test'){
            steps{
                echo 'Test Result'
                junit '**/build/test-results/test/*.xml'
            }
        }

        // docker build
        stage('Bulid Docker') {
            agent any
            steps {
                echo 'Bulid Docker'
                //이부분
                dir ('../API/API') {
                    script {
                        dockerImage = docker.build 'hwangdy/api:latest'
                    }
                }
            }
            post {
                failure {
                    error 'This pipeline stops here...'
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
            post {
                failure {
                    error 'This pipeline stops here...'
                }
            }
        }
//
//        // shh - deploy
//        stage('SSH') {
//            steps {
//                script {
//                    def remote = [:]
//                    remote.name = 'test'
//                    remote.host = '127.0.0.5'
//                    remote.user = ubuntuId
//                    remote.password = ubuntuPass
//                    remote.allowAnyHosts = true
//                    stage('Remote SSH') {
//                        sshCommand remote: remote, command: "bash jenkins.sh"
//                    }
//                }
//            }
//        }
    }
}