CREATE TABLE access_discipline
(
    id            SERIAL PRIMARY KEY,
    student_id    INT REFERENCES account (id)    NOT NULL,
    discipline_id INT REFERENCES discipline (id) NOT NULL
);