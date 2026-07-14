/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

import java.time.LocalDateTime;

/**
 * Esta clase representa la cuenta abierta de una mesa.
 * Los envios a cocina se manejan por separado mediante comandas.
 *
 * @author gael
 */
public class Pedido {

    // Atributos del pedido
    private int idPedido;               // Identificador unico
    private int idMesa;                 // Mesa donde se hizo el pedido
    private int idUsuario;              // Mesero que tomo el pedido
    private LocalDateTime fechaHora;    // Fecha y hora en que se creo
    private String estado;              // ABIERTO, CERRADO
    private double total;               // Total del pedido

    /**
     * Constructor vacio.
     */
    public Pedido() {}

    // ===== GETTERS Y SETTERS =====

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
