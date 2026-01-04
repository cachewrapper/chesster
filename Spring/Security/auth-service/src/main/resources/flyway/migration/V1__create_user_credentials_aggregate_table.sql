CREATE TABLE user_credentials_aggregate (
    aggregate_uuid UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE user_credentials_aggregate_refresh_tokens (
    aggregate_uuid UUID NOT NULL
        REFERENCES user_credentials_aggregate(aggregate_uuid)
        ON DELETE CASCADE,
    refresh_token VARCHAR(512) NOT NULL,
    PRIMARY KEY(aggregate_uuid, refresh_token)
);