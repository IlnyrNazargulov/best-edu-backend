CREATE TABLE account
(
    id            SERIAL PRIMARY KEY,
    created_at    TIMESTAMP   NOT NULL,
    login         VARCHAR(63) NOT NULL UNIQUE,
    password_hash VARCHAR(63) NOT NULL,
    second_name   VARCHAR(63) NOT NULL,
    first_name    VARCHAR(63) NOT NULL,
    patronymic    VARCHAR(63),
    type          VARCHAR(63) NOT NULL
);

CREATE TABLE notification
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP                   NOT NULL,
    text       TEXT                        NOT NULL,
    is_removed BOOLEAN                     NOT NULL,
    account_id INT REFERENCES account (id) NOT NULL
);

CREATE TABLE discipline
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP                   NOT NULL,
    is_removed BOOLEAN                     NOT NULL,
    teacher_id INT REFERENCES account (id) NOT NULL,
    UNIQUE (name, teacher_id)
);

CREATE TABLE exercise
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255)                   NOT NULL,
    content       TEXT                           NOT NULL,
    created_at    TIMESTAMP                      NOT NULL,
    is_removed    BOOLEAN                        NOT NULL,
    order_number  INT                            NOT NULL,
    discipline_id INT REFERENCES discipline (id) NOT NULL,
    UNIQUE (name, discipline_id)
);

CREATE TABLE refresh_token
(
    token      UUID PRIMARY KEY,
    account_id INT REFERENCES account (id) NOT NULL,
    created_at TIMESTAMP                   NOT NULL,
    expired_at TIMESTAMP                   NOT NULL
);

CREATE TABLE file
(
    uuid       UUID PRIMARY KEY,
    created_at TIMESTAMP   NOT NULL,
    is_removed BOOLEAN     NOT NULL,
    name       VARCHAR(63) NOT NULL,
    extension  VARCHAR(63) NOT NULL,
    size       BIGINT,
    owner_id   INT         NOT NULL REFERENCES account (id)
);

CREATE TABLE exercise_file
(
    file_uuid   UUID REFERENCES file (uuid) PRIMARY KEY,
    mime_type   VARCHAR(63)                  NOT NULL,
    exercise_id INT REFERENCES exercise (id) NOT NULL
);

CREATE TABLE comment
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP                    NOT NULL,
    text        TEXT                         NOT NULL,
    is_removed  BOOLEAN                      NOT NULL,
    author_id   INT REFERENCES account (id)  NOT NULL,
    exercise_id INT REFERENCES exercise (id) NOT NULL,
    parent_id   INT REFERENCES comment (id)  NOT NULL
);
