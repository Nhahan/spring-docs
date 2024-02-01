# 1. controller, dto, entity, repository, service 만들기
<img width="110" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/1ba2a1ac-55a3-4f39-91e9-e02ab4d1b02f">

<br><br>

# 2. Entity 만들기
- `@Getter`, `@Entity`, `@NoArgsConstructor`
- `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`
<img width="709" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/03499c80-b736-489d-88d6-18d5726c166c">

<br><br>

# 3. Controller, Service, Repository 만들기
- Controller: `@RestController`, `@RequiredArgsConstructor`
- Service: `@Service`, `@RequiredArgsConstructor`
<img width="506" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/ac251402-c2b2-45b0-8c21-a81ed2e99c83">
<img width="806" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/db64ab0c-5dbc-41e3-9204-a2daad33688d">
<img width="110" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/1ba2a1ac-55a3-4f39-91e9-e02ab4d1b02f">

<br><br>

# 4. CRUD 중 R을 제외한 나머지 로직에 `@Transactional` 붙이기
<img width="419" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/07b86eed-b970-4759-a97b-97e2985c0aa4">

<br><br>

# 5. 반드시 DTO를 이용해서 request, response 하기
- DTO에는 `@Getter`가 있어야한다
<img width="327" alt="image" src="https://github.com/Nhahan/mvc/assets/81916648/29513507-cb07-40eb-9447-60c5b016dcea">

<br><br>
