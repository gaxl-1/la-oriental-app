/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.modelo;

/**
 * Esta clase representa una mesa del restaurante.
 * Cada mesa tiene un numero, capacidad de personas, estado,
 * ubicacion y si esta activa.
 *
 * @author gael
 */
public class Mesa {

    // Atributos de la mesa
    private int idMesa;         // Identificador unico
    private int numero;         // Numero visible de la mesa (ej: 1, 2, 3...)
    private int capacidad;      // Cuantas personas caben
    private String estado;      // DISPONIBLE u OCUPADA
    private String ubicacion;   // Donde esta (ej: "Interior - ventana")
    private boolean activo;     // TRUE = activa, FALSE = desactivada

    /**
     * Constructor vacio.
     */
    public Mesa() {
    }

    /**
     * Constructor con todos los datos.
     */
    public Mesa(int idMesa, int numero, int capacidad, String estado,
                String ubicacion, boolean activo) {
        this.idMesa = idMesa;
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.activo = activo;
    }

    // ===== GETTERS Y SETTERS =====

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
