package com.fortis.laoriental.util;

/**
 * Resultado estandar para validaciones de negocio.
 *
 * Permite devolver dos datos juntos: si la validacion fue correcta y el mensaje
 * que debe mostrarse al usuario cuando no lo fue.
 */
public record ResultadoValidacion(boolean valido, String mensaje) {

    /** Crea una respuesta de validacion exitosa. */
    public static ResultadoValidacion correcto() {
        return new ResultadoValidacion(true, "");
    }

    /** Crea una respuesta de validacion fallida con mensaje visible. */
    public static ResultadoValidacion error(String mensaje) {
        return new ResultadoValidacion(false, mensaje);
    }
}
