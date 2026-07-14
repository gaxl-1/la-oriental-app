/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.controlador;

import com.fortis.laoriental.dao.MesaDAO;
import com.fortis.laoriental.modelo.Mesa;
import com.fortis.laoriental.util.ResultadoValidacion;
import com.fortis.laoriental.util.Validaciones;
import java.util.List;

/**
 * Este controlador maneja las operaciones de mesas.
 * Valida los datos antes de mandarlos al DAO.
 *
 * @author gael
 */
public class MesaController {

    private final MesaDAO mesaDAO = new MesaDAO();

    /**
     * Guarda una nueva mesa.
     * Valida que el numero y capacidad sean numeros positivos
     * y que el estado sea valido.
     */
    public String guardar(String numeroTexto, String capacidadTexto,
                          String estado, String ubicacion, boolean activo) {
        int numero;
        int capacidad;

        ResultadoValidacion validacion = Validaciones.textoObligatorio(ubicacion, "ubicacion", 100);
        if (!validacion.valido()) return validacion.mensaje();

        // Convertir numero y capacidad de texto a numeros
        try {
            numero = Integer.parseInt(numeroTexto.trim());
            capacidad = Integer.parseInt(capacidadTexto.trim());
        } catch (NumberFormatException e) {
            return "El numero de mesa y la capacidad deben ser numericos.";
        }

        if (numero <= 0) return "El numero de mesa debe ser mayor a cero.";
        if (capacidad <= 0) return "La capacidad debe ser mayor a cero.";

        // Validar que el estado sea DISPONIBLE u OCUPADA
        if (!estado.equals("DISPONIBLE") && !estado.equals("OCUPADA"))
            return "El estado de la mesa no es valido.";

        Mesa mesa = new Mesa(0, numero, capacidad, estado, ubicacion.trim(), activo);

        if (mesaDAO.insertar(mesa)) {
            return "Mesa guardada correctamente.";
        } else {
            return "No se pudo guardar la mesa.";
        }
    }

    /**
     * Actualiza los datos de una mesa existente.
     */
    public String actualizar(int idMesa, String numeroTexto, String capacidadTexto,
                             String estado, String ubicacion, boolean activo) {
        if (idMesa <= 0) return "Seleccione una mesa valida.";

        ResultadoValidacion validacion = Validaciones.textoObligatorio(ubicacion, "ubicacion", 100);
        if (!validacion.valido()) return validacion.mensaje();

        int numero, capacidad;
        try {
            numero = Integer.parseInt(numeroTexto.trim());
            capacidad = Integer.parseInt(capacidadTexto.trim());
        } catch (NumberFormatException e) {
            return "El numero de mesa y la capacidad deben ser numericos.";
        }

        if (numero <= 0) return "El numero de mesa debe ser mayor a cero.";
        if (capacidad <= 0) return "La capacidad debe ser mayor a cero.";

        Mesa mesa = new Mesa(idMesa, numero, capacidad, estado, ubicacion.trim(), activo);

        if (mesaDAO.actualizar(mesa)) {
            return "Mesa actualizada correctamente.";
        } else {
            return "No se pudo actualizar la mesa.";
        }
    }

    /**
     * Cambia el estado de una mesa (DISPONIBLE <-> OCUPADA).
     */
    public String cambiarEstado(int idMesa, String estado) {
        if (idMesa <= 0) return "Seleccione una mesa valida.";
        if (!estado.equals("DISPONIBLE") && !estado.equals("OCUPADA"))
            return "El estado de la mesa no es valido.";

        if (mesaDAO.cambiarEstado(idMesa, estado)) {
            return "Estado actualizado correctamente.";
        } else {
            return "No se pudo cambiar el estado.";
        }
    }

    /**
     * Desactiva una mesa.
     */
    public String desactivar(int idMesa) {
        if (idMesa <= 0) return "Seleccione una mesa valida.";

        if (mesaDAO.desactivar(idMesa)) {
            return "Mesa desactivada correctamente.";
        } else {
            return "No se pudo desactivar la mesa.";
        }
    }

    public String actualizarActivo(int idMesa, boolean activo) {
        if (idMesa <= 0) return "Seleccione una mesa valida.";
        if (mesaDAO.actualizarActivo(idMesa, activo)) {
            return activo ? "Mesa activada correctamente." : "Mesa desactivada correctamente.";
        }
        return "No se pudo actualizar la mesa.";
    }

    public String eliminar(int idMesa) {
        if (idMesa <= 0) return "Seleccione una mesa valida.";
        if (mesaDAO.eliminar(idMesa)) return "Mesa eliminada definitivamente.";
        return "No se puede eliminar definitivamente porque tiene historial. Use Desactivar.";
    }

    /**
     * Obtiene todas las mesas activas.
     */
    public List<Mesa> listarActivas() {
        return mesaDAO.listarActivas();
    }

    public List<Mesa> listarTodas() {
        return mesaDAO.listarTodas();
    }
}
