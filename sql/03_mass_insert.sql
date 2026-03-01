USE tfi_p2_bd1;

-- 1) Tabla auxiliar de numeros (0..9999)
DROP TABLE IF EXISTS aux_nums;
CREATE TABLE aux_nums (n INT NOT NULL PRIMARY KEY);

INSERT INTO aux_nums (n)
SELECT a.d + b.d*10 + c.d*100 + d.d*1000
FROM (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
      UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) a
CROSS JOIN (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
      UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) b
CROSS JOIN (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
      UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) c
CROSS JOIN (SELECT 0 d UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
      UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) d;

-- Si ya tenes datos y queres empezar "limpio" (opcional):
-- DELETE FROM codigo_barras;
-- DELETE FROM producto;

-- 2) Insert masivo en PRODUCTO (10.000)
INSERT INTO producto (eliminado, nombre, marca, categoria, precio, peso)
SELECT
  0 AS eliminado,
  CONCAT('Producto ', n) AS nombre,
  CASE (n % 6)
    WHEN 0 THEN 'Acme'
    WHEN 1 THEN 'Nova'
    WHEN 2 THEN 'Fenix'
    WHEN 3 THEN 'Zen'
    WHEN 4 THEN 'Orion'
    ELSE 'Delta'
  END AS marca,
  CASE (n % 5)
    WHEN 0 THEN 'Alimentos'
    WHEN 1 THEN 'Tecnologia'
    WHEN 2 THEN 'Limpieza'
    WHEN 3 THEN 'Hogar'
    ELSE 'Bebidas'
  END AS categoria,
  ROUND(5 + (n % 500) * 1.15, 2) AS precio,
  CASE
    WHEN (n % 4) = 0 THEN NULL
    ELSE ROUND(0.10 + (n % 300) / 100.0, 3)
  END AS peso
FROM aux_nums
WHERE n BETWEEN 0 AND 9999;

-- 3) Insert masivo en CODIGO_BARRAS (1 a 1 con producto)
-- Asumimos que los IDs de producto son consecutivos.
-- Si tu tabla ya tenia filas, esta parte se basa en el id real, no en aux_nums.
INSERT INTO codigo_barras (eliminado, tipo, valor, fecha_asignacion, observaciones, producto_id)
SELECT
  0 AS eliminado,
  CASE (p.id % 3)
    WHEN 0 THEN 'EAN13'
    WHEN 1 THEN 'EAN8'
    ELSE 'UPC'
  END AS tipo,
  CONCAT('779', LPAD(p.id, 10, '0')) AS valor,
  DATE_ADD('2025-01-01', INTERVAL (p.id % 365) DAY) AS fecha_asignacion,
  'Carga masiva' AS observaciones,
  p.id AS producto_id
FROM producto p
LEFT JOIN codigo_barras c ON c.producto_id = p.id
WHERE c.producto_id IS NULL
ORDER BY p.id
LIMIT 10000;