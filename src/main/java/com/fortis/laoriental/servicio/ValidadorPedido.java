package com.fortis.laoriental.servicio;

import com.fortis.laoriental.util.ResultadoValidacion;

/**
 * Reglas de validacion del flujo de pedidos/cuentas.
 *
 * Se separa de los JFrame para que las reglas no dependan de Swing y puedan
 * reutilizarse desde controladores o pruebas.
 */
public final class ValidadorPedido {

    private ValidadorPedido() {}

    /**
     * Valida si puede crearse una cuenta nueva para una mesa.
     */
    public static ResultadoValidacion validarAltaPedido(int idMesa, boolean mesaDisponible, boolean existePedidoActivo) {
        if (idMesa <= 0) {
            return ResultadoValidacion.error("Seleccione una mesa.");
        }
        if (!mesaDisponible) {
            return ResultadoValidacion.error("La mesa no esta disponible.");
        }
        if (existePedidoActivo) {
            return ResultadoValidacion.error("La mesa ya tiene un pedido activo.");
        }
        return ResultadoValidacion.correcto();
    }

    /**
     * Valida si una cuenta tiene partidas y total consistente antes de cobrarla.
     */
    public static ResultadoValidacion validarCierrePedido(int numeroPartidas, boolean totalCorrecto) {
        if (numeroPartidas <= 0) {
            return ResultadoValidacion.error("No se puede cerrar un pedido vacio.");
        }
        if (!totalCorrecto) {
            return ResultadoValidacion.error("El total del pedido debe verificarse antes de cerrar.");
        }
        return ResultadoValidacion.correcto();
    }
}
