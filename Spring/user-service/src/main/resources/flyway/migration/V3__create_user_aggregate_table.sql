CREATE TABLE user_aggregate (
    aggregate_uuid UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_user_credentials
        FOREIGN KEY (aggregate_uuid)
            REFERENCES user_credentials_aggregate(aggregate_uuid)
            ON DELETE CASCADE
);