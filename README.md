
## 무식한 영어 서비스-소개
 
![서비스](https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/a06b047c-0c0f-4d78-86dc-a7acf3775a2f)

## ERD
![erd](https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/72a8ab87-ccfe-44de-8dbe-0be2b920bb9c)
## 개발 환경
- Java11
- SpringBoot, JPA
- MySQL
- HTML, CSS, Bootstrap, JavaScript(axios)
- Redis, ELK Stack, Kafka

## 서비스 기능
### 사용자 기능
- **회원 가입과 이메일 인증**

회원 가입에는 일반 회원 가입과 Google OAuth2 회원 가입을 제공합니다.  
가입한 이메일을 통해 이메일 인증 절차를 진행합니다.

![회원가입](https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/5a61f608-9369-4270-8c8f-aba5a00abf8a)

- **영어 문장 테스트**

문법과 상황에 맞는 영어 문장을 테스트할 수 있고, 테스트 결과는 저장되어 틀린 문장만 따로 테스트가 가능합니다.


![테스트](https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/2e88c401-8b9d-45dc-bca9-8a21fbd4fccc)

- **사용자 문장 구독**

사용자는 자신의 문장 그룹을 생성할 수 있고, 다른 사용자와 공유할 수 있습니다.  
구독된 문장이 업데이트되면 구독자에게 알림이 갑니다.

![구독](https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/64a1933e-6143-45b4-818c-f8fdf5eec4c0)


### 관리자 기능
- **관리자 문장 관리**

사용자가 테스트할 문장을 등록, 수정, 삭제, 조회합니다.

![관리자](https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/cbe6340a-0418-4270-8659-f25bf7fb22d5)

- **사용자 테스트 결과 수집**

사용자의 테스트 기록(맞음, 틀림, 힌트 봄, 테스트 시간)을 ElasticSearch에 저장하고 조회할 수 있습니다.

<p align="left">
<img src="https://github.com/dae0hwang/Food_Ordering_Service/assets/103154389/c3f6535d-4dfd-4e1a-9b77-dca2b8651670" width="700" height="700">
</p>
