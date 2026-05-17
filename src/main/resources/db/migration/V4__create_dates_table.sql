CREATE TABLE dates(
    id BIGSERIAL PRIMARY KEY,
    date VARCHAR(50) NOT NULL,
    time VARCHAR(50) NOT NULL,
    local VARCHAR(250) NOT NULL,
    title VARCHAR(500) NOT NULL,
    description VARCHAR(500) NOT NULL,
    id_icon INTEGER NOT NULL,
    id_relationship INTEGER NOT NULL,
    id_author INTEGER NOT NULL
);