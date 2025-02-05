# 모임관리 운영 서비스, 누가 왔는지 모르겠다면! 왔슈? (Whatssue)
왓슈는 출석, 일정 관리, 공지 기능 및 커뮤니티 생성까지 **소모임 관리의 편의성을 제공**하는 서비스입니다. 

왓슈_버전업 및 MVP 코드 개선을 위한 레포지터리입니다.


## 프로젝트 소개 
(아래 이미지를 클릭하면 상세 소개 링크로 이동합니다.) 
<a href =https://notefolio.net/kms02336047/360251>
<img width="919" alt="image" src="https://github.com/Onion-City/Whatssue_BE_v2/assets/79689822/78e8f5e3-f2cb-4e41-a775-e4469ae20209"> 
</a>

--- 

## 📌 주요 기능 

### 초대 기능 
- 소모임 관리자는 초대 코드를 생성합니다.
- 소모임의 공개 비공개 설정을 할 수 있습니다. (비공개 상태인 경우, 초대 코드를 통해서만 입장 가능합니다.)
- 소모임 지원자는 초대 코드를 통해 입장 대기를 할 수 있습니다.
- 공개 설정 된 소모임의 관리자는 가입 대기자를 거절 및 수락 할 수 있습니다.

### ✔️ 출석 관리 기능
- 관리자는 스케줄별 출석을 진행할 수 있습니다. (출석 열기, 출석 닫기, 출석 재진행, 출석 삭제, 수정)
- 멤버는 '출석 열기'가 진행된 스케줄의 출석을 진행합니다. 이때 출석은 세자리의 숫자를 입력하여 진행합니다. 
- 관리자는 멤버로부터 공결 신청을 받아 공결처리가 가능합니다. 
- 기간별, 출석 종류(공결,출석,결석)에 따른 출석 현황 필터링 기능을 제공합니다. 

### ⏰ 일정 관리 기능 
- 캘린더를 통해 소모임의 날짜별 일정을 등록합니다. 
- 일정 목록 리스트로 필터링하여 조회할 수 있습니다. 

### 📝 게시판 기능 
- Admin 계정의 공지용 게시판과 일반 멤버의 소통을 위한 게시판이 분리되어 공지가 섞이지 않습니다. 
- 게시물에 대한 좋아요를 할 수 있습니다. 
- 최신순으로 댓글, 대댓글 기능을 제공합니다. 
- 내가 작성한 게시글 및 댓글을 확인할 수 있습니다. 

### 👨‍👨‍👦‍👦 멤버 관리 기능 
- 전화 번호 문자 인증을 통한 멤버 인증을 진행합니다. 
- 관리자는 소모임에 적합한 형태로 멤버 프로필 설정 기능을 제한하여 소모임별 맞춤형 프로필을 제작할 수 있습니다. 
- 멤버의 프로필 조회 권한을 설정을 할 수 있습니다. 
- 멤버 추방 탈퇴가 가능합니다.

### 권한 분리 
- 유저, 멤버, 매니저 Role에 따른 api 호출 권한을 부여합니다. 


### 🔍배포 url (현재 해당 url은 내려간 상태입니다.)
https://whatssue.app

### Infra Structure 
<img width="666" alt="Image" src="https://github.com/user-attachments/assets/f3c120cf-2a8e-4735-bda6-e64d0d6ba3cd" />

### ⌛개발 기간
- 2023.09 : <a href=https://github.com/Onion-City/Whatssue-BE_v1/tree/develop> MVP 단기 개발</a> 
- 2024.01 ~ 08 : 기능 확장 및 Ver2 개발 

---

### 🔧기술 스택
#### 🖥️Front
- HTML
- CSS
- TypeScript
- React
#### 🖥️ Backend
- Java
- Spring Boot
- JPA
- MySql
- Redis
- S3
- Kakao OAuth
- Spring Security

### Infra 
- AWS (EC2, LB, AutoScailing)
- Docker
- GithubActions
- Nginx 

---

###  기획 및 설계
- [유저 플로우](https://app.eraser.io/workspace/HQCZcSFCKQBPPZ1dQqqv?origin=share)
- [ERD설계](https://www.erdcloud.com/d/AWGmk3K42vFRxkEE3)
- [UI 설계](https://www.figma.com/file/dmcv7zXS4A2nTHWgYsZlkd/%EC%99%93%EC%8A%88UI?type=design&node-id=0%3A1&mode=design&t=lf8Yx4lyhPAW1gIv-1)

---

### 👩‍💻 Whatssue Team
- Product Manager : [@Ji-minhyeok](https://github.com/Ji-minhyeok)
- Team Leader : [@pinetree2](https://github.com/pinetree2)
- Designer : [@kms0233](https://github.com/kms0233)
- FrontEnd Developer : [@hhbb0081](https://github.com/hhbb0081)
- BackEnd Developer : [@pinetree2](https://github.com/pinetree2), [@kjyyjk](https://github.com/kjyyjk), [@dPwls0125](https://github.com/dPwls0125) ,[@Ji-minhyeok](https://github.com/Ji-minhyeok)



