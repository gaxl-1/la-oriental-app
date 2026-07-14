/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

/**
 * Esta clase representa un producto dentro de un pedido.
 * Un pedido puede tener varios detalles (varios productos).
 * Aqui se guarda cuantos productos se pidieron y a que precio.
 *
 * @author gael
 */
public class DetallePedido {

    // Atributos del detalle del pedido
    private int idDetallePedido;    // Identificador unico
    private int idPedido;           // Pedido al que pertenece
    private int idProducto;         // Producto que se pidio
    private String nombreProducto;  // Nombre del producto para mostrar en vistas
    private int cantidad;           // Cuantos se pidieron
    private double precioUnitario;  // Precio de cada uno
    private double subtotal;        // cantidad * precioUnitario

    /**
     * Constructor vacio.
     */
    public DetallePedido() {}

    /**
     * Calcula el subtotal de este detalle.
     * Subtotal = cantidad * precioUnitario
     */
    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }

    // ===== GETTERS Y SETTERS =====

    public int getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(int idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
