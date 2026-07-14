package com.fortis.laoriental.controlador;

import com.fortis.laoriental.dao.PedidoDAO;
import com.fortis.laoriental.modelo.ComandaResumen;
import com.fortis.laoriental.modelo.DetallePedido;
import com.fortis.laoriental.modelo.Mesa;
import com.fortis.laoriental.modelo.PedidoResumen;
import com.fortis.laoriental.servicio.ValidadorPedido;
import com.fortis.laoriental.util.ResultadoValidacion;
import java.sql.SQLException;
import java.util.List;

/**
 * Controlador del flujo de cuentas, comandas y cobro.
 *
 * Esta clase es el punto intermedio entre las pantallas Swing y PedidoDAO.
 * Su trabajo es validar datos simples que vienen de la interfaz, convertir
 * textos a numeros cuando hace falta y delegar la operacion real a la capa DAO.
 */
public class PedidoController {

    private final PedidoDAO pedidoDAO = new PedidoDAO();

    /**
     * Obtiene mesas que pueden recibir una nueva cuenta.
     *
     * @return mesas activas que no tienen un pedido/cuenta ABIERTO
     */
    public List<Mesa> listarMesasDisponibles() {
        return pedidoDAO.listarMesasDisponibles();
    }

    /**
     * Lista las cuentas abiertas para que el mesero pueda agregar productos,
     * enviar comandas a cocina o revisar el estado de la cuenta.
     */
    public List<PedidoResumen> listarPedidosActivos() {
        return pedidoDAO.listarPedidosPorEstados("ABIERTO");
    }

    /**
     * Lista comandas en estado EN_COCINA para la pantalla de cocina.
     */
    public List<ComandaResumen> listarComandasCocina() {
        return pedidoDAO.listarComandasCocina();
    }

    /**
     * Crea una cuenta para una mesa.
     *
     * @param idMesa mesa seleccionada
     * @param idUsuario usuario mesero que toma la cuenta
     * @return ID generado del pedido/cuenta
     * @throws SQLException si los datos son invalidos o la mesa ya tiene cuenta
     */
    public int crearPedidoPorMesa(int idMesa, int idUsuario) throws SQLException {
        if (idMesa <= 0) throw new SQLException("Seleccione una mesa valida.");
        if (idUsuario <= 0) throw new SQLException("No hay usuario en sesion.");
        return pedidoDAO.crearPedidoPorMesa(idMesa, idUsuario);
    }

    /**
     * Agrega un producto a la cuenta abierta. La cantidad llega como texto
     * porque viene desde JOptionPane o controles Swing.
     */
    public boolean agregarProducto(int idPedido, int idProducto, String cantidadTexto) {
        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            if (idPedido <= 0 || idProducto <= 0 || cantidad <= 0) return false;
            return pedidoDAO.agregarProducto(idPedido, idProducto, cantidad);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Edita la cantidad de un detalle que aun no se envio a cocina.
     * Si la comanda ya fue enviada, PedidoDAO rechazara el cambio.
     */
    public boolean editarDetallePendiente(int idDetalle, String cantidadTexto) {
        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            if (idDetalle <= 0 || cantidad <= 0) return false;
            return pedidoDAO.editarDetallePendiente(idDetalle, cantidad);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Quita un producto del pedido antes de enviarlo a cocina.
     */
    public boolean eliminarDetallePendiente(int idDetalle) {
        return pedidoDAO.eliminarDetallePendiente(idDetalle);
    }

    /** Lista todos los productos acumulados en una cuenta. */
    public List<DetallePedido> listarDetallePedido(int idPedido) {
        return pedidoDAO.listarDetallePedido(idPedido);
    }

    /** Lista solamente los productos de una comanda especifica. */
    public List<DetallePedido> listarDetalleComanda(int idComanda) {
        return pedidoDAO.listarDetalleComanda(idComanda);
    }

    /** Envia a cocina la comanda pendiente de una cuenta. */
    public boolean enviarACocina(int idPedido) {
        return pedidoDAO.enviarACocina(idPedido);
    }

    /** Marca una comanda como lista desde la pantalla de cocina. */
    public boolean marcarListo(int idComanda) {
        return pedidoDAO.marcarListo(idComanda);
    }

    /** Lista cuentas que ya no tienen comandas pendientes ni en cocina. */
    public List<PedidoResumen> listarPedidosParaCuenta() {
        return pedidoDAO.listarPedidosListosParaCobro();
    }

    /**
     * Cierra una cuenta, genera venta y libera la mesa.
     *
     * @param idPedido cuenta a cerrar
     * @param idUsuarioCajero usuario que cobra
     * @param observaciones notas de pago o cierre
     * @return true si se registro la venta correctamente
     */
    public boolean cerrarPedido(int idPedido, int idUsuarioCajero, String observaciones) {
        ResultadoValidacion validacion = ValidadorPedido.validarCierrePedido(
            pedidoDAO.listarDetallePedido(idPedido).size(), true
        );
        if (!validacion.valido()) return false;
        return pedidoDAO.cerrarPedido(idPedido, idUsuarioCajero, observaciones);
    }
}
