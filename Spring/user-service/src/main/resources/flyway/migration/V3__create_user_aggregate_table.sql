CREATE TABLE users (
    user_uuid UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    avatar BYTEA,
    CONSTRAINT fk_user_credentials
        FOREIGN KEY (user_uuid)
        REFERENCES user_credentials(user_uuid)
        ON DELETE CASCADE
);