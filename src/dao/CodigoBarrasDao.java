package dao;

import java.sql.*;

import entities.CodigoBarras;

public class CodigoBarrasDao {

    public long insert(Connection conn, CodigoBarras c) throws SQLException {
        String sql = "INSERT INTO codigo_barras (eliminado, tipo, valor, fecha_asignacion, observaciones, producto_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, c.isEliminado());
            ps.setString(2, c.getTipo());
            ps.setString(3, c.getValor());

            if (c.getFechaAsignacion() == null || c.getFechaAsignacion().trim().isEmpty()) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4, Date.valueOf(c.getFechaAsignacion()));
            }

            ps.setString(5, c.getObservaciones());
            ps.setLong(6, c.getProductoId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("No se pudo obtener el id generado de codigo_barras.");
    }

    public CodigoBarras getByProductoId(Connection conn, long productoId) throws SQLException {
        String sql = "SELECT id, eliminado, tipo, valor, fecha_asignacion, observaciones, producto_id "
                   + "FROM codigo_barras WHERE producto_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, productoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                CodigoBarras c = new CodigoBarras();
                c.setId(rs.getLong("id"));
                c.setEliminado(rs.getBoolean("eliminado"));
                c.setTipo(rs.getString("tipo"));
                c.setValor(rs.getString("valor"));

                Date d = rs.getDate("fecha_asignacion");
                c.setFechaAsignacion(d == null ? null : d.toString());

                c.setObservaciones(rs.getString("observaciones"));
                c.setProductoId(rs.getLong("producto_id"));
                return c;
            }
        }
    }

    public boolean deleteLogico(Connection conn, long id) throws SQLException {
        String sql = "UPDATE codigo_barras SET eliminado = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
