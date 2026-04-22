DROP TABLE IF EXISTS member_referees;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS collectivity;

CREATE TABLE collectivity (
    id VARCHAR(50) PRIMARY KEY,
    location VARCHAR(255) NOT NULL, 
    specialty VARCHAR(100) NOT NULL, 
    name VARCHAR(255) UNIQUE NOT NULL,
    federation_approval BOOLEAN DEFAULT FALSE, 
    creation_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE member (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    gender VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE')), 
    address TEXT,
    profession VARCHAR(100),
    phone_number VARCHAR(20),
    email VARCHAR(255),
    occupation VARCHAR(50), 
    joining_date DATE NOT NULL DEFAULT CURRENT_DATE, 
    collectivity_id VARCHAR(50) REFERENCES collectivity(id)
);

CREATE TABLE member_referees (
    member_id VARCHAR(50) REFERENCES member(id),
    referee_id VARCHAR(50) REFERENCES member(id),
    relationship_type VARCHAR(50),
    PRIMARY KEY (member_id, referee_id)
);

CREATE TABLE account (
    id VARCHAR(50) PRIMARY KEY,
    type VARCHAR(20) CHECK (type IN ('CASH', 'BANK', 'MOBILE_MONEY')),
    owner_id VARCHAR(50) NOT NULL, -- collectivity id or 'federation'
    balance DECIMAL(15,2) DEFAULT 0.00,
    holder_name VARCHAR(255),
    bank_name VARCHAR(20) CHECK (bank_name IN ('BRED', 'MCB', 'BMOI', 'BOA', 'BGFI', 'AFG', 'ACCES_BANQUE', 'BAOBAB', 'SIPEM')),
    account_number VARCHAR(23),
    mobile_service VARCHAR(20) CHECK (mobile_service IN ('ORANGE_MONEY', 'MVOLA', 'AIRTEL_MONEY')),
    phone_number VARCHAR(20)
);

CREATE TABLE contribution (
    id VARCHAR(50) PRIMARY KEY,
    member_id VARCHAR(50) REFERENCES member(id),
    collectivity_id VARCHAR(50) REFERENCES collectivity(id),
    amount DECIMAL(15,2) NOT NULL,
    date DATE NOT NULL,
    payment_method VARCHAR(20) CHECK (payment_method IN ('CASH', 'BANK_TRANSFER', 'MOBILE_MONEY')),
    description TEXT
);