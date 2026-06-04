CREATE TABLE dates(
    id BIGSERIAL PRIMARY KEY,
    date VARCHAR(50) NOT NULL,
    time VARCHAR(50) NOT NULL,
    local VARCHAR(250) NOT NULL,
    title VARCHAR(500) NOT NULL,
    description VARCHAR(500) NOT NULL,
    id_icon BIGINT NOT NULL,
    id_relationship BIGINT NOT NULL,
    id_author BIGINT NOT NULL
);

CREATE INDEX idx_dates_id
    ON dates(id);
CREATE INDEX idx_dates_relationship_id
    ON dates(id_relationship);