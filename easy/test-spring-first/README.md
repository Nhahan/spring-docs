3. 일단 생성한다.
    1. 5형제를 일단 만든다.

   <img width="224" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/58878b46-f283-41b7-9e35-e59424f1a365">


4. Entity를 만든다.
    1. id 없으면 안되겠죠? 만든다.
    2. Entity의 어노테이션 3형제 붙여넣기

   <img width="678" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/3aa568b7-ac1b-4930-b226-050217152076">


5. 어노테이션 붙이기
    1. Controller
        1.

        ```java
        @RestController
        @RequiredArgsConstructor
        ```

    2. Service

        ```java
        @Service
        @RequiredArgsConstructor
        ```

6. CRUD에서 R이 아닌 로직이 포함된 컨트롤러를 제외한 최상위 메소드에는 `@Transactional` 붙인다.
7. 무조건 DTO를 이용한다.
    1. 반드시 `@Getter` 붙인다.
