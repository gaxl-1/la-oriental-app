/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.dao;

import com.fortis.laoriental.conexion.ConexionBD;
import com.fortis.laoriental.modelo.Mesa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de las operaciones en la base de datos
 * para la tabla 'mesa'. Aqui estan el CRUD de mesas.
 *
 * @author gael
 */
public class MesaDAO {

    // Conexion a la base de datos
    private final ConexionBD conexionBD = new ConexionBD();

    /**
     * Guarda una nueva mesa en la base de datos.
     *
     * @param mesa La mesa a guardar
     * @return true si se guardo bien
     */
    public boolean insertar(Mesa mesa) {
        String sql = "INSERT INTO mesa(numero, capacidad, estado, ubicacion, activo) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstado());
            ps.setString(4, mesa.getUbicacion());
            ps.setBoolean(5, mesa.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar mesa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todas las mesas activas del restaurante.
     *
     * @return Lista de mesas activas
     */
    public List<Mesa> listarActivas() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id_mesa, numero, capacidad, estado, ubicacion, activo "
                   + "FROM mesa WHERE activo = TRUE ORDER BY numero";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mesa m = new Mesa();
                m.setIdMesa(rs.getInt("id_mesa"));
                m.setNumero(rs.getInt("numero"));
                m.setCapacidad(rs.getInt("capacidad"));
                m.setEstado(rs.getString("estado"));
                m.setUbicacion(rs.getString("ubicacion"));
                m.setActivo(rs.getBoolean("activo"));
                mesas.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar mesas: " + e.getMessage());
        }

        return mesas;
    }

    public List<Mesa> listarTodas() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id_mesa, numero, capacidad, estado, ubicacion, activo "
                   + "FROM mesa ORDER BY numero";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mesa m = new Mesa();
                m.setIdMesa(rs.getInt("id_mesa"));
                m.setNumero(rs.getInt("numero"));
                m.setCapacidad(rs.getInt("capacidad"));
                m.setEstado(rs.getString("estado"));
                m.setUbicacion(rs.getString("ubicacion"));
                m.setActivo(rs.getBoolean("activo"));
                mesas.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar mesas: " + e.getMessage());
        }

        return mesas;
    }

    /**
     * Actualiza los datos de una mesa existente.
     *
     * @param mesa La mesa con los datos nuevos
     * @return true si se actualizo bien
     */
    public boolean actualizar(Mesa mesa) {
        String sql = "UPDATE mesa SET numero = ?, capacidad = ?, estado = ?, "
                   + "ubicacion = ?, activo = ? WHERE id_mesa = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getEstado());
            ps.setString(4, mesa.getUbicacion());
            ps.setBoolean(5, mesa.isActivo());
            ps.setInt(6, mesa.getIdMesa());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar mesa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cambia el estado de una mesa (DISPONIBLE u OCUPADA).
     *
     * @param idMesa ID de la mesa
     * @param estado El nuevo estado
     * @return true si se cambio bien
     */
    public boolean cambiarEstado(int idMesa, String estado) {
        String sql = "UPDATE mesa SET estado = ? WHERE id_mesa = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idMesa);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado de mesa: " + e.getMessage());
            return false;
        }
    }

    /**
     * Desactiva una mesa (borrado logico).
     * La mesa deja de aparecer pero no se borra de la BD.
     *
     * @param idMesa ID de la mesa a desactivar
     * @return true si se desactivo bien
     */
    public boolean desactivar(int idMesa) {
        String sql = "UPDATE mesa SET activo = FALSE WHERE id_mesa = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMesa);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al desactivar mesa: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarActivo(int idMesa, boolean activo) {
        String sql = "UPDATE mesa SET activo = ? WHERE id_mesa = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, idMesa);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar activo de mesa: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idMesa) {
        String sql = "DELETE FROM mesa WHERE id_mesa = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idMesa);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar mesa: " + e.getMessage());
            return false;
        }
    }
}
