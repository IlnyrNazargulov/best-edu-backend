ALTER TABLE exercise
    DROP COLUMN content;
ALTER TABLE exercise_file
    DROP COLUMN mime_type;
ALTER TABLE exercise_file
    ADD COLUMN exercise_file_type VARCHAR(63) DEFAULT 'CODE';
ALTER TABLE exercise_file
    ALTER COLUMN exercise_file_type DROP DEFAULT;
ALTER TABLE exercise_file
    ALTER COLUMN exercise_id DROP NOT NULL;