CREATE TABLE user_refresh_tokens (
    refresh_token_uuid UUID PRIMARY KEY,
    user_uuid UUID NOT NULL,
    refresh_token TEXT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_uuid) REFERENCES user_credentials(user_uuid) ON DELETE CASCADE
);
