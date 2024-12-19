# 💊 Project Dose

> 복용 알림을 등록해 잊지말고 약을 제 때 챙겨드세요 👉 [[링크]](http://ormi-donkey.com/)


## 📖Description

- 복용할 약을 언제 먹어야 하는지 등록하고 PUSH 알림을 받으세요
- 복용에 대한 통계를 제공받고 필요하면 PDF로 출력하세요
- 이것 저것 함께 먹어도 될지 궁금하신가요? 복용 방법을 앨런 AI를 통해 알아보세요


### 1. 💾**개발 환경**
## Tech Stack

![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)

### Backend
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### Frontend
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![BootStrap](https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

### Infrastructure
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazon-ec2&logoColor=white)
![AWS S3](https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white)
![AWS RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Firebase](https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white)

<br>

### 2. 🤔**기능 명세서**

![img.png](readme/mindmap.png)

### 3. 🔎**개발 일정**

![img.png](readme/DevelopmentSchedule.png)

## ✨UI(화면) 설계서

- [피그마 링크 바로가기]([https://www.figma.com/design/MHo4sYaluH3V8VyPcu7vIo/Project_Dose?node-id=0-1&t=b6YrEvOq5R2sPcTE-1])

|                                                                                                                       |                                                                                                                       |
|-----------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------|
| Main Page (Member)                                                                                                    | Main Page (Guest)                                                                                                     |
| <img src="" width="370"> | <img src="" width="370"> |
| Login Page                                                                                                            | SignUp Page                                                                                                           |
|                                                                                                                       |                                                                                                                       |

## 📂Project Structure
```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂estsoft
 ┃ ┃ ┃ ┃ ┗ 📂projectdose
 ┃ ┃ ┃ ┃ ┃ ┣ 📂admin
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┣ 📂calendar
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┣ 📂fcm
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂impl
 ┃ ┃ ┃ ┃ ┃ ┣ 📂report
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┣ 📂users
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┗ 📂resources
 ┃ ┃ ┣ 📂firebase
 ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┣ 📂firebase
 ┃ ┃ ┃ ┣ 📂fonts
 ┃ ┃ ┃ ┣ 📂img
 ┃ ┃ ┃ ┗ 📂js
 ┃ ┃ ┣ 📂templates
 ┃ ┃ ┃ ┗ 📂common
 ┗ 📂test
```

## 🏭System Structure
![img.png](src/main/resources/static/img/Architecture.png)


## 🔐ERD Structure
- [ERDCloud]([https://www.erdcloud.com/d/s9NpRt9zbDWTytzFL])

![img.png](src/main/resources/static/img/ERD.png)

## 🎈API 명세서

### 📁 Signup
| 🏷NAME         | ⚙METHOD | 📎URL                         | 📖DESCRIPTION              |
|---------------------|---------|-------------------------------|--------------------------|
| signUp                 | POST | /api/auth/signup               | 회원 가입               |
| checkEmailDuplicate    | GET  | /api/auth/checkEmailDuplicate  | 이메일 중복 체크         |
| checkNicknameDuplicate | GET  | /api/auth/checkNicknameDuplicate| 닉네임 중복 체크       |

### 📁 Login
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION |
|---------------------|---------|-------------------------------|---------------|
| login               | POST    | /api/auth/login               | 로그인          |
| logout              | GET     | /api/auth/logout              | 로그아웃         |
| findPassword        | POST    | /api/auth/findPassword        | 비밀번호 찾기    |
| resetPassword       | POST    | /api/auth/resetPassword       | 비밀번호 재설정   |

### 📁 User
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| userInfoUpdate      | PUT     | /api/users/{userId}           | 사용자 정보 수정           |
| requsetUserdelete   | DELETE  | /api/users/{userId}           | 사용자 탈퇴 요청                |
| viewMypage          | GET     | /api/users/{userId}/myPage    | 마이페이지 확인                |


### 📁 Report
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| showAllReport         | GET    | /api/report                    | 복약 통계 조회         |
| makeReportImage      | GET    | /api/report/startDate={startDate}&endDate={endDate}| 복약 리포트 이미지 생성     |

### 📁 Admin
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchUser          | GET     | /api/users                    | 등록 유저 조회           |
| deleteUser          | PUT     | /api/deleteuser={userId}      | 등록 유저 삭제            |
| searchUserDetails   | PUT     | /api/users?userId={userId}     | 등록 유저 상세 조회       |

### 📁 Calander
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| createDoseInfo      | POST   | /api/schedule                 | 투약 정보 생성         |
| deleteDoseInfo      | DELETE   | /api/schedule/{scheduleId}   | 투약 정보 삭제       |
| updateDoseInfo      | PUT   | /api/schedule/{scheduleId}      | 투약 정보 수정      |
| searchDoseDetail    | GET   | /api/schedule/{scheduleId}       | 투약 상세 조회       |
| searchDoseList      | GET   | /api/scheduleList/{date}         | 투약 목록 조회       |
| updateDosageStatus    | PUT   | /api/log/{logId}               | 투여 여부 수정       |
| updateDosageAllStatus  | PUT   | /api/log                       | 투여 여부 전체 수정    |

### 📁 Notification
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| sendFcmNoti         | POST     | /api/v1/fcm/sendMessage      | FCM 알림 전송           |
| saveFcmToken        | POST     | /api/v1/device-token/register | FCM 토큰 저장           |



<br>

## 🛠Coding Convention

### 자바 컨벤션

* [네이버 코딩 컨벤션](https://www.notion.so/oreumi/202d0d0895884dd7847673fe7d40a0e0)

<br>

### 커밋 메시지 컨벤션

```
type : subject

body

footer
```

> Commit Type
>
> 타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자로 한다.

**`태그 : 제목`의 형태이며, `:`뒤에만 space가 있음에 유의한다.**

- `feat` : 새로운 기능 추가
- `fix` : 버그 수정
- `docs` : 문서 수정
- `style` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- `refactor` : 코드 리펙토링
- `test` : 테스트 코드, 리펙토링 테스트 코드 추가
- `chore` : 빌드 업무 수정, 패키지 매니저 수정

> 3. Subject
>
> - 제목은 최대 50글자가 넘지 않도록 하고 마침표 및 특수기호는 사용하지 않는다.
> - 영문으로 표기하는 경우 동사(원형)를 가장 앞에 두고 첫 글자는 대문자로 표기한다.(과거 시제를 사용하지 않는다.)
> - 제목은 **개조식 구문**으로 작성한다. --> 완전한 서술형 문장이 아니라, 간결하고 요점적인 서술을 의미.
- Fixed --> Fix
- Added --> Add
- Modified --> Modify

> 4. Body
>

본문은 다음의 규칙을 지킨다.

- 본문은 한 줄 당 72자 내로 작성한다.
- 본문 내용은 양에 구애받지 않고 최대한 상세히 작성한다.
- 본문 내용은 어떻게 변경했는지 보다 무엇을 변경했는지 또는 왜 변경했는지를 설명한다.

> 5. footer
>

꼬릿말은 다음의 규칙을 지킨다.

- 꼬리말은 `optional`이고 `이슈 트래커 ID`를 작성한다.

- 꼬리말은 `"유형: #이슈 번호"` 형식으로 사용한다.
- 여러 개의 이슈 번호를 적을 때는 `쉼표(,)`로 구분한다.
- 이슈 트래커 유형은 다음 중 하나를 사용한다.`Fixes`: 이슈 수정중 (아직 해결되지 않은 경우)`Resolves`: 이슈를 해결했을 때 사용`Ref`: 참고할 이슈가 있을 때 사용`Related to`: 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)**`ex) Fixes: #45 Related to: #34, #23`**

<br>

## PR 템플릿
```
## 🔎 작업 내용

> 기능에서 어떤 부분이 구현되었는지 설명해주세요

<br/>

## ➕ 이슈 링크

> #이슈번호

<br/>

## 이미지 첨부 (선택)

<br/>
```

## Issues 템플릿
```
## 설명
> 코드에 대한 설명

## 진행 상황
- [ ] todo1
- [ ] todo2
- [ ] todo3

## 부가 설명
> 기타
```


## 👨‍💻 팀원
- 장준혁
- 김근우
- 정윤호
- 정승원
