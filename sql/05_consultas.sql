USE tfi_p2_bd1;

-- =========================
-- Q1 (JOIN): listado de productos con su codigo
-- =========================
SELECT
  p.id,
  p.nombre,
  p.marca,
  p.categoria,
  p.precio,
  c.tipo,
  c.valor,
  c.fecha_asignacion
FROM producto p
JOIN codigo_barras c ON c.producto_id = p.id
WHERE p.eliminado = 0 AND c.eliminado = 0
ORDER BY p.id DESC
LIMIT 50;

-- EXPLAIN Q1 (para captura)
EXPLAIN
SELECT
  p.id, p.nombre, p.marca, p.categoria, p.precio,
  c.tipo, c.valor, c.fecha_asignacion
FROM producto p
JOIN codigo_barras c ON c.producto_id = p.id
WHERE p.eliminado = 0 AND c.eliminado = 0
ORDER BY p.id DESC;


-- =========================
-- Q2 (JOIN): filtrar por categoria y rango de fechas en codigo
-- =========================
SELECT
  p.categoria,
  COUNT(*) AS cantidad,
  ROUND(AVG(p.precio), 2) AS precio_promedio
FROM producto p
JOIN codigo_barras c ON c.producto_id = p.id
WHERE p.eliminado = 0
  AND c.eliminado = 0
  AND p.categoria IN ('Tecnologia', 'Alimentos')
  AND c.fecha_asignacion BETWEEN '2025-03-01' AND '2025-06-30'
GROUP BY p.categoria
ORDER BY cantidad DESC;

-- EXPLAIN Q2
EXPLAIN
SELECT
  p.categoria,
  COUNT(*) AS cantidad,
  ROUND(AVG(p.precio), 2) AS precio_promedio
FROM producto p
JOIN codigo_barras c ON c.producto_id = p.id
WHERE p.eliminado = 0
  AND c.eliminado = 0
  AND p.categoria IN ('Tecnologia', 'Alimentos')
  AND c.fecha_asignacion BETWEEN '2025-03-01' AND '2025-06-30'
GROUP BY p.categoria;


-- =========================
-- Q3 (GROUP BY + HAVING): marcas con muchos productos y precio promedio alto
-- =========================
SELECT
  p.marca,
  COUNT(*) AS cantidad_productos,
  ROUND(AVG(p.precio), 2) AS precio_promedio
FROM producto p
WHERE p.eliminado = 0
GROUP BY p.marca
HAVING COUNT(*) >= 3000 AND AVG(p.precio) >= 150
ORDER BY cantidad_productos DESC;

-- EXPLAIN Q3
EXPLAIN
SELECT
  p.marca,
  COUNT(*) AS cantidad_productos,
  ROUND(AVG(p.precio), 2) AS precio_promedio
FROM producto p
WHERE p.eliminado = 0
GROUP BY p.marca
HAVING COUNT(*) >= 3000 AND AVG(p.precio) >= 150;


-- =========================
-- Q4 (SUBCONSULTA): productos con precio mayor al promedio de su categoria
-- =========================
SELECT
  p.id,
  p.nombre,
  p.categoria,
  p.precio
FROM producto p
WHERE p.eliminado = 0
  AND p.precio >
    (SELECT AVG(p2.precio)
     FROM producto p2
     WHERE p2.eliminado = 0
       AND p2.categoria = p.categoria)
ORDER BY p.precio DESC
LIMIT 50;

-- EXPLAIN Q4
EXPLAIN
SELECT
  p.id, p.nombre, p.categoria, p.precio
FROM producto p
WHERE p.eliminado = 0
  AND p.precio >
    (SELECT AVG(p2.precio)
     FROM producto p2
     WHERE p2.eliminado = 0
       AND p2.categoria = p.categoria)
ORDER BY p.precio DESC
LIMIT 50;


-- =========================
-- VISTA (para Etapa 3)
-- =========================
CREATE OR REPLACE VIEW vw_producto_codigo AS
SELECT
  p.id AS producto_id,
  p.nombre,
  p.marca,
  p.categoria,
  p.precio,
  p.peso,
  c.tipo AS codigo_tipo,
  c.valor AS codigo_valor,
  c.fecha_asignacion
FROM producto p
JOIN codigo_barras c ON c.producto_id = p.id
WHERE p.eliminado = 0 AND c.eliminado = 0;

-- Probar vista
SELECT * FROM vw_producto_codigo LIMIT 20;