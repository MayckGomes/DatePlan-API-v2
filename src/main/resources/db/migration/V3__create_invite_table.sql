CREATE TABLE invite(
    id BIGSERIAL PRIMARY KEY,
    to_id BIGINT NOT NULL,
    to_name VARCHAR(50) NOT NULL,
    from_id BIGINT NOT NULL,
    from_Name VARCHAR(50) NOT NULL,
):