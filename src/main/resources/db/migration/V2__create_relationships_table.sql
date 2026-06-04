CREATE TABLE relationships(
    id BIGSERIAL PRIMARY KEY,
    user_id1 BIGINT NOT NULL,
    user_id2 BIGINT NOT NULL,
    initial_day VARCHAR(50) NOT NULL
);

CREATE INDEX idx_relationships_id
    ON relationships(id);