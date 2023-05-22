# IgnorantEnglish
무식한 영어 서비스 - 영어 통 문장을 암기하는 서비스, API 서버와 UI 서버가 동시에 들어 있는 멀티 프로젝트이다.

# 무식한 영어 통문장 암기 서비스
기획의도를 쫙~~~

# 도커등 뭐든 실행방법 쿠버네티스

# API docs
블로그하고

# 서비스 동작 구성
### 1. 관리자 문장 관리하기
서비스에 가입한 모든 유저가 테스트할 수 있는 관리자 문장(영어, 번역) 데이터를 관리합니다.  
문장을 문법, 상황 별로 추가할 수 있고, 조건에 맞는 조회와 데이터 수정 삭제 기능을 사용할 수 있습니다.
- 관리자 문장 추가하기

![ㅇ관리자문장추가](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/a85d26ec-26ec-4997-8c9a-ee9981d8349c)

- 관리자 문장 조회

관리자가 등록한 문장을 전체 조회하거나, 문법, 상황, 검색 조건 별로 조회할 수 있습니다.


![ㅇ관리자문장조회](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/36eb59c1-ca9d-496c-9df3-429a6afe2846)

- 관리자 문장 수정

![ㅇ관리자문장수정](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/66167b92-5116-4a89-b460-f57d53694f02)

- 관리자 문장 삭제

![ㅇ관리자문장삭제](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/3fcfd243-2f71-4005-9d29-8f6c95a4cf8d)

### 2. 사용자 계정 관리(이메일 인증)
- 사용자 회원 가입과 로그인

![ㅇ사용자회원가입로그인](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/45277e6d-2c94-4b78-8214-3eb157c1a3fb)

- 사용자 이메일 인증하기

![ㅇ유저이메일인증](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/2b00756b-3402-45c2-beda-c48d4e6bbab7)

### 3. 관리자 문장 통문장 암기 테스트하기
관리자 문장을 원하는 조건에 멎춰 테스트하고 그 결과를 확인할 수 있습니다.

- 관리자 문장 테스트하기

해당 번역문을 영어로 표현할 수 있다면 맞음 버튼, 없다면 틀림 버튼을 눌러 테스트를 진행합니다.  
힌트와 정답을 확인할 수 있고, 테스트 결과를 조회할 수 있습니다.

![관리자문장테스트하기](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/6096f510-78da-4eb2-8627-4dfbd5f6c065)

- 관리자 문장 조회하기(테스트 결과 포함)

![테스트결과조회](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/fffb8788-5ad6-49c7-9ab1-6bbb4e5e9b5f)

### 4. 관리자 문장 테스트 결과 ElasticSearch에 저장 후 kibana에서 확인
각 유저별로 테스트한 결과(맞음, 틀림, 힌트봄, 테스트 시간) 데이터를 로그로 남겨 EliasticSearch에 저장합니다.

- 맞음 테스트 결과 데이터

![correct](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/1af84c51-dbac-4985-b3bb-735fd4d9e0d3)

- 테스트 시간 데이터

![testTime](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/13a65e62-f749-4f05-ae01-da8b4afd43cb)

- 틀림 테스트 결과 데이터

![wrong](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/b9d603a8-fd6f-4d22-a1f5-0b7d1bfbd344)

- 힌트 확인 데이터

![hint](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/f21b177e-6b6e-4dcd-a265-8d6cac1bdb6d)

- (kibana-검색) 특정 문제 푼 시간 이상 데이터 확인하기

![특정시간이상검색조건](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/400ac22e-e16a-4848-95f4-b7c6394c1f0f)

- (kibana-검색) 특정 유저 틀린 문장만 확인하기

![특정유저틀린문제만검색](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/06ccac87-2f58-451e-9bd1-73e5a92db626)

### 5. 유저 문장 관리 및 구독 알림 서비스
관리자 문장 외에 사용자도 자신만의 문장을 등록할 수 있습니다.    
그리고 다른 유저가 등록한 문장 그룹을 구독할 수 있고 구독한 문장 그룹이 업데이트할 때 마다 알림 서비스가 동작합니다.
- 유저 문장 등록 구독하기 알림

![문장그룹구독](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/0d7116f1-0ef5-40ea-aa12-acf4997b66ba)

- 유저 문장 추가 알림

![문장추가알림](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/d1e5ed4d-a6e8-4345-9f7e-57a2b95d3516)

- 유저문장 삭제 알림

![문장삭제알림](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/92342689-fdd2-498e-8c09-1ef615d2e59d)

- 알림 확인 후 데이터 삭제

![알림 확인 삭제](https://github.com/dae0hwang/IgnorantEnglish/assets/103154389/4145422c-1723-4069-9bf4-c0de82a5a6ba)


