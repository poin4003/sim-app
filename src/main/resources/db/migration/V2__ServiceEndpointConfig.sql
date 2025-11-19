DROP TABLE IF EXISTS service_endpoint_configs CASCADE;

CREATE TABLE service_endpoint_configs (
    service_endpoint_config_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    endpoint_type INT NOT NULL,

    endpoint_status INT NOT NULL,

    topic VARCHAR(255) NOT NULL,

    consumer_group VARCHAR(255) UNIQUE NOT NULL,

    concurrency INT DEFAULT 1,

    filter_key VARCHAR(100),
    
    filter_value VARCHAR(100),

    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO service_endpoint_configs
(
    endpoint_type,
    endpoint_status,
    topic,
    consumer_group,
    concurrency,
    filter_key,
    filter_value,
    created_at,
    updated_at
)
VALUES
(
    1,
    1,
    'sim-import-topic',
    'sim-import-group-partner-a',
    3,
    'partnerId',
    'PARTNER_A',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    1,
    1,
    'sim-import-topic',
    'sim-import-group-partner-b',
    6,
    'partnerId',
    'PARTNER_B',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);