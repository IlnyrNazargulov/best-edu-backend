DROP TABLE IF EXISTS account
    , notification
    , comment
    , discipline
    , exercise
    , refresh_token
    , file
    , exercise_file;

DELETE
FROM flyway_schema_history
WHERE version = '2021.02.15.01';