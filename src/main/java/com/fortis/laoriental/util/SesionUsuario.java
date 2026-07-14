/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.util;

import com.fortis.laoriental.modelo.Usuario;

/**
 * Esta clase guarda al usuario que inicio sesion.
 * Es como una "sesion" que dura mientras el programa esta abierto.
 * Solamente puede haber un usuario conectado a la vez (singleton).
 *
 * @author gael
 */
public class SesionUsuario {

    // Aqui se guarda el usuario que esta usando el sistema ahorita
    private static Usuario usuarioActual;

    // Constructor privado para que nadie pueda crear objetos de esta clase
    private SesionUsuario() {}

    /**
     * Devuelve el usuario que esta actualmente en sesion.
     *
     * @return El usuario actual, o null si no hay nadie
     */
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean estaActiva() {
        return usuarioActual != null;
    }

    public static boolean tieneRol(String rol) {
        return usuarioActual != null && usuarioActual.getRol() != null
                && usuarioActual.getRol().equalsIgnoreCase(rol);
    }

    public static boolean esAdministrador() {
        return tieneRol("administrador");
    }

    public static boolean esMesero() {
        return tieneRol("mesero");
    }

    public static boolean esCocina() {
        return tieneRol("cocina");
    }

    public static boolean esCajero() {
        return tieneRol("cajero");
    }

    /**
     * Guarda al usuario que acaba de iniciar sesion.
     *
     * @param usuario El usuario que se conecto
     */
    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    /**
     * Cierra la sesion del usuario actual.
     * Se llama cuando el usuario cierra sesion o sale del programa.
     */
    public static void cerrarSesion() {
        usuarioActual = null;
    }
}
