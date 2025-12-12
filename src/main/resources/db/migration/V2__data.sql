-- Datos iniciales de Country
INSERT INTO country (iso_country, name, active) VALUES
('CO', 'Colombia', TRUE),
('MX', 'México', TRUE),
('AR', 'Argentina', TRUE),
('US', 'USA', TRUE);

-- Datos iniciales de User
INSERT INTO users (firstname, lastname, email, birthdate, password, cellphone, country_id) VALUES ('Cristiannnnn', 'Contreras', 'cristian@example.com', '1997-12-21', '123456', '3001234567', 1),
('Juan', 'Pérez', 'juan@example.com', '1990-04-10', 'abc123', '3109876543', 2);
