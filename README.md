# hanghaeblog 
> Spring Boot로 로그인 기능이 없는 나만의 항해 블로그 백엔드 서버 만들기

<details><summary>level 2 내용</summary>
  
## 요구사항
1. 아래의 요구사항을 기반으로 Use Case 그려보기
2. 전체 게시글 목록 조회 API
  - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
  - 작성 날짜 기준 내림차순으로 정렬하기
3. 게시글 작성 API
  - 제목, 작성자명, 비밀번호, 작성 내용을 저장하고
  - 저장된 게시글을 Client 로 반환하기
4. 선택한 게시글 조회 API 
  - 선택한 게시글의 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
5. 선택한 게시글 수정 API
  - 수정을 요청할 때 수정할 데이터와 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - 제목, 작성자명, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
6. 선택한 게시글 삭제 API
  - 삭제를 요청할 때 비밀번호를 같이 보내서 서버에서 비밀번호 일치 여부를 확인 한 후
  - 선택한 게시글을 삭제하고 Client 로 성공했다는 표시 반환하기

## 주의사항
1. Entity를 그대로 반환하지 말고, DTO에 담아서 반환해주세요.
2. JSON을 반환하는 API형태로 진행해주세요. (PostMan을 활용해 서버가 반환하는 결과값을 확인할 수 있습니다.)


## 구현한 기능
1. Lombok과 JPA를 이용하여 원하는 DB 사용
2. Spring Boot 기반으로 CRUD 기능이 포함된 REST API 설계
## Use Case
<img width="293" alt="메모장_pjt_유스케이스_다이어그램" src="https://user-images.githubusercontent.com/106947027/232636416-e7aaa3d7-d051-440e-911a-3801d0b5bfa6.png">

## API 명세서
![image](https://user-images.githubusercontent.com/97998858/232209293-27dd7f32-4398-4a4b-8fc9-a8b75e6ea07f.png)

## Why?
1. 수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)
  - 수정, 삭제 기능은 pk인 id를 기반으로 Repo 메소드를 사용하였고, id값은 @PathVariable을 통해서 받고, 프런트에서 온 HTTP body 데이터(JSON)는 @RequestBody를 사용하여 JSON형태의 객체로 받고 반환함 
2. 어떤 상황에 어떤 방식의 request를 써야하나요?
  - 프런트에서 백엔드로 http 통신으로 데이터를 전달할 때는 (1) url을 통한 데이터 전달, (2) HTTP body를 통한 데이터 전달이 있음
  - (1) Url을 통해 데이터를 전달 받을 때는 @PathVariable을 통해서 데이터를 받을 수 있음
  - (2) Http Body 데이터는 JSON, xml 등 다양한 형태로 오는데 JSON을 객체로 받으려면 @RequestBody를 사용
3. RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
  - RESTFul한 API: 자원 (URI), 행위 (HTTP Method), 표현을 잘 표현해서 설계한 API 
  - controller/service/repository로 레이어 분리함 
  - 적절한 메소드 사용 (등록 : POST, 수정 : PUT, 삭제 : DELETE) 
  - URI에 대한 표현은 예시와 동일하게 구성하여서 잘 표현이 안됨
  
4. 적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)
  -  Controller, Repository, Service를 잘 분리함
5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
  - Request와 Response에 header, eliments도 들어갈 수 있음
  
</details>

<details><summary>level 3 내용</summary>
  
