/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.util;

import java.util.Set;

/**
 * Esta clase tiene metodos utiles para validar datos
 * antes de mandarlos a la base de datos.
 *
 * @author gael
 */
public class Validaciones {

    private static final Set<String> ROLES = Set.of("administrador", "mesero", "cocina", "cajero");

    // Constructor privado para que nadie pueda crear objetos
    private Validaciones() {}

    /**
     * Revisa si un texto esta vacio o es null.
     *
     * @param valor El texto a revisar
     * @return true si esta vacio o es null
     */
    public static boolean textoVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /**
     * Revisa si un precio es valido (mayor a cero).
     *
     * @param precio El precio a revisar
     * @return true si es mayor a cero
     */
    public static boolean precioValido(double precio) {
        return precio > 0;
    }

    public static boolean enteroPositivo(int valor) {
        return valor > 0;
    }

    public static ResultadoValidacion textoObligatorio(String valor, String campo, int maximo) {
        if (textoVacio(valor)) {
            return ResultadoValidacion.error("El campo " + campo + " es obligatorio.");
        }
        if (valor.trim().length() > maximo) {
            return ResultadoValidacion.error(campo + " no puede exceder " + maximo + " caracteres.");
        }
        return ResultadoValidacion.correcto();
    }

    public static ResultadoValidacion numeroPositivo(String valor, String campo) {
        try {
            int numero = Integer.parseInt(valor);
            if (numero <= 0) {
                return ResultadoValidacion.error(campo + " debe ser mayor que cero.");
            }
            return ResultadoValidacion.correcto();
        } catch (NumberFormatException e) {
            return ResultadoValidacion.error(campo + " debe ser numerico.");
        }
    }

    public static ResultadoValidacion precioValido(String valor) {
        try {
            double precio = Double.parseDouble(valor);
            if (precio <= 0) {
                return ResultadoValidacion.error("El precio debe ser mayor que cero.");
            }
            return ResultadoValidacion.correcto();
        } catch (NumberFormatException e) {
            return ResultadoValidacion.error("El precio debe ser numerico.");
        }
    }

    public static ResultadoValidacion rolValido(String rol) {
        if (rol == null || !ROLES.contains(rol.toLowerCase())) {
            return ResultadoValidacion.error("Seleccione un rol valido.");
        }
        return ResultadoValidacion.correcto();
    }
}
