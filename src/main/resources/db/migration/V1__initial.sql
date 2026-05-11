CREATE TABLE Users(
    id BIGSERIAL PRIMARY KEY,
    public_Id VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(250) NOT NULL,
    relationship_Id BIGINT,
    plan VARCHAR(50) NOT NULL,
    notification_Token VARCHAR(250) NOT NULL
);