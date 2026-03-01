USE tfi_p2_bd1;

-- 1) Conteos
SELECT (SELECT COUNT(*) FROM producto) AS productos,
       (SELECT COUNT(*) FROM codigo_barras) AS codigos;

-- 2) Huérfanos (FK invalida) => debe dar 0
SELECT COUNT(*) AS codigos_huerfanos
FROM codigo_barras c
LEFT JOIN producto p ON p.id = c.producto_id
WHERE p.id IS NULL;

-- 3) Cardinalidad 1 a 1 (producto sin codigo) => ideal 0 si cargaste parejo
SELECT COUNT(*) AS productos_sin_codigo
FROM producto p
LEFT JOIN codigo_barras c ON c.producto_id = p.id
WHERE c.producto_id IS NULL;

-- 4) Unicidad de valor de codigo (por si algo salio mal) => ideal 0
SELECT COUNT(*) AS codigos_duplicados
FROM (
  SELECT valor
  FROM codigo_barras
  GROUP BY valor
  HAVING COUNT(*) > 1
) t;

-- 5) Dominio/rangos (precio > 0 y peso NULL o > 0) => ideal 0
SELECT COUNT(*) AS productos_fuera_de_dominio
FROM producto
WHERE precio <= 0 OR (peso IS NOT NULL AND peso <= 0);