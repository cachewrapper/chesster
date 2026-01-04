CREATE TABLE user_credentials_view (
    user_uuid UUID PRIMARY KEY
        REFERENCES user_credentials_aggregate(aggregate_uuid)
        ON DELETE CASCADE,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE user_credentials_view_refresh_tokens (
    user_uuid UUID NOT NULL
        REFERENCES user_credentials_view(user_uuid)
        ON DELETE CASCADE,
    refresh_token TEXT NOT NULL,
    refresh_token_hash CHAR(32) GENERATED ALWAYS AS (md5(refresh_token)) STORED,
    PRIMARY KEY (user_uuid, refresh_token_hash)
);