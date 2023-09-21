CREATE SCHEMA IF NOT EXISTS security;
CREATE SCHEMA IF NOT EXISTS borrower;

CREATE TABLE IF NOT EXISTS security.user_account (
    id int8,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    citizenship VARCHAR(100),
    role VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS borrower.personal_data (
    id int8,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    birth_date DATE,
    marital_status VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS borrower.borrower (
    id int8,
    user_account_id int8,
    personal_data_id int8,
    PRIMARY KEY (id),
    CONSTRAINT fk_borrower_borrower_user_account FOREIGN KEY (user_account_id) REFERENCES security.user_account(id),
    CONSTRAINT fk_borrower_borrower_personal_data FOREIGN KEY (personal_data_id) REFERENCES borrower.personal_data(id)
);
