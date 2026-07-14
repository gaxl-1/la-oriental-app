/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.util;

import javax.swing.JOptionPane;

/**
 * Esta clase tiene metodos para mostrar mensajes en pantalla
 * usando cuadros de dialogo (pop-ups) de Java Swing.
 *
 * @author gael
 */
public class Mensajes {

    // Constructor privado para que nadie pueda crear objetos
    private Mensajes() {}

    /**
     * Muestra un mensaje de informacion (con un icono azul de "i").
     *
     * @param mensaje El texto a mostrar
     */
    public static void info(String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            "Informacion",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra un mensaje de error (con un icono rojo de "X").
     *
     * @param mensaje El texto a mostrar
     */
    public static void error(String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
