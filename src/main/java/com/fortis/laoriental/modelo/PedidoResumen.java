package com.fortis.laoriental.modelo;

import java.time.LocalDateTime;

/**
 * Datos resumidos de la cuenta abierta o cerrada de una mesa.
 * El campo estado representa la cuenta (ABIERTO/CERRADO) y estadoCocina
 * resume si hay comandas pendientes, en cocina o listas para cobro.
 */
public class PedidoResumen {

    private int idPedido;
    private int idMesa;
    private int numeroMesa;
    private String mesero;
    private LocalDateTime fechaHora;
    private String estado;
    private String estadoCocina;
    private double total;

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

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
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

    public String getEstadoCocina() {
        return estadoCocina;
    }

    public void setEstadoCocina(String estadoCocina) {
        this.estadoCocina = estadoCocina;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
