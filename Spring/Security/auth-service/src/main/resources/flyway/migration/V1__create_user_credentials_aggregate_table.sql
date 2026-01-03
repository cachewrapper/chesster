CREATE TABLE user_credentials_aggregate (
    aggregate_uuid UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(512) NOT NULL
);