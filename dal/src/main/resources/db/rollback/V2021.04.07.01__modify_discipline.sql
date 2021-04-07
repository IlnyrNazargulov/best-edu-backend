ALTER TABLE discipline
    ALTER COLUMN description TYPE VARCHAR(200);

DELETE
FROM flyway_schema_history
WHERE version = '2021.04.07.01';