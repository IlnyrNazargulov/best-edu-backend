ALTER TABLE account
    DROP COLUMN is_removed;

DELETE
FROM flyway_schema_history
WHERE version = '2021.05.09.02';