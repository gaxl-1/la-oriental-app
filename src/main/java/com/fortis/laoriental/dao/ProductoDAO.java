/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.dao;

import com.fortis.laoriental.conexion.ConexionBD;
import com.fortis.laoriental.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de las operaciones en la base de datos
 * para la tabla 'producto'. Aqui estan el CRUD de productos.
 *
 * @author gael
 */
public class ProductoDAO {

    // Conexion a la base de datos
    private final ConexionBD conexionBD = new ConexionBD();

    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param producto El producto a guardar
     * @return true si se guardo bien
     */
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO producto(nombre, categoria, precio, activo, stock) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setDouble(3, producto.getPrecio());
            ps.setBoolean(4, producto.isActivo());
            ps.setInt(5, producto.getStock());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los productos activos del menu.
     *
     * @return Lista de productos activos
     */
    public List<Producto> listarActivos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, categoria, precio, activo, stock "
                   + "FROM producto WHERE activo = TRUE AND stock > 0 ORDER BY nombre";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setActivo(rs.getBoolean("activo"));
                p.setStock(rs.getInt("stock"));
                productos.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return productos;
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto El producto con los datos nuevos
     * @return true si se actualizo bien
     */
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, categoria = ?, "
                   + "precio = ?, activo = ?, stock = ? WHERE id_producto = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setDouble(3, producto.getPrecio());
            ps.setBoolean(4, producto.isActivo());
            ps.setInt(5, producto.getStock());
            ps.setInt(6, producto.getIdProducto());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Desactiva un producto (borrado logico).
     * El producto deja de aparecer en el menu pero no se borra de la BD.
     *
     * @param idProducto ID del producto a desactivar
     * @return true si se desactivo bien
     */
    public boolean desactivar(int idProducto) {
        String sql = "UPDATE producto SET activo = FALSE WHERE id_producto = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al desactivar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarActivo(int idProducto, boolean activo) {
        String sql = "UPDATE producto SET activo = ? WHERE id_producto = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado de producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, categoria, precio, activo, stock "
                   + "FROM producto ORDER BY nombre";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setActivo(rs.getBoolean("activo"));
                p.setStock(rs.getInt("stock"));
                productos.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return productos;
    }

    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (Connection con = conexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
