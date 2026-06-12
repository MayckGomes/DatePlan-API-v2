CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    public_id VARCHAR(50) NOT NULL,

    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(250),

    account_type VARCHAR(50),
    external_provider_id VARCHAR(255),

    relationship_id BIGINT,
    plan VARCHAR(50) NOT NULL,
    notification_token VARCHAR(250) NOT NULL,

    accept_policy_privacy_version BIGINT NOT NULL,
    policy_privacy_accepted_at VARCHAR(50) NOT NULL,

    accept_terms_of_use_version BIGINT NOT NULL,
    terms_of_use_accepted_at VARCHAR(50) NOT NULL
);

CREATE INDEX idx_users_id
    ON users(id);

CREATE INDEX idx_users_email
    ON users(email);

CREATE INDEX idx_users_public_id
    ON users(public_id);