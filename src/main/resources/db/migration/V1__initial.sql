CREATE TABLE Users(
    id BIGSERIAL PRIMARY KEY,
    public_Id VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    relationship_Id BIGINT,
    plan VARCHAR NOT NULL,
    notification_Token VARCHAR NOT NULL
);