/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental;

import com.fortis.laoriental.vista.LoginFrame;

/**
 * Esta es la clase principal del programa.
 * Aqui se ejecuta el metodo main() que inicia todo el sistema.
 * Solo abre la ventana de login para que el usuario inicie sesion.
 *
 * @author gael
 */
public class LaOriental {

    /**
     * Metodo principal. Java empieza aqui.
     * Usa SwingUtilities.invokeLater para que la ventana se abra
     * en el hilo correcto de Java Swing.
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
