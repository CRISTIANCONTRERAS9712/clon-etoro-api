DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS country CASCADE;
DROP TABLE IF EXISTS flyway_schema_history;

-- Tabla Country
CREATE TABLE country (
    id_country SERIAL PRIMARY KEY,
    iso_country VARCHAR(10) NOT NULL UNIQUE,
    "name" VARCHAR(120) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

-- Tabla User
CREATE TABLE users (
    id_user BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    birthdate DATE,
    "password" VARCHAR(255) NOT NULL,
    cellphone VARCHAR(30),
    country_id INT,
    CONSTRAINT fk_user_country
        FOREIGN KEY (country_id)
        REFERENCES country(id_country)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

-- Tabla Asset
CREATE TABLE asset (
    id SERIAL PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL UNIQUE,
    "name" VARCHAR(120) NOT NULL,
    "description" VARCHAR(255),
    logo VARCHAR(255),
    price VARCHAR(255),
    active BOOLEAN DEFAULT TRUE
);

