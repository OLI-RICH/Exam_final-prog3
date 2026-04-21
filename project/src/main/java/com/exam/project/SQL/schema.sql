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