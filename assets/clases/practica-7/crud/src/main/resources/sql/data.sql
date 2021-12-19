--- Vaciamos la tabla usuarios
DELETE FROM usuarios;

--- Insertamos el administrador
INSERT INTO usuarios (name, email, pass, admin) VALUES ('Administrador', 'admin@empresa.com', '654321', 1);
-- Insertamos el resto de usuarios
INSERT INTO usuarios (name, email, pass) VALUES
('Usuario', 'user@empresa.com', '123456'),
('Visor', 'visor@empresa.com', '123456'),
('Max Power', 'max.power@empresa.com','123456'),
('Bruno Díaz', 'bruce.wayne@empresa.com','123456'),
('Enrico Palazzo', 'enricco.palazo@empresa.com','123456'),
('Ricardo Tapia', 'ri.cardo@empresa.com','123456');