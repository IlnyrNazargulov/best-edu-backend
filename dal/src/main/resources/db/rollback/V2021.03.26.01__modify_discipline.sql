ALTER TABLE discipline
    DROP COLUMN description;

DELETE
FROM flyway_schema_history
WHERE version = '2021.03.26.01';