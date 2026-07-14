/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.controlador;

import com.fortis.laoriental.dao.ProductoDAO;
import com.fortis.laoriental.modelo.Producto;
import com.fortis.laoriental.util.ResultadoValidacion;
import com.fortis.laoriental.util.Validaciones;
import java.util.List;

/**
 * Este controlador maneja las operaciones de productos (menu).
 * Valida los datos antes de mandarlos al DAO.
 *
 * @author gael
 */
public class ProductoController {

    private final ProductoDAO productoDAO = new ProductoDAO();

    /**
     * Guarda un nuevo producto en el menu.
     * Valida que el nombre, categoria y precio esten correctos.
     */
    public String guardar(String nombre, String categoria, String precioTexto, boolean activo) {
        return guardar(nombre, categoria, precioTexto, activo, "0");
    }

    public String guardar(String nombre, String categoria, String precioTexto, boolean activo, String stockTexto) {
        ResultadoValidacion validacion = Validaciones.textoObligatorio(nombre, "nombre del producto", 100);
        if (!validacion.valido()) return validacion.mensaje();
        validacion = Validaciones.textoObligatorio(categoria, "categoria", 50);
        if (!validacion.valido()) return validacion.mensaje();

        // Validar que el precio sea un numero
        double precio;
        try {
            precio = Double.parseDouble(precioTexto.trim());
        } catch (NumberFormatException e) {
            return "El precio debe ser numerico.";
        }

        // Validar que el precio sea mayor a cero
        if (precio <= 0)
            return "El precio debe ser mayor a cero.";

        int stock = convertirStock(stockTexto);
        if (stock < 0) return "El stock debe ser numerico y no negativo.";

        Producto producto = new Producto(0, nombre.trim(), categoria.trim(), precio, activo, stock);

        if (productoDAO.insertar(producto)) {
            return "Producto guardado correctamente.";
        } else {
            return "No se pudo guardar el producto.";
        }
    }

    /**
     * Actualiza los datos de un producto existente.
     */
    public String actualizar(int idProducto, String nombre, String categoria,
                             String precioTexto, boolean activo) {
        return actualizar(idProducto, nombre, categoria, precioTexto, activo, "0");
    }

    public String actualizar(int idProducto, String nombre, String categoria,
                             String precioTexto, boolean activo, String stockTexto) {
        if (idProducto <= 0) return "Seleccione un producto valido.";
        ResultadoValidacion validacion = Validaciones.textoObligatorio(nombre, "nombre del producto", 100);
        if (!validacion.valido()) return validacion.mensaje();
        validacion = Validaciones.textoObligatorio(categoria, "categoria", 50);
        if (!validacion.valido()) return validacion.mensaje();

        double precio;
        try {
            precio = Double.parseDouble(precioTexto.trim());
        } catch (NumberFormatException e) {
            return "El precio debe ser numerico.";
        }

        if (precio <= 0) return "El precio debe ser mayor a cero.";

        int stock = convertirStock(stockTexto);
        if (stock < 0) return "El stock debe ser numerico y no negativo.";

        Producto producto = new Producto(idProducto, nombre.trim(), categoria.trim(), precio, activo, stock);

        if (productoDAO.actualizar(producto)) {
            return "Producto actualizado correctamente.";
        } else {
            return "No se pudo actualizar el producto.";
        }
    }

    /**
     * Desactiva un producto del menu.
     */
    public String desactivar(int idProducto) {
        if (idProducto <= 0) return "Seleccione un producto valido.";

        if (productoDAO.desactivar(idProducto)) {
            return "Producto desactivado correctamente.";
        } else {
            return "No se pudo desactivar el producto.";
        }
    }

    public String actualizarActivo(int idProducto, boolean activo) {
        if (idProducto <= 0) return "Seleccione un producto valido.";
        if (productoDAO.actualizarActivo(idProducto, activo)) {
            return activo ? "Producto activado correctamente." : "Producto desactivado correctamente.";
        }
        return "No se pudo actualizar el estado del producto.";
    }

    public String eliminar(int idProducto) {
        if (idProducto <= 0) return "Seleccione un producto valido.";
        if (productoDAO.eliminar(idProducto)) return "Producto eliminado definitivamente.";
        return "No se puede eliminar definitivamente porque tiene historial. Use Desactivar.";
    }

    /**
     * Obtiene todos los productos activos.
     */
    public List<Producto> listarActivos() {
        return productoDAO.listarActivos();
    }

    public List<Producto> listarTodos() {
        return productoDAO.listarTodos();
    }

    private int convertirStock(String stockTexto) {
        try {
            return Integer.parseInt(stockTexto.trim());
        } catch (Exception e) {
            return -1;
        }
    }
}
