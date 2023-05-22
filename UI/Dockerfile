#base 이미지를 지정하기
FROM openjdk:11-jre

#이미지를 만들 때
#실제 프로젝트 jar 파일을 이미지내 파일로 복사하기
COPY build/libs/*.jar app.jar

#이미지가 컨테이너로 실행될 때 수행할 java -jar app.jar 명령어 설정하기
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=server", "app.jar"]

#jar 파일 생성하기
#./gradelw clean build

#dockerimage 만들기 해당 이미지에 jar파일이 포함되어 있다.
#docker build -t hwangdy/ui:07 .

#도커 허브에 이미지 올리기
#docker push hwangdy/ui:01

#도커 허브에서 이미지 가져오기
#sudo docker image pull hwangdy/ui:07

#해당 이미지를 실행하기
#굳이 다운 받지 않고 해당 run하면 레포지토리에서 다운한다.
# docker run --name ui -p 8081:8081 hwangdy/ui:07