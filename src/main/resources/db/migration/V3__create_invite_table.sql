CREATE TABLE invite(
    id BIGSERIAL PRIMARY KEY,
    reciver_id BIGINT NOT NULL,
    reciver_name VARCHAR(50) NOT NULL,
    sender_id BIGINT NOT NULL,
    sender_Name VARCHAR(50) NOT NULL
);