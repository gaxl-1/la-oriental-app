package com.fortis.laoriental.dao;

import com.fortis.laoriental.conexion.ConexionBD;
import com.fortis.laoriental.modelo.VentaResumen;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para reportes de ventas.
 *
 * La tabla venta se llena al cerrar una cuenta en PedidoDAO. Esta clase no
 * registra ventas; solamente consulta ventas cerradas para reportes diarios,
 * filtros por cajero e indicadores de caja.
 */
public class VentaDAO {

    private final ConexionBD conexionBD = new ConexionBD();

    /**
     * Consulta ventas del dia actual sin filtrar por cajero.
     */
    public List<VentaResumen> listarVentasDelDia() {
        return listarVentas(LocalDate.now(), "Todos");
    }

    /**
     * Consulta ventas de una fecha, opcionalmente filtradas por cajero.
     *
     * @param fecha fecha que se desea consultar
     * @param cajeroFiltro nombre del cajero o "Todos"
     * @return ventas encontradas ordenadas de la mas reciente a la mas antigua
     */
    public List<VentaResumen> listarVentas(LocalDate fecha, String cajeroFiltro) {
        List<VentaResumen> ventas = new ArrayList<>();
        String sql = "SELECT v.id_venta, v.id_pedido, u.nombre AS cajero, "
                + "v.fecha_hora, v.total, v.observaciones "
                + "FROM venta v "
                + "INNER JOIN usuario u ON v.id_usuario_cajero = u.id_usuario "
                + "WHERE DATE(v.fecha_hora) = ? "
                + "AND (? = 'Todos' OR u.nombre = ?) "
                + "ORDER BY v.fecha_hora DESC";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(fecha));
            ps.setString(2, cajeroFiltro == null || cajeroFiltro.isBlank() ? "Todos" : cajeroFiltro);
            ps.setString(3, cajeroFiltro == null || cajeroFiltro.isBlank() ? "Todos" : cajeroFiltro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapearResumen(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return ventas;
    }

    /**
     * Obtiene los nombres de cajeros que ya tienen ventas registradas.
     * Se usa para llenar el filtro del modulo Reportes.
     */
    public List<String> listarCajerosConVenta() {
        List<String> cajeros = new ArrayList<>();
        String sql = "SELECT DISTINCT u.nombre FROM venta v "
                + "INNER JOIN usuario u ON v.id_usuario_cajero = u.id_usuario ORDER BY u.nombre";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cajeros.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cajeros: " + e.getMessage());
        }
        return cajeros;
    }

    /** Convierte una fila SQL en un DTO VentaResumen para mostrar en tablas. */
    private VentaResumen mapearResumen(ResultSet rs) throws SQLException {
        VentaResumen venta = new VentaResumen();
        venta.setIdVenta(rs.getInt("id_venta"));
        venta.setIdPedido(rs.getInt("id_pedido"));
        venta.setCajero(rs.getString("cajero"));
        Timestamp fecha = rs.getTimestamp("fecha_hora");
        if (fecha != null) {
            venta.setFechaHora(fecha.toLocalDateTime());
        }
        venta.setTotal(rs.getDouble("total"));
        venta.setObservaciones(rs.getString("observaciones"));
        return venta;
    }
}
