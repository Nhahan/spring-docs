3. 일단 생성한다.
    1. 5형제를 일단 만든다.

   ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/e85a9eaf-b17f-4159-ad44-2e2d5cf701cc/Untitled.png)

4. Entity를 만든다.
    1. id 없으면 안되겠죠? 만든다.
    2. Entity의 어노테이션 3형제 붙여넣기

   ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/83c75a39-3aba-4ba4-a792-7aefe4b07895/85512693-5b5d-44f0-9a06-ae70e5c9c30f/Untitled.png)

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