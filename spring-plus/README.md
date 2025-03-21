# SPRING PLUS

## aws

health check api: http://13.125.229.236:8080/health

### ec2

![img](images/ec2.png)

### s3
![img](images/s3.png)

### rds
![img](images/rds.png)

### 대용량 데이터 처리

![img](images/count.png)

- users 테이블에 약 100만건 insert
- nickname 필드에 index 적용

>> index 적용 방법은 User 엔티티 클래스에서 @Index 어노테이션을 사용하여 적용하거나, ALTER SQL 문으로 직접 적용할 수 있다.

```java
@Table(name = "users", indexes = {
        @Index(name = "idx_nickname", columnList = "nickname")
})
public class User extends Timestamped {
```

```sql
ALTER TABLE users ADD INDEX idx_nickname (nickname);
```

#### before

![img](images/before.png)

#### after

![img](images/after.png)

#### 결과 정리

| 데이터 건수  | Index 적용 여부 | 응답 시간 (ms) | 감소율 (%) |
|--------------|-----------------|------------|---------|
| 1,000,000    | 미적용          | 4,920      | -       |
| 1,000,000    | 적용            | 251        | 94.9%   |
