-- Active: 1762248135493@@127.0.0.1@5432@sim_app_db
-- Run this file to migrate db

DROP TABLE IF EXISTS sim;
CREATE TABLE sim (
    sim_id VARCHAR(255) PRIMARY KEY,
    sim_phone_number VARCHAR(20) NOT NULL UNIQUE,
    sim_status INT NOT NULL,
    sim_selling_price INT,
    sim_dealer_price INT,
    sim_import_price INT,

    note TEXT,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

ALTER TABLE sim ADD CONSTRAINT uq_sim_phone_number UNIQUE (sim_phone_number);
