# Hongik Lost and Found https://github.com/qkrtjdrbs/hongik-lost-found

> 교내 분실물 게시판

### 구현 동기

    학교 커뮤니티를 보다가 교내에서 분실물이 자주 발생하는 것을 알게 되었고 전용 웹 사이트를 만들어서 편리하게 분실물을 찾거나 등록할 수 있으면 좋겠다는 생각에 만들었습니다.

### 기본 동작

    게시물은 비 로그인 상태로도 열람 가능합니다.

    게시물이나 댓글을 작성하려면 이메일 인증이 완료된 계정으로 로그인해야 합니다.

    게시물은 제목, 내용, 잃어버린 장소 or 발견한 장소(지도에 표시), 사진을 첨부할 수 있습니다.

---

# 1. 로그인

> 사이트 내에서 만든 계정 또는 소셜 계정으로 로그인이 가능합니다.

![로그인창](https://user-images.githubusercontent.com/68425462/170914523-0691276e-8a50-4ed6-a6f4-742b9d615060.PNG)

---

# 2. 회원 가입

> 사이트 내 직접 계정을 만들 수 있습니다.

![회원가입](https://user-images.githubusercontent.com/68425462/170914584-75e39192-3d2f-49d8-8c85-586c7764a91b.png)

- '회원가입' 버튼을 클릭하면 자동으로 기입한 이메일로 인증 링크가 전송됩니다. (인증 토큰 생성)

- 인증 링크의 유효 기간은 5분입니다.

- 미인증시 게시물, 댓글 열람은 가능하나 작성은 불가능 합니다.

- 나중에 인증 메일을 재전송할 수 있습니다.

---

# 3. 아이디 / 비밀번호 찾기

> 회원가입시 등록한 이메일로 아이디 / 비밀번호를 찾을 수 있습니다.

## 3-1. 아이디 찾기

![아이디찾기](https://user-images.githubusercontent.com/68425462/170914637-c9a7c94c-161e-47e5-8a65-7d13b6594314.png)

## 3-2. 비밀번호 찾기

![비번찾기](https://user-images.githubusercontent.com/68425462/170914668-fceeed78-dbf3-45ea-8f8f-a25d496e7e2d.png)

- 아이디에 등록된 이메일과 일치하면 임시 비밀번호를 생성 후 해당 아이디의 비밀번호를 임시 비밀번호로 바꿉니다.

---

# 4. 홈 화면

## 4-1. 구글 계정 로그인

> 구글 계정으로 로그인한 홈 화면입니다.

![홈화면](https://user-images.githubusercontent.com/68425462/170914693-8b4fdbfa-5d71-4265-8fb6-9e2fdad72193.PNG)

- Navigation Bar에 로그인 계정 이름을 띄워줍니다.

- '등록' 버튼을 클릭하면 게시물 작성폼을 보여줍니다.

- '목록' 또는 '이동' 버튼을 클릭하면 게시판을 보여줍니다.

- '로그아웃' 버튼을 클릭하면 현재 계정에서 로그아웃됩니다.

## 4-2. 이메일 미인증 계정 로그인

> 인증되지 않은 이메일로 로그인한 홈 화면입니다.

![미인증홈화면](https://user-images.githubusercontent.com/68425462/170914732-f2afb128-960f-4bcc-ac7d-2b896052c436.PNG)

- 이메일이 인증되지 않았을 시 Navigation Bar에 경고메시지를 띄웁니다.

- '인증 링크 재전송' 버튼을 클릭하면 해당 계정의 이메일로 인증 링크가 다시 전송됩니다.

---

# 5. 게시물 작성

> 게시물 작성 폼 입니다.

![게시물폼](https://user-images.githubusercontent.com/68425462/170914769-3885d7b9-868d-430a-b8f2-5cd7238cf741.png)

- 분실물인지 습득물인지 선택합니다.

- 텍스트 에디터를 이용해서 자유롭게 내용을 작성할 수 있고 사진 첨부 또한 가능합니다.

- 네이버 지도 API를 통해서 장소를 직접 좌표로 표시할 수 있습니다.

---

# 6. 게시판

## 6-1. 기본 게시판

> 게시물들이 페이징된 게시판입니다.

![게시판](https://user-images.githubusercontent.com/68425462/170914805-e61ec712-5bba-43d0-8f28-f2c5990ef3e6.png)

- 게시물이 최신순으로 정렬되어 보여집니다.

- 사용자가 원하는 게시물을 동적으로 검색할 수 있습니다.

- 페이지를 클릭해서 원하는 페이지로 이동할 수 있습니다.

## 6-2. 동적 검색

> 검색했을 때 결과를 페이징해서 보여줍니다.

![동적게시판](https://user-images.githubusercontent.com/68425462/170914848-750040be-00d3-4674-a567-867734ddc36f.png)

---

# 7. 게시물

## 7-1. 기본 게시물 구조

> 게시물 내용과 댓글, 댓글 작성 폼을 보여줍니다.

![게시물](https://user-images.githubusercontent.com/68425462/170914890-0e3db40d-a559-4b4e-b889-3fe8d514e2e9.png)

- 내용에는 작성자가 작성한 내용과 분실 or 습득 장소 좌표가 지도에 표시됩니다.

- '수정' 버튼으로 작성자가 게시물을 수정할 수 있습니다.

- '삭제' 버튼으로 작성자가 게시물을 삭제할 수 있습니다.

- 게시판이 표시되고 현재 어떤 게시물에 들어왔는지 알려줍니다.

## 7-2. 댓글

> 기본 댓글 구조입니다.

![댓글](https://user-images.githubusercontent.com/68425462/170914918-b62b36f2-3a7e-4d3f-8d08-8ce65a77c6e9.PNG)

- 댓글과 대댓글을 작성하면 ajax를 사용해서 실시간으로 화면에 반영됩니다.

- '답글' 버튼으로 댓글에 대댓글을 달 수 있습니다.

- 대댓글을 달면 누구에게 달았는지 내용에 자동으로 표시합니다.

- 무한 대댓글 작성이 가능합니다.

## 7-3. 댓글 페이징

> 페이징된 댓글 구조입니다.

![댓글페이징](https://user-images.githubusercontent.com/68425462/170914931-4bb2c059-4b98-4025-bbe1-d6b4f7eb9ae5.png)

- 댓글을 10개 단위로 페이징합니다.

- '댓글 더보기' 버튼을 누르면 ajax를 사용해서 최대 10개의 댓글을 더 보여줍니다.

---

# 8. 댓글 알람

> 댓글이 달렸을 때 작성자에게 알려줍니다.

![댓글알림](https://user-images.githubusercontent.com/68425462/170914955-d95662c5-15ae-4ff6-a78d-812be7bd2cb3.png)

- 현재 로그인 중인 계정이 작성한 게시물에 댓글이 달릴 경우 실시간으로 알려줍니다.

- 댓글 알람을 클릭하면 게시물의 해당 댓글의 위치로 이동합니다.

---

# 사용한 기술 스택

```
개인 프로젝트라서 모든 부분을 제가 개발했습니다.

Thymeleaf + Spring Boot의 조합으로 개발했습니다.

인터넷 강의를 통해 기본 사용 방법을 익히고 개발하면서 모르는 내용은 구글링을 통해 해결했습니다.
```

> 사용한 기술스택은 다음과 같습니다.

- Thymeleaf (Frontend)
- Spring Boot (Backend)
- Spring Security (Social Login)
- H2 Database (DB)
- Spring Data JPA & QueryDSL (ORM)
- AWS (Image Upload, Deployment)

---

# 패키지 구조

- config

  > 여러가지 기능을 위한 Configuration

  ```
  이메일 async 전송을 위한 AsyncConfig

  OAuth2 로그인을 위한 SecurityConfig

  로그인 권한 확인을 위한 WebConfig

  댓글 알람을 위한 WebSocketConfig
  ```

- controller

  > 매핑된 url이 요청되면 처리해주는 Controller

  ```
  게시물 관련 요청 BoardController

  댓글 알람 처리 CommentAlarmHandler

  댓글 관련 요청 CommentController

  이메일 관련 요청 EmailController

  홈 화면 관련 요청 HomeController

  로그인 관련 요청 LoginController

  사진 업로드 관련 요청 UploadController

  유저 관련 요청 UserController
  ```

- dto

  > 인자 전달을 편리하게 하기 위한 dto

- entity

  > 개발에 필요한 Entity

- exception

  > 권한이 없는 사용자가 접근했을 때 발생할 사용자 정의 Exception

- interceptor

  > login이 필요한 url에 접근했을 시 로그인 상태인지 검사하는 Interceptor

- repository

  > 영속성 context에 접근할 수 있는 Repository

- service

  > 비즈니스 로직을 수행해주는 Service

  ```
  사용자 인증 관련 AuthTokenService

  이미지 업로드 관련 AwsS3Service

  게시물 관련 BoardService

  댓글 관련 CommentService

  OAuth2 관련 CustomOAuth2UserService

  이메일 전송 관련 EmailService

  유저 관련 UserService
  ```

---

# 프로젝트 구조도

> 배포 전 local 환경에서의 구조입니다.

![구조도](https://user-images.githubusercontent.com/68425462/170915021-0e822a07-cbcb-4dee-a5ab-30c0bf239461.PNG)

---

# 배포

> AWS ec2를 이용한 수동 배포

```
Amazon ec2 인스턴스를 생성하고 그 안에 프로젝트 파일을 넣은 다음 jar 파일을 생성하고 실행하여 배포했습니다.

추후 Jenkins를 이용한 배포 자동화나 nginx를 이용한 무중단 배포도 해볼 계획입니다.
```

---

# 트러블 슈팅

## 무한 대댓글

> Comment Entity 구조입니다.

```java
  @Entity
  public class Comment {

      ...

      @ManyToOne(fetch = LAZY)
      @JoinColumn(name = "parent_id")
      private Comment parent;

      @OneToMany(mappedBy = "parent")
      private List<Comment> children = new ArrayList<>();

      ...

  }
```

```
parent는 자식의 부모 댓글, children은 부모의 대댓글로 연관 관계를 설정했습니다.

N+1 문제를 방지하기 위해 지연 로딩을 설정했습니다.
```

### 여러가지 Query 방법

- 게시물의 댓글을 불러올 때 chlidren이 있으면 불러오기

  ```
  만약 게시물의 댓글이 1000개이고 하나의 댓글에 999개의 대댓글이 달려있는 형태라면 대댓글 하나를 읽어올 때마다 Query가 나가게 됩니다. (N+1 문제)
  ```

- 게시물을 불러올 때 댓글들을 한번에 fetch join

  ```
  댓글 페이징을 하려면 댓글들을 한번에 불러와서 메모리에 올리게 됩니다.

  Query는 1번 나가지만 댓글 수가 많을 경우 시스템이 다운될 수도 있기 때문에 바람직하지 않습니다.
  ```

- batch size를 설정 후 in query

  ```
  batch size만큼의 in Query가 나눠서 나가게 됩니다.

  만약 게시물의 댓글이 1000개이고 하나의 댓글에 대댓글, 그 대댓글에 대대댓글, 그 대대댓글에 대대대댓글... 같은 형태로 한 뭉텅이가 있는 구조라면 역시 최악의 경우 1000개의 Query가 나가게 됩니다. (N+1 문제)
  ```

- 댓글 Query를 따로 분리후 groupId 필드로 관리

  ```
  "계층형 게시판"이라는 검색 키워드를 알아내고 얻은 방법입니다.

  원 댓글은 자신의 commentId로 groupId를 설정하고, 그 밑에 달리게 되는 모든 대댓글(자식)들은 원 댓글의 groupId와 같은 groupId를 갖게 설정합니다.

  commentId가 작을 수록 예전에 달린 댓글이기 떄문에, 댓글이 달린 순서대로 보여주기위해 querydsl로 작성한 query의 orderBy를 groupId의 오름차순, groupId가 같다면 commentId의 오름차순으로 설정합니다.

  페이징도 할 수 있게 되었고, N+1 문제도 해결할 수 있었습니다.
  ```

---
