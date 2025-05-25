## API 명세 (v2)

| 기능 | 메서드 | URL | 요청 | 응답 | 상태 코드 |
| --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | `/api/v2/auth/signup` | RequestBody | ResponseBody | 200, 400 |
| 로그인 | POST | `/api/v2/auth/signin` | RequestBody | ResponseBody | 200, 400 |
| 로그아웃 | POST | `/api/v2/auth/signout` | RequestHeader | ResponseBody | 200, 400 | <!-- 로그아웃을 GET으로 구현하면 안된다!!! -->
| - | - | - | - | - | - | <!-- 비밀번호 검증을 위해 RequestBody도 함께 요청 -->
| 회원정보 수정 | POST | `/api/v2/auth/me` | RequestHeader, RequestBody | ResponseBody | 200, 400 |
| 회원 탈퇴 | DELETE | `/api/v2/auth/me` | RequestHeader, RequestBody | ResponseBody | 200, 400 |
| - | - | - | - | - | - |
| 전체 일정 조회 | GET | `/api/v2/schedules` | (optional) QueryString | ResponseBody | 200 |
| 선택 일정 조회 | GET | `/api/v2/schedules/{id}` | PathVariable | ResponseBody | 200, 404 |
| - | - | - | - | - | - | <!-- 일정 생성, 수정, 삭제는 로그인 요구 -->
| 일정 생성 | POST | `/api/v2/schedules` | RequestHeader, RequestBody | ResponseBody | 200, 400 |
| 일정 수정 | PUT | `/api/v2/schedules/{id}` | RequestHeader, PathVariable, RequestBody | ResponseBody | 200, 400, 404 |
| 일정 삭제 | DELETE | `/api/v2/schedules/{id}` | RequestHeader, PathVariable, RequestBody | ResponseBody | 200, 400, 404 |

<details>
<summary>0-2단계 API 명세 (v1)</summary>

## API 명세 (v1)

| 기능 | 메서드 | URL | 요청 | 응답 | 상태 코드 |
| --- | --- | --- | --- | --- | --- |
| 일정 생성 | POST | `/api/v1/schedules` | RequestBody | ResponseBody | 200, 400 |
| 전체 일정 조회 | GET | `/api/v1/schedules` | (optional) QueryString | ResponseBody | 200 |
| 선택 일정 조회 | GET | `/api/v1/schedules/{id}` | PathVariable | ResponseBody | 200, 404 |
| 일정 수정 | PUT | `/api/v1/schedules/{id}` | PathVariable, RequestBody | ResponseBody | 200, 400, 404 |
| 일정 삭제 | DELETE | `/api/v1/schedules/{id}` | PathVariable, RequestBody | ResponseBody | 200, 400, 404 |
</details>

## ERD (v2)

```mermaid
erDiagram
    direction LR
    user ||--o{ schedule : "1:N"

    user {
        Long id PK
        VARCHAR name
        VARCHAR password
    %% 암호를 평문으로 저장하는게 정말 너무 거슬린다....
        VARCHAR email
        DATE created_at
        DATE modified_at
    %% 요구사항이 YYYY-MM-DD 형식이므로 DATE 사용
    }
    schedule {
        Long id PK
        Long user_id FK
        TEXT content
        DATE created_at
        DATE modified_at
    %% 요구사항이 YYYY-MM-DD 형식이므로 DATE 사용
    }
```
- SQL
```sql
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at DATE,
    modified_at DATE
);

CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at DATE,
    modified_at DATE,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
```

<details>
<summary>0-2단계 ERD (v1)</summary>

## ERD (v1)
```mermaid
erDiagram
    schedule {
        Long id PK
        TEXT content
        VARCHAR name
        VARCHAR password
    %% 암호를 평문으로 저장하는게 정말 너무 거슬린다....
        DATE created_at
        DATE modified_at
    %% 요구사항이 YYYY-MM-DD 형식이므로 DATE 사용
    }
```

- SQL
```sql
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATE,
    modified_at DATE
);
```
</details>