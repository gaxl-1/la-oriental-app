/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

/**
 * Esta clase representa un producto del menu de la taqueria.
 * Cada producto tiene nombre, categoria, precio y si esta activo.
 *
 * @author gael
 */
public class Producto {

    // Atributos del producto
    private int idProducto;     // Identificador unico (automatico en la BD)
    private String nombre;      // Nombre del producto (ej: "Taco al pastor")
    private String categoria;   // Categoria (ej: "Tacos", "Bebidas")
    private double precio;      // Precio del producto
    private boolean activo;     // TRUE = disponible, FALSE = desactivado
    private int stock;          // Existencia disponible para venta

    /**
     * Constructor vacio.
     */
    public Producto() {
    }

    /**
     * Constructor con todos los datos.
     */
    public Producto(int idProducto, String nombre, String categoria,
                    double precio, boolean activo) {
        this(idProducto, nombre, categoria, precio, activo, 0);
    }

    public Producto(int idProducto, String nombre, String categoria,
                    double precio, boolean activo, int stock) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.activo = activo;
        this.stock = stock;
    }

    // ===== GETTERS Y SETTERS =====

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
