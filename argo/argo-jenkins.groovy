pipeline {
    agent any
    environment {
        registryCredential = 'DockerHub_IdPwd'
        apiImageName = 'hwangdy/api:'+ "${env.BUILD_NUMBER}"
        plusImageName = 'hwangdy/plus:'+ "${env.BUILD_NUMBER}"
        uiImageName = 'hwangdy/ui:'+ "${env.BUILD_NUMBER}"
        dockerImage = ''
    }
    stages {

        // git에서 repository clone
        stage('Prepare') {
            steps {
                git url: 'https://github.com/dae0hwang/Ignorant_English_Service', branch: 'master'
            }
        }

        // 1 API 서버 진행
        // gradle build
        stage('API Bulid Gradle') {
            agent any
            steps {
                echo 'Bulid Gradle'
                //이부분
                dir ('../argo-english/API') {
                    bat 'gradlew clean build'
                }
            }
        }
        // docker build
        stage('API Bulid Docker') {
            agent any
            steps {
                echo 'Bulid Docker'
                dir ('../argo-english/API') {
                    script {
                        dockerImage = docker.build "${apiImageName}"
                    }
                }
            }
        }
        // docker push
        stage('API Push Docker') {
            steps {
                echo 'Push Docker'
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
//
//        // 2 PLUS 서버 진행
//        // gradle build
//        stage('PLUS Bulid Gradle') {
//            agent any
//            steps {
//                echo 'Bulid Gradle'
//                //이부분
//                dir ('../argo-english/PLUS') {
//                    bat 'gradlew clean build'
//                }
//            }
//        }
//        // docker build
//        stage('PLUS Bulid Docker') {
//            agent any
//            steps {
//                echo 'Bulid Docker'
//                dir ('../argo-english/PLUS') {
//                    script {
//                        dockerImage = docker.build "${plusImageName}"
//                    }
//                }
//            }
//        }
//        // docker push
//        stage('PLUS Push Docker') {
//            steps {
//                echo 'Push Docker'
//                script {
//                    docker.withRegistry('', registryCredential) {
//                        dockerImage.push()
//                    }
//                }
//            }
//        }
//
//        // 3 UI 서버 진행
//        // gradle build
//        stage('UI Bulid Gradle') {
//            agent any
//            steps {
//                echo 'Bulid Gradle'
//                //이부분
//                dir ('../argo-english/UI') {
//                    bat 'gradlew clean build'
//                }
//            }
//        }
//        // docker build
//        stage('UI Bulid Docker') {
//            agent any
//            steps {
//                echo 'Bulid Docker'
//                dir ('../argo-english/UI') {
//                    script {
//                        dockerImage = docker.build "${uiImageName}"
//                    }
//                }
//            }
//        }
//        // docker push
//        stage('UI Push Docker') {
//            steps {
//                echo 'Push Docker'
//                script {
//                    docker.withRegistry('', registryCredential) {
//                        dockerImage.push()
//                    }
//                }
//            }
//        }
//
//
//
//        // git에서 가져오기
//        stage('Prepare Helm') {
//            steps {
//                dir ('../argo-english@tmp') {
//                    git url: 'https://github.com/dae0hwang/helm-chart', branch: 'master'
//                }
//            }
//        }
//        //value.yaml 파일을 변경을 해줘야한다.
//        stage('update Helm') {
//            steps {
//                dir ('../argo-english@tmp/helm-english') {
//                    script {
//                        def filename = 'values.yaml'
//                        def data = readYaml file: filename
//                        data.image.api= "${apiImageName}"
//                        data.image.ui= "${uiImageName}"
//                        data.image.plus= "${plusImageName}"
//                        writeYaml file: filename, data: data, overwrite: true
//                    }
//                }
//            }
//        }
//
//        stage('push Helm to git') {
//            steps {
//                dir ('../argo-english@tmp') {
//                    withCredentials([gitUsernamePassword(credentialsId: 'git-hub-credential', gitToolName: 'git-tool')]) {
//                        bat 'git add .'
//                        bat 'git config --global user.email "geungan9@gmail.com"'
//                        bat 'git config --global user.name "dae0hwang"'
//                        bat "git commit -m argo"
//                        bat 'git push --set-upstream origin master'
//                    }
//                }
//            }
//        }
    }
}