ALTER TABLE discipline
    DROP COLUMN is_visible;

DELETE
FROM flyway_schema_history
WHERE version = '2021.05.08.01';