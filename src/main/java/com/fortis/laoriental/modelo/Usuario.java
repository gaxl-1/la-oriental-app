/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

/**
 * Esta clase representa a un usuario del sistema.
 * Cada usuario tiene un nombre, username, contraseña, rol y si esta activo.
 * Se usa para guardar los datos que vienen de la tabla 'usuario' de la base de datos.
 *
 * @author gael
 */
public class Usuario {

    // Atributos que coinciden con las columnas de la tabla usuario
    private int idUsuario;      // Identificador unico (automatico en la BD)
    private String nombre;      // Nombre completo del usuario
    private String username;     // Nombre de usuario para iniciar sesion
    private String contrasena;  // Contraseña para iniciar sesion
    private String rol;         // Rol: administrador, mesero, cocina, cajero
    private boolean activo;     // TRUE = activo, FALSE = desactivado

    /**
     * Constructor vacio.
     * Sirve para crear un usuario sin datos y luego llenarlos con los setters.
     */
    public Usuario() {
    }

    /**
     * Constructor con todos los datos.
     * Sirve para crear un usuario y asignarle valores desde el principio.
     */
    public Usuario(int idUsuario, String nombre, String username,
                   String contrasena, String rol, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.username = username;
        this.contrasena = contrasena;
        this.rol = rol;
        this.activo = activo;
    }

    // ===== GETTERS Y SETTERS =====
    // Los getters sirven para OBTENER el valor de un atributo
    // Los setters sirven para CAMBIAR el valor de un atributo

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
