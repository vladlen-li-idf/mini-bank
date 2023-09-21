CREATE SCHEMA IF NOT EXISTS security;
CREATE SCHEMA IF NOT EXISTS borrower;

CREATE TYPE security.status as enum('ACTIVE', 'CLOSED', 'DRAFT');
CREATE TYPE borrower.status as enum('ACTIVE', 'CLOSED', 'DRAFT');

CREATE TABLE IF NOT EXISTS security.user (
    id int8,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    status security.status,
    citizenship VARCHAR(100),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS security.role (
    id int8,
    code VARCHAR(100),
    status security.status
);

CREATE TABLE IF NOT EXISTS borrower.personal_data (
    id int8,
    status borrower.status,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birth_date DATE,
    marital_status VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS borrower.borrower (
    id int8,
    status borrower.status,
    user_id int8,
    personal_data_id int8,
    PRIMARY KEY (id),
    CONSTRAINT fk_borrower_borrower_user_account FOREIGN KEY (user_id) REFERENCES security.user(id),
    CONSTRAINT fk_borrower_borrower_personal_data FOREIGN KEY (personal_data_id) REFERENCES borrower.personal_data(id)
);
