CREATE TABLE user_view (
    user_uuid UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_user_aggregate
        FOREIGN KEY (user_uuid)
          REFERENCES user_aggregate(user_uuid)
          ON DELETE CASCADE
);