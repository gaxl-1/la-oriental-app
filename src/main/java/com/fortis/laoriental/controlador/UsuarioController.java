/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.controlador;

import com.fortis.laoriental.dao.UsuarioDAO;
import com.fortis.laoriental.modelo.Usuario;
import com.fortis.laoriental.util.ResultadoValidacion;
import com.fortis.laoriental.util.Validaciones;
import java.sql.SQLException;
import java.util.List;

/**
 * Este controlador maneja las operaciones de usuarios.
 * Aqui se validan los datos antes de mandarlos al DAO.
 *
 * @author gael
 */
public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Guarda un nuevo usuario. Primero valida los datos.
     *
     * @return Mensaje diciendo si se guardo o que falta
     */
    public String guardar(String nombre, String username, String contrasena, String rol) {
        try {
            ResultadoValidacion validacion = Validaciones.textoObligatorio(nombre, "nombre", 100);
            if (!validacion.valido()) return validacion.mensaje();
            validacion = Validaciones.textoObligatorio(username, "username", 50);
            if (!validacion.valido()) return validacion.mensaje();
            validacion = Validaciones.textoObligatorio(contrasena, "contrasena", 255);
            if (!validacion.valido()) return validacion.mensaje();
            validacion = Validaciones.rolValido(rol);
            if (!validacion.valido()) return validacion.mensaje();

            // Verificar que el username no este repetido
            if (usuarioDAO.existeUsername(username.trim(), 0))
                return "El username ya existe.";

            // Crear el usuario y guardarlo
            Usuario u = new Usuario(0, nombre.trim(), username.trim(),
                                    contrasena.trim(), rol, true);

            if (usuarioDAO.insertar(u)) {
                return "Usuario guardado correctamente.";
            } else {
                return "No se pudo guardar el usuario.";
            }

        } catch (SQLException e) {
            return "Error de base de datos: " + e.getMessage();
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     */
    public String actualizar(int id, String nombre, String username,
                             String contrasena, String rol) {
        return actualizar(id, nombre, username, contrasena, rol, true);
    }

    public String actualizar(int id, String nombre, String username,
                             String contrasena, String rol, boolean activo) {
        try {
            if (id <= 0) return "Seleccione un usuario de la tabla.";
            ResultadoValidacion validacion = Validaciones.textoObligatorio(nombre, "nombre", 100);
            if (!validacion.valido()) return validacion.mensaje();
            validacion = Validaciones.textoObligatorio(username, "username", 50);
            if (!validacion.valido()) return validacion.mensaje();
            validacion = Validaciones.textoObligatorio(contrasena, "contrasena", 255);
            if (!validacion.valido()) return validacion.mensaje();
            validacion = Validaciones.rolValido(rol);
            if (!validacion.valido()) return validacion.mensaje();

            // Verificar que el username no este repetido (ignorando este usuario)
            if (usuarioDAO.existeUsername(username.trim(), id))
                return "El username ya existe.";

            Usuario u = new Usuario(id, nombre.trim(), username.trim(),
                                    contrasena.trim(), rol, activo);

            if (usuarioDAO.actualizar(u)) {
                return "Usuario actualizado correctamente.";
            } else {
                return "No se pudo actualizar.";
            }

        } catch (SQLException e) {
            return "Error de base de datos: " + e.getMessage();
        }
    }

    /**
     * Desactiva un usuario (no lo borra).
     */
    public String desactivar(int id) {
        try {
            if (id <= 0) return "Seleccione un usuario de la tabla.";

            if (usuarioDAO.desactivar(id)) {
                return "Usuario desactivado correctamente.";
            } else {
                return "No se pudo desactivar.";
            }

        } catch (SQLException e) {
            return "Error de base de datos: " + e.getMessage();
        }
    }

    public String actualizarActivo(int id, boolean activo) {
        try {
            if (id <= 0) return "Seleccione un usuario de la tabla.";
            if (usuarioDAO.actualizarActivo(id, activo)) {
                return activo ? "Usuario activado correctamente." : "Usuario desactivado correctamente.";
            }
            return "No se pudo actualizar el estado del usuario.";
        } catch (SQLException e) {
            return "Error de base de datos: " + e.getMessage();
        }
    }

    public String eliminar(int id) {
        try {
            if (id <= 0) return "Seleccione un usuario de la tabla.";
            if (usuarioDAO.eliminar(id)) return "Usuario eliminado definitivamente.";
            return "No se pudo eliminar el usuario.";
        } catch (SQLException e) {
            return "No se puede eliminar definitivamente porque tiene historial. Use Desactivar.";
        }
    }

    /**
     * Obtiene la lista de todos los usuarios.
     */
    public List<Usuario> listar() throws SQLException {
        return usuarioDAO.listar();
    }
}
