# UTN - Programacion 2 / Bases de Datos I - TPI

Proyecto simple en Java (JDBC) + MySQL para el dominio:
Producto (A) -> CodigoBarras (B) con relacion 1 a 1.

## Requisitos
- JDK instalado
- MySQL Server corriendo (localhost:3306)
- MySQL Workbench (opcional, recomendado)
- Driver JDBC: lib/mysql-connector-j-9.6.0.jar

## Base de datos
Ejecutar scripts en MySQL Workbench:

1) sql/01_schema.sql
2) sql/02_seed.sql

La base se crea como: tfi_p2_bd1

Tablas:
- producto
- codigo_barras

## Ejecutar el proyecto (Windows PowerShell)

Compilar:
javac -cp "lib/mysql-connector-j-9.6.0.jar" -d bin src/config/*.java src/entities/*.java src/dao/*.java src/service/*.java src/main/*.java

Ejecutar:
java -cp "bin;lib/mysql-connector-j-9.6.0.jar" main.App

## Funcionalidades
- Crear producto con codigo de barras (transaccion)
- Listar productos (sin eliminados)
- Buscar producto por ID
- Actualizar producto
- Eliminar producto (baja logica)

## Notas
- La eliminacion es logica: producto.eliminado = 1
- Se usa PreparedStatement para consultas seguras
- Se usa transaccion al crear Producto + CodigoBarras

## Arquitectura del proyecto

- config: configuracion de conexion JDBC
- entities: clases modelo (Producto, CodigoBarras)
- dao: acceso a datos (CRUD con PreparedStatement)
- service: logica de negocio y transacciones
- main: menu de consola


## Manejo de transacciones

Al crear un producto se realiza una transaccion:
- Se inserta Producto
- Se inserta CodigoBarras
- Si ocurre un error se hace rollback
- Si todo es correcto se hace commit

Esto garantiza atomicidad.