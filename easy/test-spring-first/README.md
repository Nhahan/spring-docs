1. 구조 잡기

   <img width="224" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/58878b46-f283-41b7-9e35-e59424f1a365">


2. Entity를 생성
    1. Entity의 어노테이션 3형제 + id 만들기

   <img width="678" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/3aa568b7-ac1b-4930-b226-050217152076">


3. 어노테이션 붙이기
    1. Controller

        ```java
        @RestController
        @RequiredArgsConstructor
        ```

    2. Service

        ```java
        @Service
        @RequiredArgsConstructor
        ```

4. CRUD에서 R이 아닌 로직이 포함된 컨트롤러를 제외한 메소드에는 @Transactional 붙인다.
5. Request와 Response는 Entity가 아닌 DTO를 반드시 이용한다. (@Getter 잊지 말기)
    1. 이는 추후 JPA 연관관계 매핑 등에서 발생하는 잠재적 에러를 방지해준다.
