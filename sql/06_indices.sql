USE tfi_p2_bd1;

-- Indices recomendados para tus consultas
CREATE INDEX idx_producto_categoria ON producto(categoria);
CREATE INDEX idx_producto_marca ON producto(marca);
CREATE INDEX idx_producto_precio ON producto(precio);

CREATE INDEX idx_codigo_fecha ON codigo_barras(fecha_asignacion);
-- producto_id ya deberia estar indexado por el UNIQUE, pero por si no:
-- CREATE INDEX idx_codigo_producto_id ON codigo_barras(producto_id);