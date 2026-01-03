CREATE TABLE user_credentials_view (
    user_uuid UUID PRIMARY KEY
        REFERENCES user_credentials_aggregate(aggregate_uuid)
        ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);
