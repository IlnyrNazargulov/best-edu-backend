CREATE TABLE request_code
(
    id         SERIAL PRIMARY KEY,
    email      VARCHAR(63) NOT NULL,
    code       VARCHAR(63) NOT NULL,
    created_at TIMESTAMP   NOT NULL
);