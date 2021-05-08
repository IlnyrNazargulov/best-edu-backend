ALTER TABLE discipline
    ADD COLUMN is_visible BOOLEAN NOT NULL DEFAULT true;

ALTER TABLE discipline
    ALTER COLUMN is_visible DROP DEFAULT;