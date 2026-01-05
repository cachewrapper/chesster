CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE user_credentials (
    user_uuid UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL
);

CREATE UNIQUE INDEX idx_user_credentials_email ON user_credentials(email);
CREATE UNIQUE INDEX idx_user_credentials_username ON user_credentials(username);