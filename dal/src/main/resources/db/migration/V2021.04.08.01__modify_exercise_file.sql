ALTER TABLE exercise_file
    ADD COLUMN is_removed BOOLEAN NOT NULL DEFAULT false;
ALTER TABLE exercise_file
    ALTER COLUMN is_removed DROP DEFAULT;
ALTER TABLE exercise_file
    ALTER COLUMN exercise_id SET NOT NULL;

UPDATE exercise_file
SET exercise_file_type = 'CODE';
ALTER TABLE exercise_file
    ALTER COLUMN exercise_file_type SET NOT NULL;

CREATE UNIQUE INDEX exercise_file_content_unique ON exercise_file (exercise_id) WHERE (exercise_file_type = 'CONTENT');

ALTER TABLE exercise
    ADD COLUMN content_id UUID REFERENCES exercise_file (file_uuid);

CREATE TABLE image
(
    file_uuid UUID REFERENCES file (uuid) PRIMARY KEY,
    mime_type VARCHAR(63) NOT NULL
);