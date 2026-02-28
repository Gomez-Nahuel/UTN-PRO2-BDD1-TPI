package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Producto;

public class ProductoDao {

    public long insert(Connection conn, Producto p) throws SQLException {
        String sql = "INSERT INTO producto (eliminado, nombre, marca, categoria, precio, peso) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, p.isEliminado());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getMarca());
            ps.setString(4, p.getCategoria());
            ps.setDouble(5, p.getPrecio());

            if (p.getPeso() == null) {
                ps.setNull(6, Types.DECIMAL);
            } else {
                ps.setDouble(6, p.getPeso());
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("No se pudo obtener el id generado de producto.");
    }

    public Producto getById(Connection conn, long id) throws SQLException {
        String sql = "SELECT id, eliminado, nombre, marca, categoria, precio, peso FROM producto WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Producto p = new Producto();
                p.setId(rs.getLong("id"));
                p.setEliminado(rs.getBoolean("eliminado"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));

                double pesoVal = rs.getDouble("peso");
                if (rs.wasNull()) p.setPeso(null);
                else p.setPeso(pesoVal);

                return p;
            }
        }
    }

    public List<Producto> getAll(Connection conn, boolean incluirEliminados) throws SQLException {
        String sql = "SELECT id, eliminado, nombre, marca, categoria, precio, peso FROM producto";
        if (!incluirEliminados) {
            sql += " WHERE eliminado = 0";
        }
        sql += " ORDER BY id DESC";

        List<Producto> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getLong("id"));
                p.setEliminado(rs.getBoolean("eliminado"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));

                double pesoVal = rs.getDouble("peso");
                if (rs.wasNull()) p.setPeso(null);
                else p.setPeso(pesoVal);

                lista.add(p);
            }
        }
        return lista;
    }

    public boolean update(Connection conn, Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre = ?, marca = ?, categoria = ?, precio = ?, peso = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecio());

            if (p.getPeso() == null) ps.setNull(5, Types.DECIMAL);
            else ps.setDouble(5, p.getPeso());

            ps.setLong(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteLogico(Connection conn, long id) throws SQLException {
        String sql = "UPDATE producto SET eliminado = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}