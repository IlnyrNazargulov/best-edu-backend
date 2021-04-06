ALTER TABLE exercise
    ADD COLUMN content TEXT NOT NULL;

ALTER TABLE exercise_file
    ADD COLUMN mime_type VARCHAR(63) NOT NULL DEFAULT 'jpeg';

ALTER TABLE exercise_file
    ALTER COLUMN mime_type DROP DEFAULT;

ALTER TABLE exercise_file
    DROP COLUMN exercise_file_type;
ALTER TABLE exercise_file
    ALTER COLUMN exercise_id SET NOT NULL;

DELETE
FROM flyway_schema_history
WHERE version = '2021.03.30.01';