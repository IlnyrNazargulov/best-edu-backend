ALTER TABLE access_discipline
    DROP COLUMN status;

DELETE
FROM flyway_schema_history
WHERE version = '2021.05.07.01';