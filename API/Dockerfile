#base 이미지를 지정하기
FROM openjdk:11-jre

#해당 dockerfile위치의 build/libs/.jar 파일을 컨테이너 기본위치 app.jar 파일로 카피하기
COPY build/libs/*.jar app.jar

#docker 파일 실행되면 작동할 java -jar app.jar 명령어 설정하기
#이미지 파일->컨테이너 시작되면 시작되도록 이미지 파일에 이 명령어가 저장됨
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=server", "app.jar"]

#jar 파일 생성하기
#./gradelw clean build

#dockerimage 만들기 해당 이미지에 jar파일이 포함되어 있다.
#docker build -t hwangdy/api:01 .

#도커 허브에 이미지 올리기
#docker push hwangdy/api:01

#도커 허브에서 이미지 가져오기
#sudo docker image pull hwangdy/api:01

#해당 이미지를 실행하기
#굳이 다운 받지 않고 해당 run하면 레포지토리에서 다운한다.
# docker run --name {컨테이너명} -p 8080:8080 {이미지명}
# docker run --name api -p 5511:5511 hwangdy/api:01