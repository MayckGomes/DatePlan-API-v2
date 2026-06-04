CREATE TABLE invites(
    id BIGSERIAL PRIMARY KEY,
    reciver_id BIGINT NOT NULL,
    reciver_name VARCHAR(50) NOT NULL,
    sender_id BIGINT NOT NULL,
    sender_Name VARCHAR(50) NOT NULL,
    decision_maker_id BIGINT NOT NULL
);

CREATE INDEX idx_invites_id
    ON invites(id);

CREATE INDEX idx_invites_reciver_id
    ON invites(reciver_id);