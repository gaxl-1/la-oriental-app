/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.controlador;

import com.fortis.laoriental.dao.UsuarioDAO;
import com.fortis.laoriental.modelo.Usuario;

/**
 * Este controlador maneja todo lo relacionado con el inicio de sesion.
 * Revisa que los campos no esten vacios y llama al DAO para
 * verificar si el usuario existe en la base de datos.
 *
 * @author gael
 */
public class LoginController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Intenta iniciar sesion con los datos proporcionados.
     * Primero valida que los campos no esten vacios,
     * luego busca al usuario en la base de datos.
     *
     * @param username Nombre de usuario
     * @param contrasena Contrasena del usuario
     * @return El objeto Usuario si las credenciales son correctas
     * @throws Exception Si hay campos vacios o error en la BD
     */
    public Usuario iniciarSesion(String username, String contrasena) throws Exception {
        // Validar que el username no este vacio
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Ingrese el nombre de usuario.");
        }

        // Validar que la contrasena no este vacia
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new Exception("Ingrese la contrasena.");
        }

        // Buscar al usuario en la base de datos
        return usuarioDAO.autenticar(username.trim(), contrasena.trim());
    }
}
