# 🐴 Project Dose

> 취업과 회사에 대해 자유롭게 이야기 할 수 있는 커뮤니티 👉 [[링크]](http://ormi-donkey.com/)

![img.png](readme/mainImg.png)

## 📖Description

- 누구나 쉽게 접근할 수 있는 사이트
- 자유롭게 표현할 수 있는 사이트
- 취업 정보를 제약없이 교류할 수 있는 사이트

취업 준비에 필요한 자료를 구할 수 있는 곳이 흔치 않고, 결제를 해야 자료를 열람할 수 있는 곳이 많습니다.

편하게 취준생들이 편하게 모여 이야기 하고, 취업에 성공한 선배님들과 대화하며, 누구에게나 열려있는 커뮤니티로 좋은 추억이 되었으면 좋겠습니다.



### 1. 💾**개발 환경**
## Tech Stack

![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![awssecretsmanager](https://img.shields.io/badge/awssecretsmanager-#DD344C.svg?style=for-the-badge&logo=awssecretsmanager&logoColor=white)

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

### 📁 Member
| 🏷NAME         | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| withdrawal     | PUT     | /member/withdrawal            | 회원 탈퇴 요청           |
| modify         | PUT     | /member/modifyInfo            | 회원 정보 수정           |
| signup         | POST    | /member/register              | 회원 가입                |
| findPassword   | POST    | /member/findpassword          | 비밀번호 찾기            |
| changePassword | POST    | /member/changepassword        | 비밀번호 변경            |
| userInfo       | GET     | /member/userinfo              | 사용자 정보 조회         |
| userInfo_1     | GET     | /member/userinfo/{email}      | 이메일로 사용자 정보 조회|

### 📁 Document
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION |
|---------------------|---------|-------------------------------|---------------|
| increaseDocumentLike| PUT     | /document/{document_id}/like  | 게시글 좋아요 증가    |
| modifyDocument      | PUT     | /document/manage/{document_id}| 게시글 수정         |
| deleteDocument      | DELETE  | /document/manage/{document_id}| 게시글 삭제         |
| saveDocument        | POST    | /document/manage              | 게시글 저장         |
| showOneDocument     | GET     | /document/{document_id}       | 특정 게시글 조회      |
| showAllDocument     | GET     | /document/list/{board_id}     | 게시판별 모든 게시글 조회 |

### 📁 Comment
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| showOneComment      | GET     | /comment/{comment_id}         | 특정 댓글 조회           |
| modifyComment       | PUT     | /comment/{comment_id}         | 댓글 수정                |
| deleteComment       | DELETE  | /comment/{comment_id}         | 댓글 삭제                |
| likeComment         | PUT     | /comment/{comment_id}/like    | 댓글 좋아요 처리         |
| saveComment         | POST    | /comment/{doc_id}             | 댓글 저장                |
| showCommentAll      | GET     | /comment/list/{doc_id}        | 게시글 ID로 모든 댓글 조회 |

### 📁 Viewership
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchViewershipCount| GET    | /viewership/{doc_id}          | 게시글 조회수 조회         |
| updateViewershipCount| PUT    | /viewership/{doc_id}          | 게시글 조회수 업데이트     |

### 📁 Like
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchLikeItCount   | GET     | /likeit/{uuid}                | 좋아요 수 조회           |
| updateLikeItCount   | PUT     | /likeit/{uuid}                | 좋아요 수 업데이트       |

### 📁 Report
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| acceptReport        | PUT     | /admin/report/accept/{report_id}| 신고 승인 처리        |
| reportDocument      | POST    | /report/doc/{document_id}     | 게시글 신고                |
| reportComment       | POST    | /report/com/{comment_id}      | 댓글 신고                |
| showAllReport       | GET     | /admin/report                 | 모든 신고 내역 조회      |

### 📁 Admin
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| updateBoardStatus   | PUT     | /admin/board                  | 게시판 상태 업데이트     |
| savePasswordQuestion| POST    | /admin/passwordquestion       | 비밀번호 질문 저장       |
| saveIndustry        | POST    | /admin/industry               | 산업 정보 저장           |

### 📁 Password Question
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchPasswordQuestion| GET   | /passwordquestion             | 비밀번호 질문 조회       |

### 📁 Industry
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchIndustry      | GET     | /industry                     | 산업 정보 조회           |

### 📁 Company
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| bringCompanyDataById| GET     | /companydata/{com_id}         | 회사 ID로 회사 데이터 조회|
| bringCompanyDataByName| GET   | /companydata/name/{com_Name}  | 회사 이름으로 회사 데이터 조회|

### 📁 Board
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| requestCreateBoard  | POST    | /board                        | 게시판 생성 요청         |
| searchBoard         | GET     | /board/{isApprove}            | 승인된 게시판 조회       |

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
