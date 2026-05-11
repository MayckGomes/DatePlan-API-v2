CREATE TABLE Users(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    public_Id VARCHAR NOT NULL,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    relationship_Id INTEGER,
    plan VARCHAR NOT NULL,
    notification_Token VARCHAR NOT NULL
);