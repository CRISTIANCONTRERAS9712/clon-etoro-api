-- Datos iniciales de Country
INSERT INTO country (iso_country, name, active) VALUES
('CO', 'Colombia', TRUE),
('MX', 'México', TRUE),
('AR', 'Argentina', TRUE),
('US', 'USA', TRUE);

-- Datos iniciales de User
INSERT INTO users (firstname, lastname, email, birthdate, password, cellphone, country_id) VALUES ('Cristiannnnn', 'Contreras', 'cristian@example.com', '1997-12-21', '123456', '3001234567', 1),
('Juan', 'Pérez', 'juan@example.com', '1990-04-10', 'abc123', '3109876543', 2);

-- Datos iniciales de Asset
INSERT INTO asset (ticker, "name", description, logo, price, active) VALUES
('GOOG', 'Google', 'Empresa Google', 'https://example.com/goog.png', '200', TRUE),
('META', 'Meta', 'Empresa Meta', 'https://example.com/meta.png', '700', TRUE);

-- Datos iniciales de Propertie
INSERT INTO propertie ("key", "value", active) VALUES
('API_KEY', '123456', TRUE),
('API_SECRET', '654321', TRUE);

-- Datos iniciales de Position
INSERT INTO position (units, buy_price, buy_date, user_id, asset_id) VALUES
(10, 200, '2022-01-01', 1, 1),
(20, 700, '2022-02-01', 1, 2);
