/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

/**
 * Esta clase representa un producto dentro de una venta.
 * Sirve para guardar el historico de lo que se vendio
 * en cada venta realizada.
 *
 * @author gael
 */
public class DetalleVenta {

    // Atributos del detalle de venta
    private int idDetalleVenta;     // Identificador unico
    private int idVenta;            // Venta a la que pertenece
    private int idProducto;         // Producto vendido
    private int cantidad;           // Cuantos se vendieron
    private double precioUnitario;  // Precio de cada producto vendido
    private double subtotal;        // Total de este producto

    /**
     * Constructor vacio.
     */
    public DetalleVenta() {}

    // ===== GETTERS Y SETTERS =====

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
