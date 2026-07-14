/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.dao;

import com.fortis.laoriental.conexion.ConexionBD;
import com.fortis.laoriental.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de hacer las operaciones en la base de datos
 * para la tabla 'usuario'. Aqui estan el login y el CRUD completo.
 *
 * DAO significa Data Access Object (Objeto de Acceso a Datos).
 *
 * @author gael
 */
public class UsuarioDAO {

    // Conexion a la base de datos
    private final ConexionBD conexionBD = new ConexionBD();

    /**
     * Busca un usuario por su username y contraseña.
     * Esto se usa para el inicio de sesion.
     *
     * @param username El nombre de usuario
     * @param contrasena La contraseña
     * @return El usuario si encontro match, o null si no
     */
    public Usuario autenticar(String username, String contrasena) throws SQLException {
        // Consulta SQL: busca un usuario activo con ese username y contraseña
        String sql = "SELECT id_usuario, nombre, username, contrasena, rol, activo "
                   + "FROM usuario WHERE username = ? AND contrasena = ? AND activo = TRUE";

        // try-with-resources: el Connection y PreparedStatement se cierran solos
        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Los signos de ? se reemplazan con los valores
            ps.setString(1, username);
            ps.setString(2, contrasena);

            // Ejecuta la consulta y obtiene los resultados
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si encontro un usuario, lo convierte de la BD a un objeto Java
                    return mapearUsuario(rs);
                }
            }
        }
        return null; // No encontro nada
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a guardar
     * @return true si se guardo bien, false si no
     */
    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario(nombre, username, contrasena, rol, activo) "
                   + "VALUES (?, ?, ?, ?, TRUE)";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getRol());

            // executeUpdate devuelve cuantas filas se afectaron
            // Si es mayor a 0, significa que se inserto correctamente
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return Lista con todos los usuarios
     */
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, username, contrasena, rol, activo "
                   + "FROM usuario ORDER BY id_usuario";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Recorre todas las filas que devolvio la consulta
            while (rs.next()) {
                // Convierte cada fila a un objeto Usuario y lo agrega a la lista
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario El usuario con los datos nuevos
     * @return true si se actualizo bien
     */
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ?, username = ?, "
                   + "contrasena = ?, rol = ?, activo = ? WHERE id_usuario = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getRol());
            ps.setBoolean(5, usuario.isActivo());
            ps.setInt(6, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Desactiva un usuario (no lo borra, solo pone activo = FALSE).
     * Esto se llama "borrado logico" y sirve para no perder el historial.
     *
     * @param idUsuario El ID del usuario a desactivar
     * @return true si se desactivo bien
     */
    public boolean desactivar(int idUsuario) throws SQLException {
        String sql = "UPDATE usuario SET activo = FALSE WHERE id_usuario = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizarActivo(int idUsuario, boolean activo) throws SQLException {
        String sql = "UPDATE usuario SET activo = ? WHERE id_usuario = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Verifica si ya existe un username en la base de datos.
     * Sirve para no tener dos usuarios con el mismo nombre de usuario.
     *
     * @param username El username a verificar
     * @param idIgnorar ID a ignorar (cuando se edita, ignoramos al mismo usuario)
     * @return true si ya existe
     */
    public boolean existeUsername(String username, int idIgnorar) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE username = ? AND id_usuario <> ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, idIgnorar);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Metodo privado que convierte una fila de la base de datos
     * en un objeto Usuario de Java.
     *
     * @param rs El resultado de la consulta
     * @return Un objeto Usuario con los datos
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setUsername(rs.getString("username"));
        u.setContrasena(rs.getString("contrasena"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
