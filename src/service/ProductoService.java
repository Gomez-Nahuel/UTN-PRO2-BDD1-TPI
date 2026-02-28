package service;

import java.sql.Connection;

import config.DbConfig;
import dao.CodigoBarrasDao;
import dao.ProductoDao;
import entities.CodigoBarras;
import entities.Producto;

public class ProductoService {

    private final ProductoDao productoDao = new ProductoDao();
    private final CodigoBarrasDao codigoDao = new CodigoBarrasDao();

    public long crearProductoConCodigo(Producto p, CodigoBarras c) throws Exception {

        try (Connection conn = DbConfig.getConnection()) {
            conn.setAutoCommit(false);

            try {
                long productoId = productoDao.insert(conn, p);

                c.setProductoId(productoId);
                codigoDao.insert(conn, c);

                conn.commit();
                return productoId;

            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
