CREATE DATABASE IF NOT EXISTS tfi_p2_bd1
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE tfi_p2_bd1;

DROP TABLE IF EXISTS codigo_barras;
DROP TABLE IF EXISTS producto;

CREATE TABLE producto (
  id BIGINT NOT NULL AUTO_INCREMENT,
  eliminado BOOLEAN NOT NULL DEFAULT 0,
  nombre VARCHAR(120) NOT NULL,
  marca VARCHAR(80) NULL,
  categoria VARCHAR(80) NULL,
  precio DECIMAL(10,2) NOT NULL,
  peso DECIMAL(10,3) NULL,
  CONSTRAINT pk_producto PRIMARY KEY (id),
  CONSTRAINT chk_producto_precio CHECK (precio > 0),
  CONSTRAINT chk_producto_peso CHECK (peso IS NULL OR peso > 0)
);

CREATE TABLE codigo_barras (
  id BIGINT NOT NULL AUTO_INCREMENT,
  eliminado BOOLEAN NOT NULL DEFAULT 0,
  tipo ENUM('EAN13','EAN8','UPC') NOT NULL,
  valor VARCHAR(20) NOT NULL,
  fecha_asignacion DATE NULL,
  observaciones VARCHAR(255) NULL,
  producto_id BIGINT NOT NULL,
  CONSTRAINT pk_codigo_barras PRIMARY KEY (id),
  CONSTRAINT uq_codigo_barras_valor UNIQUE (valor),
  CONSTRAINT uq_codigo_barras_producto UNIQUE (producto_id),
  CONSTRAINT fk_codigo_barras_producto
    FOREIGN KEY (producto_id) REFERENCES producto(id)
    ON DELETE CASCADE
);

CREATE INDEX ix_producto_categoria ON producto(categoria);
CREATE INDEX ix_producto_marca ON producto(marca);
CREATE INDEX ix_producto_precio ON producto(precio);