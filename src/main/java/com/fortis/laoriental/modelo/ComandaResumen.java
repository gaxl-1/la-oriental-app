package com.fortis.laoriental.modelo;

import java.time.LocalDateTime;

/**
 * Resumen de una comanda enviada a cocina.
 * Una cuenta/pedido puede tener varias comandas: por ejemplo, una primera
 * ronda de tacos y despues otra ronda de bebidas o extras. Esta clase se usa
 * para mostrar esas tandas en CocinaFrame sin mezclarla con la cuenta completa.
 */
public class ComandaResumen {

    private int idComanda;
    private int idPedido;
    private int numeroMesa;
    private String mesero;
    private String estado;
    private LocalDateTime fechaHora;
    private double total;

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
