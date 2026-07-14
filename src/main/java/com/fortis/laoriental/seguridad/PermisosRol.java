package com.fortis.laoriental.seguridad;

import java.util.Map;
import java.util.Set;

/**
 * Tabla central de permisos por rol.
 *
 * Cada boton del menu consulta esta clase para saber si debe habilitarse. Asi
 * los permisos no quedan repartidos en varias pantallas.
 */
public final class PermisosRol {

    private static final Map<String, Set<String>> PERMISOS = Map.of(
        "administrador", Set.of("USUARIOS", "PRODUCTOS", "MESAS", "PEDIDOS", "COCINA", "CUENTA", "REPORTES"),
        "mesero", Set.of("MESAS", "PEDIDOS"),
        "cocina", Set.of("COCINA"),
        "cajero", Set.of("CUENTA", "REPORTES")
    );

    private PermisosRol() {}

    /**
     * Indica si un rol puede entrar a un modulo del sistema.
     *
     * @param rol rol del usuario autenticado
     * @param modulo nombre logico del modulo, por ejemplo PEDIDOS o REPORTES
     */
    public static boolean puedeAcceder(String rol, String modulo) {
        if (rol == null || modulo == null) return false;
        return PERMISOS.getOrDefault(rol.toLowerCase(), Set.of()).contains(modulo.toUpperCase());
    }
}
