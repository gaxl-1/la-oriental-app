/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

import java.time.LocalDateTime;

/**
 * Esta clase representa una venta final.
 * Cuando un pedido se cierra y se cobra, se genera una venta.
 *
 * @author gael
 */
public class Venta {

    // Atributos de la venta
    private int idVenta;                // Identificador unico
    private int idPedido;               // Pedido que se esta cobrando
    private int idUsuarioCajero;        // Cajero que realizo el cobro
    private LocalDateTime fechaHora;    // Fecha y hora del cobro
    private double total;               // Total cobrado
    private String observaciones;       // Nota u observacion del cobro

    /**
     * Constructor vacio.
     */
    public Venta() {}

    // ===== GETTERS Y SETTERS =====

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdUsuarioCajero() {
        return idUsuarioCajero;
    }

    public void setIdUsuarioCajero(int idUsuarioCajero) {
        this.idUsuarioCajero = idUsuarioCajero;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
