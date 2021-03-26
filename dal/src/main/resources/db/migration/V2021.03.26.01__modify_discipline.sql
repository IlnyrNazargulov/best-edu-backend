ALTER TABLE discipline
    ADD COLUMN description VARCHAR(200) NOT NULL DEFAULT 'Описание';

ALTER TABLE discipline
    ALTER COLUMN description DROP DEFAULT;