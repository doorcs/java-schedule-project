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

-- `0 - 2`단계 테이블:

-- CREATE TABLE schedule (
--     id BIGINT AUTO_INCREMENT PRIMARY KEY,
--     content TEXT NOT NULL,
--     name VARCHAR(255) NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     created_at DATE,
--     modified_at DATE
-- );