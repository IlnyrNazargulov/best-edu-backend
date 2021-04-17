ALTER TABLE exercise_file
    DROP COLUMN is_removed;
ALTER TABLE exercise_file
    ALTER COLUMN exercise_id DROP NOT NULL;
ALTER TABLE exercise_file
    ALTER COLUMN exercise_file_type DROP NOT NULL;

DROP INDEX exercise_file_content_unique;

DROP TABLE IF EXISTS image;

ALTER TABLE exercise
    DROP COLUMN content_id;

DELETE
FROM flyway_schema_history
WHERE version = '2021.04.08.01';