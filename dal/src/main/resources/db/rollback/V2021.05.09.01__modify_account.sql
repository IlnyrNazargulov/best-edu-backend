ALTER TABLE account
    DROP COLUMN birthdate,
    DROP COLUMN rank,
    DROP COLUMN information;

DELETE
FROM flyway_schema_history
WHERE version = '2021.05.09.01';