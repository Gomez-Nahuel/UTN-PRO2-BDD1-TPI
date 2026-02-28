USE tfi_p2_bd1;

INSERT INTO producto (nombre, marca, categoria, precio, peso)
VALUES
('Yerba Mate 1kg', 'Playadito', 'Alimentos', 9.99, 1.000),
('Mouse Gamer', 'Logi', 'Tecnologia', 39.90, 0.300);

INSERT INTO codigo_barras (tipo, valor, fecha_asignacion, producto_id)
VALUES
('EAN13', '7791234567890', '2025-01-10', 1),
('UPC',   '042100005264',  '2025-01-11', 2);