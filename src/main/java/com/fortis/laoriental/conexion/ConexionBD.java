/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fortis.laoriental.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase encargada de conectar con la base de datos MySQL.
 * Lee los datos de conexion desde el archivo config.properties
 * que esta en la carpeta resources.
 *
 * @author gael
 */
public class ConexionBD {

    // Variables donde se guardan los datos de conexion
    private final String HOST;
    private final String USUARIO;
    private final String PASSWORD;
    private final String BASE_DATOS;
    private final String PARAMS;
    private final String URL;

    // Este bloque se ejecuta UNA SOLA VEZ cuando se carga la clase
    // Sirve para leer el archivo config.properties
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream is = ConexionBD.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new IllegalStateException("No se encontro el archivo config.properties");
            }
            CONFIG.load(is);
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(
                    "Error al leer config.properties: " + ex.getMessage()
            );
        }
    }

    /**
     * Constructor: toma los valores del archivo config.properties
     * y arma la URL de conexion a MySQL.
     */
    public ConexionBD() {
        this.HOST = CONFIG.getProperty("dataBase.host");
        this.USUARIO = CONFIG.getProperty("dataBase.usuario");
        this.PASSWORD = CONFIG.getProperty("dataBase.password");
        this.BASE_DATOS = CONFIG.getProperty("dataBase.nombre");
        this.PARAMS = CONFIG.getProperty("dataBase.params");

        // Ejemplo de como queda la URL:
        // jdbc:mysql://localhost:3306/fortis_la_oriental?useSSL=false&serverTimezone=UTC
        this.URL = "jdbc:mysql://" + HOST + "/" + BASE_DATOS + PARAMS;

    }

    /**
     * Este metodo abre la conexion con la base de datos.
     * Si ya hay una conexion abierta, la reutiliza.
     */
    public Connection conectar() throws SQLException {
        Connection nuevaConexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        System.out.println("¡Conexion exitosa a la base de datos!");
        return nuevaConexion;
    }

    /**
     * Devuelve la conexion actual.
     * Si no hay conexion, primero la crea.
     */
    public Connection getConexion() throws SQLException {
        return conectar();
    }

    /**
     * Cierra la conexion si esta abierta.
     */
    public void desconectar() {
        System.out.println("Las conexiones se cierran automaticamente con try-with-resources.");
    }
}