### ERD
  ![image](https://user-images.githubusercontent.com/106947027/234207313-fe01b5c4-3a2d-47a9-8eed-77fa6dc232f2.png)


### API 명세서
https://documenter.getpostman.com/view/26164797/2s93Y6re1n
  
### Why
1. 처음 설계한 API 명세서에 변경사항이 있었나요? 
변경 되었다면 어떤 점 때문 일까요? 첫 설계의 중요성에 대해 작성해 주세요!
    
    → 댓글과 게시물 테이블 사이의 연관관계를 반영하여 API를 수정하였습니다.
    때문에 엔티티의 필드도 재정의하는 과정에서 필드들의 값을 사용하는 Getter 메소드 수정에 어려움이 있었습니다. 다시한번 리펙토링 할 때, Git으로 버전 관리하는 중요성을 느꼈습니다.
    
2. ERD를 먼저 설계한 후 Entity를 개발했을 때 어떤 점이 도움이 되셨나요?
    - ERD를 설계하면서 데이터베이스가 어떻게 생기고, 객체 간 관계가 어떻게 형성되는지 미리 정하고 개발을 시작할 수 있었어요.
3. JWT를 사용하여 인증/인가를 구현 했을 때의 장점은 무엇일까요?
    - 서버의 부담이 줄어들어요!
4. 반대로 JWT를 사용한 인증/인가의 한계점은 무엇일까요?
    - 토큰을 강제로 만료하기가 어렵고, 중요한 정보를 담을 수 없어요.
5. 댓글이 달려있는 게시글을 삭제하려고 할 때 무슨 문제가 발생할까요?
JPA가 아닌 DB테이블 관점에서 해결방법이 무엇일까요?
    
    → **`delete from memo where memo_id=?`**  쿼리문을 실행하는 도중 발생한 외래 키 제약 조건 관련된 에러가 발생했음
    
    memo 테이블의 데이터를 지울 때, 외래키가 참조하는 모든 테이블의 데이터까지 삭제되어야 하는데, 이에 대한 설정이 없어서 발생한 에러임
    DB 테이블 관점 (MySQL 기준)에서는 테이블 생성 쿼리문에 “ON DELETE CASCADE” 옵션을 추가해줌으로써 외래 키 제약 조건을 설정 할 수 있음
    
6. 5번과 같은 문제가 발생했을 때 JPA에서는 어떻게 해결할 수 있을까요?
→ JPA에서는 Cascade 옵션을 통해 외래 키 제약 조건을 자동으로 처리 할 수 있음
7. IoC / DI 에 대해 간략하게 설명해 주세요! 
    - IoC는 Inversion of Control(제어의 역전)으로, 제어의 흐름을 바꾸는 것입니다. 기존에는 객체를 생성하고 클래스 내부에서 의존성 객체를 생성하고 의존성 객체 메소드를 호출했다면, 스프링은 객체를 생성하고 의존성 객체를 주입하고 의존성 객체 메소드를 호출하는 방식으로 실행됩니다. -> DI는 Dependency Injection(의존성 주입)으로 객체를 직접 생성하는 게 아니라 외부에서 생성한 뒤 주입 시켜주는 것을 말합니다. DI를 통해 모듈간 결합도가 낮아지고 유연성이 높아집니다.
  
</details>


<details><summary>level 5 내용</summary>
  
### ERD
  ![image](https://user-images.githubusercontent.com/106947027/235858286-4ab5961b-2cf2-451d-9aba-38fcb52bf3ab.png)

### API 명세서
https://documenter.getpostman.com/view/26164797/2s93Y6re1n
  
### Why
1. Spring Security를 적용했을 때 어떤 점이 도움이 되셨나요?
    → 보안 관련 작업을 더욱 쉽고 안전하게 할 수 있음
    (1) 인증 및 권한 부여 (2) CSRF 방어 (3) 세션 관리 (4) 로깅 및 모니터링 (5) 확장성 (6) 커뮤니티
    
2. Spring Security를 사용하지 않는다면 어떻게 인증/인가를 효율적으로 처리할 수 있을까요?
    
    → AOP를 이용해서 인증/인가 구현을 할 수 있음
    
    1. 보안 체크 에너테이션 : @PreAuthorize, @PostAuthorize, @Secured 등의 애너테이션을 사용할 수 있음
    2. 메소드 실행 전/후 보안 체크 : Before advice와 AfterReturning advice를 사용하여 메소드 호출 전/후에 보안 체크를 수행할 수 있음
3. AOP에 대해 설명해 주세요!
    
    → AOP(Aspect-Oriented Programming)은 객체지향 프로그래밍에서 관심사의 분리를 위해 사용하는 기술 중 하나입니다. AOP는 주요 비즈니스 로직 외에도 다양한 부가 기능(로깅, 예외 처리, 인증/인가 등)을 모듈화하여 개발자가 간단하게 적용할 수 있도록 합니다.
    반복적으로 사용되는 부가 기능을 모듈화하는 것이 핵심이다.
    
4. RefreshToken 적용에 대한 장/단점을 작성해 주세요! 적용해 보지 않으셨다면 JWT를 사용하여 인증/인가를 구현 했을 때의 장/단점에 대해 숙련주차의 답변을 Upgrade 하여 작성해 주세요!
    
    → 기존의 jwt Acess Token은 만료 시간이 짧고, 사용자가 매번 새로운 토큰을 발급받아야함
    - Refresh Token 장점
    (1) 토큰을 자동으로 갱신 가능 
    (2) 보안 강화 : Refresh Token은 보통 Access Token보다 더 긴 유효기간을 가짐. 이는 Refresh Token을 탈취당하더라도 공격자가 임의의 Access Token을 발급받을 수 있는 시간을 제한할 수 있기 때문임
    (3) 다중 디바이스 지원: Refresh Token은 일반적으로 서버에 저장되므로, 여러 디바이스에서 로그인하여도 같은 Refresh Token을 사용할 수 있음
    - Refresh Token 단점
    (1) 보안 위험 : Refresh Token은 Access Token보다 더 긴 유효기간을 가지므로, Refresh Token이 탈취될 경우 보안 위험이 존재함. 따라서, Refresh Token을 안전하게 관리해야 함
    (2)서버 부하: Refresh Token은 일반적으로 서버에 저장되므로, 많은 사용자가 로그인하면 서버 부하가 증가할 수 있음
    (3) 토큰 관리 복잡성: Refresh Token을 사용하면, 토큰 관리가 복잡해질 수 있음. 예를 들어, 만료된 Refresh Token을 삭제하고 새로운 Refresh Token을 발급하는 등의 작업이 필요할 수 있음
    
5. 즉시로딩 / 지연로딩에 대해 설명해 주세요!    
    1. 즉시 로딩 : 연관된 객체를 함께 로딩하는 방식
    2. 지연 로딩 : 객체를 실제로 사용할 때 해당 객체를 로딩하는 방식
    즉 연관된 객체를 로딩하지 않고 필요한 시점에 쿼리를 실행하여 필요한 객체를 로딩함
    즉시로딩은 연관된 모든 객체를 함께 로딩하여 성능상 이점을 가져오지만, 모든 데이터를 함께 로딩하기 때문에 메모리 소비나 로딩 시간 등의 문제가 발생할 수 있습니다. 반면에, 지연로딩은 필요한 객체만 로딩하여 성능상 이점을 가져올 수 있지만, 데이터베이스 쿼리의 횟수가 많아질 수 있습니다. 어떤 방식을 사용할지는 상황과 용도에 따라 적절하게 판단하여 결정해야 합니다.
  
</details>
