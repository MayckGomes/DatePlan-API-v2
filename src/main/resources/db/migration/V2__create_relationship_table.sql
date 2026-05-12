CREATE TABLE relationship(
    id BIGSERIAL PRIMARY KEY,
    id_user1 BIGINT NOT NULL,
    id_user2 BIGINT NOT NULL,
    initial_day VARCHAR(50) NOT NULL
);