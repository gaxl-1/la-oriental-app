package com.fortis.laoriental.dao;

import com.fortis.laoriental.conexion.ConexionBD;
import com.fortis.laoriental.modelo.ComandaResumen;
import com.fortis.laoriental.modelo.DetallePedido;
import com.fortis.laoriental.modelo.Mesa;
import com.fortis.laoriental.modelo.PedidoResumen;
import com.fortis.laoriental.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO principal del flujo operativo del restaurante.
 *
 * En este sistema, pedido significa cuenta de mesa y comanda significa envio
 * a cocina. Esta separacion permite que una mesa tenga una sola cuenta, pero
 * varias tandas de productos enviadas a cocina durante la misma estancia.
 */
public class PedidoDAO {

    private final ConexionBD conexionBD = new ConexionBD();

    /** Lista mesas que no tienen una cuenta abierta. */
    public List<Mesa> listarMesasDisponibles() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id_mesa, numero, capacidad, estado, ubicacion, activo "
                + "FROM mesa WHERE activo = TRUE AND id_mesa NOT IN ("
                + "SELECT id_mesa FROM pedido WHERE estado = 'ABIERTO') ORDER BY numero";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) mesas.add(mapearMesa(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar mesas disponibles: " + e.getMessage());
        }
        return mesas;
    }

    public List<PedidoResumen> listarPedidosPorEstados(String... estados) {
        List<PedidoResumen> pedidos = new ArrayList<>();
        if (estados == null || estados.length == 0) return pedidos;
        StringBuilder marcas = new StringBuilder();
        for (int i = 0; i < estados.length; i++) {
            if (i > 0) marcas.append(",");
            marcas.append("?");
        }
        String sql = "SELECT p.id_pedido, p.id_mesa, m.numero, u.nombre AS mesero, "
                + "p.fecha_hora, p.estado, p.total, "
                + "CASE "
                + "WHEN NOT EXISTS (SELECT 1 FROM detalle_pedido dp WHERE dp.id_pedido = p.id_pedido) THEN 'SIN PRODUCTOS' "
                + "WHEN EXISTS (SELECT 1 FROM comanda c WHERE c.id_pedido = p.id_pedido AND c.estado = 'PENDIENTE') THEN 'PENDIENTE DE ENVIAR' "
                + "WHEN EXISTS (SELECT 1 FROM comanda c WHERE c.id_pedido = p.id_pedido AND c.estado = 'EN_COCINA') THEN 'EN COCINA' "
                + "WHEN p.estado = 'ABIERTO' THEN 'LISTO PARA COBRO' ELSE 'CERRADO' END AS estado_cocina "
                + "FROM pedido p "
                + "INNER JOIN mesa m ON p.id_mesa = m.id_mesa "
                + "INNER JOIN usuario u ON p.id_usuario = u.id_usuario "
                + "WHERE p.estado IN (" + marcas + ") ORDER BY p.fecha_hora ASC";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < estados.length; i++) ps.setString(i + 1, estados[i]);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) pedidos.add(mapearResumen(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    /**
     * Devuelve cuentas cobrables: tienen productos y ninguna comanda pendiente
     * ni en cocina. Asi caja no cobra una mesa incompleta.
     */
    public List<PedidoResumen> listarPedidosListosParaCobro() {
        List<PedidoResumen> pedidos = new ArrayList<>();
        String sql = "SELECT p.id_pedido, p.id_mesa, m.numero, u.nombre AS mesero, "
                + "p.fecha_hora, p.estado, p.total, 'LISTO PARA COBRO' AS estado_cocina "
                + "FROM pedido p "
                + "INNER JOIN mesa m ON p.id_mesa = m.id_mesa "
                + "INNER JOIN usuario u ON p.id_usuario = u.id_usuario "
                + "WHERE p.estado = 'ABIERTO' "
                + "AND EXISTS (SELECT 1 FROM detalle_pedido dp WHERE dp.id_pedido = p.id_pedido) "
                + "AND NOT EXISTS (SELECT 1 FROM comanda c WHERE c.id_pedido = p.id_pedido AND c.estado IN ('PENDIENTE','EN_COCINA')) "
                + "ORDER BY p.fecha_hora ASC";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) pedidos.add(mapearResumen(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos para cobro: " + e.getMessage());
        }
        return pedidos;
    }

    /** Crea una cuenta para la mesa y marca la mesa como ocupada. */
    public int crearPedidoPorMesa(int idMesa, int idUsuario) throws SQLException {
        String validar = "SELECT COUNT(*) FROM pedido WHERE id_mesa = ? AND estado = 'ABIERTO'";
        String insertar = "INSERT INTO pedido(id_mesa, id_usuario, estado, total) VALUES (?, ?, 'ABIERTO', 0.00)";
        String actualizarMesa = "UPDATE mesa SET estado = 'OCUPADA' WHERE id_mesa = ?";
        Connection con = conexionBD.getConexion();
        con.setAutoCommit(false);
        try {
            try (PreparedStatement ps = con.prepareStatement(validar)) {
                ps.setInt(1, idMesa);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("La mesa ya tiene una cuenta abierta. Agregue productos a esa misma cuenta.");
                    }
                }
            }
            int idPedido;
            try (PreparedStatement ps = con.prepareStatement(insertar, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idMesa);
                ps.setInt(2, idUsuario);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) throw new SQLException("No se pudo obtener el ID del pedido.");
                    idPedido = rs.getInt(1);
                }
            }
            try (PreparedStatement ps = con.prepareStatement(actualizarMesa)) {
                ps.setInt(1, idMesa);
                ps.executeUpdate();
            }
            con.commit();
            return idPedido;
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    /**
     * Agrega producto a la cuenta abierta. Si no hay comanda pendiente,
     * crea una nueva comanda PENDIENTE para agrupar los nuevos productos.
     */
    public boolean agregarProducto(int idPedido, int idProducto, int cantidad) {
        if (cantidad <= 0) return false;
        String validarPedido = "SELECT estado FROM pedido WHERE id_pedido = ?";
        String comandaPendiente = "SELECT id_comanda FROM comanda WHERE id_pedido = ? AND estado = 'PENDIENTE' ORDER BY id_comanda DESC LIMIT 1";
        String nuevaComanda = "INSERT INTO comanda(id_pedido, estado) VALUES (?, 'PENDIENTE')";
        String sqlPrecio = "SELECT precio, stock FROM producto WHERE id_producto = ? AND activo = TRUE";
        String sqlStock = "UPDATE producto SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";
        String sqlInsert = "INSERT INTO detalle_pedido(id_pedido, id_comanda, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlTotal = "UPDATE pedido SET total = (SELECT COALESCE(SUM(subtotal),0) FROM detalle_pedido WHERE id_pedido = ?) WHERE id_pedido = ?";
        try (Connection con = conexionBD.getConexion()) {
            con.setAutoCommit(false);
            try {
                validarPedidoAbierto(con, validarPedido, idPedido);
                int idComanda = obtenerOcrearComandaPendiente(con, comandaPendiente, nuevaComanda, idPedido);
                Producto producto = obtenerProductoParaVenta(con, sqlPrecio, idProducto, cantidad);
                try (PreparedStatement ps = con.prepareStatement(sqlStock)) {
                    ps.setInt(1, cantidad);
                    ps.setInt(2, idProducto);
                    ps.setInt(3, cantidad);
                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }
                }
                double subtotal = producto.getPrecio() * cantidad;
                try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                    ps.setInt(1, idPedido);
                    ps.setInt(2, idComanda);
                    ps.setInt(3, idProducto);
                    ps.setInt(4, cantidad);
                    ps.setDouble(5, producto.getPrecio());
                    ps.setDouble(6, subtotal);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(sqlTotal)) {
                    ps.setInt(1, idPedido);
                    ps.setInt(2, idPedido);
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

    public List<DetallePedido> listarDetallePedido(int idPedido) {
        return listarDetalle("dp.id_pedido = ?", idPedido);
    }

    /**
     * Modifica la cantidad de un producto siempre que pertenezca a una comanda
     * pendiente. Ajusta el stock con la diferencia entre cantidad anterior y nueva.
     */
    public boolean editarDetallePendiente(int idDetalle, int nuevaCantidad) {
        if (idDetalle <= 0 || nuevaCantidad <= 0) return false;
        String detalleSql = "SELECT dp.id_pedido, dp.id_producto, dp.cantidad, dp.precio_unitario, c.estado "
                + "FROM detalle_pedido dp INNER JOIN comanda c ON dp.id_comanda = c.id_comanda "
                + "WHERE dp.id_detalle = ?";
        String stockSql = "UPDATE producto SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";
        String devolverSql = "UPDATE producto SET stock = stock + ? WHERE id_producto = ?";
        String actualizarSql = "UPDATE detalle_pedido SET cantidad = ?, subtotal = ? WHERE id_detalle = ?";
        try (Connection con = conexionBD.getConexion()) {
            con.setAutoCommit(false);
            try {
                int idPedido;
                int idProducto;
                int cantidadActual;
                double precio;
                try (PreparedStatement ps = con.prepareStatement(detalleSql)) {
                    ps.setInt(1, idDetalle);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next() || !"PENDIENTE".equals(rs.getString("estado"))) {
                            con.rollback();
                            return false;
                        }
                        idPedido = rs.getInt("id_pedido");
                        idProducto = rs.getInt("id_producto");
                        cantidadActual = rs.getInt("cantidad");
                        precio = rs.getDouble("precio_unitario");
                    }
                }
                int diferencia = nuevaCantidad - cantidadActual;
                if (diferencia > 0) {
                    try (PreparedStatement ps = con.prepareStatement(stockSql)) {
                        ps.setInt(1, diferencia);
                        ps.setInt(2, idProducto);
                        ps.setInt(3, diferencia);
                        if (ps.executeUpdate() == 0) {
                            con.rollback();
                            return false;
                        }
                    }
                } else if (diferencia < 0) {
                    try (PreparedStatement ps = con.prepareStatement(devolverSql)) {
                        ps.setInt(1, Math.abs(diferencia));
                        ps.setInt(2, idProducto);
                        ps.executeUpdate();
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(actualizarSql)) {
                    ps.setInt(1, nuevaCantidad);
                    ps.setDouble(2, nuevaCantidad * precio);
                    ps.setInt(3, idDetalle);
                    ps.executeUpdate();
                }
                recalcularTotal(con, idPedido);
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al editar detalle pendiente: " + e.getMessage());
            return false;
        }
    }

    /** Elimina un detalle pendiente y devuelve su cantidad al stock. */
    public boolean eliminarDetallePendiente(int idDetalle) {
        if (idDetalle <= 0) return false;
        String detalleSql = "SELECT dp.id_pedido, dp.id_producto, dp.cantidad, c.estado "
                + "FROM detalle_pedido dp INNER JOIN comanda c ON dp.id_comanda = c.id_comanda "
                + "WHERE dp.id_detalle = ?";
        String devolverSql = "UPDATE producto SET stock = stock + ? WHERE id_producto = ?";
        String eliminarSql = "DELETE FROM detalle_pedido WHERE id_detalle = ?";
        try (Connection con = conexionBD.getConexion()) {
            con.setAutoCommit(false);
            try {
                int idPedido;
                int idProducto;
                int cantidad;
                try (PreparedStatement ps = con.prepareStatement(detalleSql)) {
                    ps.setInt(1, idDetalle);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next() || !"PENDIENTE".equals(rs.getString("estado"))) {
                            con.rollback();
                            return false;
                        }
                        idPedido = rs.getInt("id_pedido");
                        idProducto = rs.getInt("id_producto");
                        cantidad = rs.getInt("cantidad");
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(devolverSql)) {
                    ps.setInt(1, cantidad);
                    ps.setInt(2, idProducto);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(eliminarSql)) {
                    ps.setInt(1, idDetalle);
                    ps.executeUpdate();
                }
                recalcularTotal(con, idPedido);
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle pendiente: " + e.getMessage());
            return false;
        }
    }

    public List<DetallePedido> listarDetalleComanda(int idComanda) {
        return listarDetalle("dp.id_comanda = ?", idComanda);
    }

    /** Envia a cocina todos los productos que esten en la comanda pendiente. */
    public boolean enviarACocina(int idPedido) {
        String sql = "UPDATE comanda SET estado = 'EN_COCINA', fecha_envio = CURRENT_TIMESTAMP WHERE id_pedido = ? AND estado = 'PENDIENTE'";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al enviar comanda a cocina: " + e.getMessage());
            return false;
        }
    }

    /** Marca una comanda especifica como lista; la cuenta sigue abierta. */
    public boolean marcarListo(int idComanda) {
        String sql = "UPDATE comanda SET estado = 'LISTA', fecha_lista = CURRENT_TIMESTAMP WHERE id_comanda = ? AND estado = 'EN_COCINA'";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idComanda);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al marcar comanda lista: " + e.getMessage());
            return false;
        }
    }

    public List<ComandaResumen> listarComandasCocina() {
        return listarComandasPorEstados("EN_COCINA");
    }

    public List<ComandaResumen> listarComandasPorEstados(String... estados) {
        List<ComandaResumen> comandas = new ArrayList<>();
        if (estados == null || estados.length == 0) return comandas;
        StringBuilder marcas = new StringBuilder();
        for (int i = 0; i < estados.length; i++) {
            if (i > 0) marcas.append(",");
            marcas.append("?");
        }
        String sql = "SELECT c.id_comanda, c.id_pedido, m.numero, u.nombre AS mesero, c.estado, "
                + "COALESCE(c.fecha_envio, c.fecha_hora) AS fecha_hora, COALESCE(SUM(dp.subtotal),0) AS total "
                + "FROM comanda c "
                + "INNER JOIN pedido p ON c.id_pedido = p.id_pedido "
                + "INNER JOIN mesa m ON p.id_mesa = m.id_mesa "
                + "INNER JOIN usuario u ON p.id_usuario = u.id_usuario "
                + "LEFT JOIN detalle_pedido dp ON c.id_comanda = dp.id_comanda "
                + "WHERE c.estado IN (" + marcas + ") "
                + "GROUP BY c.id_comanda, c.id_pedido, m.numero, u.nombre, c.estado, c.fecha_envio, c.fecha_hora "
                + "ORDER BY fecha_hora ASC";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < estados.length; i++) ps.setString(i + 1, estados[i]);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) comandas.add(mapearComanda(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar comandas: " + e.getMessage());
        }
        return comandas;
    }

    /**
     * Cierra la cuenta, registra la venta y libera la mesa. Solo procede si no
     * quedan comandas pendientes ni en cocina.
     */
    public boolean cerrarPedido(int idPedido, int idUsuarioCajero, String observaciones) {
        String pendientesSql = "SELECT COUNT(*) FROM comanda WHERE id_pedido = ? AND estado IN ('PENDIENTE','EN_COCINA')";
        String totalSql = "SELECT COALESCE(SUM(subtotal),0) AS total FROM detalle_pedido WHERE id_pedido = ?";
        String ventaSql = "INSERT INTO venta(id_pedido, id_usuario_cajero, total, observaciones) VALUES (?, ?, ?, ?)";
        String detalleVentaSql = "INSERT INTO detalle_venta(id_venta, id_producto, cantidad, precio_unitario, subtotal) "
                + "SELECT ?, id_producto, cantidad, precio_unitario, subtotal FROM detalle_pedido WHERE id_pedido = ?";
        String pedidoSql = "UPDATE pedido SET estado = 'CERRADO', total = ? WHERE id_pedido = ? AND estado = 'ABIERTO'";
        String mesaSql = "UPDATE mesa m INNER JOIN pedido p ON m.id_mesa = p.id_mesa SET m.estado = 'DISPONIBLE' WHERE p.id_pedido = ?";
        try (Connection con = conexionBD.getConexion()) {
            con.setAutoCommit(false);
            try {
                if (tieneComandasPendientes(con, pendientesSql, idPedido)) {
                    con.rollback();
                    return false;
                }
                double total = obtenerTotal(con, totalSql, idPedido);
                if (total <= 0) {
                    con.rollback();
                    return false;
                }
                int idVenta;
                try (PreparedStatement ps = con.prepareStatement(ventaSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, idPedido);
                    ps.setInt(2, idUsuarioCajero);
                    ps.setDouble(3, total);
                    ps.setString(4, observaciones);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("No se pudo obtener el ID de venta.");
                        idVenta = rs.getInt(1);
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(detalleVentaSql)) {
                    ps.setInt(1, idVenta);
                    ps.setInt(2, idPedido);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(pedidoSql)) {
                    ps.setDouble(1, total);
                    ps.setInt(2, idPedido);
                    if (ps.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(mesaSql)) {
                    ps.setInt(1, idPedido);
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar pedido: " + e.getMessage());
            return false;
        }
    }

    private void validarPedidoAbierto(Connection con, String sql, int idPedido) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !"ABIERTO".equals(rs.getString("estado"))) {
                    throw new SQLException("La cuenta no esta abierta.");
                }
            }
        }
    }

    private int obtenerOcrearComandaPendiente(Connection con, String buscar, String crear, int idPedido) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(buscar)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_comanda");
            }
        }
        try (PreparedStatement ps = con.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idPedido);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) throw new SQLException("No se pudo crear la comanda.");
                return rs.getInt(1);
            }
        }
    }

    private Producto obtenerProductoParaVenta(Connection con, String sql, int idProducto, int cantidad) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || rs.getInt("stock") < cantidad) {
                    throw new SQLException("Producto no disponible o stock insuficiente.");
                }
                Producto producto = new Producto();
                producto.setIdProducto(idProducto);
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                return producto;
            }
        }
    }

    private boolean tieneComandasPendientes(Connection con, String sql, int idPedido) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private double obtenerTotal(Connection con, String sql, int idPedido) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("total") : 0.0;
            }
        }
    }

    private void recalcularTotal(Connection con, int idPedido) throws SQLException {
        String sql = "UPDATE pedido SET total = (SELECT COALESCE(SUM(subtotal),0) FROM detalle_pedido WHERE id_pedido = ?) WHERE id_pedido = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }

    private List<DetallePedido> listarDetalle(String condicion, int id) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT dp.id_detalle, dp.id_pedido, dp.id_producto, pr.nombre, "
                + "dp.cantidad, dp.precio_unitario, dp.subtotal "
                + "FROM detalle_pedido dp INNER JOIN producto pr ON dp.id_producto = pr.id_producto "
                + "WHERE " + condicion + " ORDER BY dp.id_detalle ASC";
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) detalles.add(mapearDetalle(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalle: " + e.getMessage());
        }
        return detalles;
    }

    private Mesa mapearMesa(ResultSet rs) throws SQLException {
        Mesa m = new Mesa();
        m.setIdMesa(rs.getInt("id_mesa"));
        m.setNumero(rs.getInt("numero"));
        m.setCapacidad(rs.getInt("capacidad"));
        m.setEstado(rs.getString("estado"));
        m.setUbicacion(rs.getString("ubicacion"));
        m.setActivo(rs.getBoolean("activo"));
        return m;
    }

    private DetallePedido mapearDetalle(ResultSet rs) throws SQLException {
        DetallePedido d = new DetallePedido();
        d.setIdDetallePedido(rs.getInt("id_detalle"));
        d.setIdPedido(rs.getInt("id_pedido"));
        d.setIdProducto(rs.getInt("id_producto"));
        d.setNombreProducto(rs.getString("nombre"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getDouble("precio_unitario"));
        d.setSubtotal(rs.getDouble("subtotal"));
        return d;
    }

    private PedidoResumen mapearResumen(ResultSet rs) throws SQLException {
        PedidoResumen p = new PedidoResumen();
        p.setIdPedido(rs.getInt("id_pedido"));
        p.setIdMesa(rs.getInt("id_mesa"));
        p.setNumeroMesa(rs.getInt("numero"));
        p.setMesero(rs.getString("mesero"));
        Timestamp ts = rs.getTimestamp("fecha_hora");
        if (ts != null) p.setFechaHora(ts.toLocalDateTime());
        p.setEstado(rs.getString("estado"));
        try {
            p.setEstadoCocina(rs.getString("estado_cocina"));
        } catch (SQLException e) {
            p.setEstadoCocina("");
        }
        p.setTotal(rs.getDouble("total"));
        return p;
    }

    private ComandaResumen mapearComanda(ResultSet rs) throws SQLException {
        ComandaResumen c = new ComandaResumen();
        c.setIdComanda(rs.getInt("id_comanda"));
        c.setIdPedido(rs.getInt("id_pedido"));
        c.setNumeroMesa(rs.getInt("numero"));
        c.setMesero(rs.getString("mesero"));
        c.setEstado(rs.getString("estado"));
        Timestamp ts = rs.getTimestamp("fecha_hora");
        if (ts != null) c.setFechaHora(ts.toLocalDateTime());
        c.setTotal(rs.getDouble("total"));
        return c;
    }
}
