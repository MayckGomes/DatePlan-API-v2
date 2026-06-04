CREATE TABLE memories(
    id BIGSERIAL PRIMARY KEY,
    date VARCHAR(50) NOT NULL,
    local VARCHAR(250) NOT NULL,
    title VARCHAR(500) NOT NULL,
    description VARCHAR(500) NOT NULL,
    id_icon BIGINT NOT NULL,
    id_relationship BIGINT NOT NULL,
    id_author BIGINT NOT NULL
);

CREATE INDEX idx_memories_id
    ON memories(id);

CREATE INDEX idx_memories_relationship_id
    ON memories(id_relationship);