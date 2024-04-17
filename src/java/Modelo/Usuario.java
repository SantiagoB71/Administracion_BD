/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Config.Conexion; // importamos la clase Conexion del paquete Config
import java.sql.ResultSetMetaData; // importamos la clase ResultSetMetaData del paquete java.sql
import java.sql.Connection; // importamos la clase Connection del paquete java.sql
import java.sql.ResultSet; // importamos la clase ResultSet del paquete java.sql
import java.sql.SQLException; // importamos la clase SQLException del paquete java.sql
import java.sql.Statement; // importamos la clase Statement del paquete java.sql
import java.util.ArrayList; // importamos la clase ArrayList del paquete java.util

/**
 * Clase Usuario que extiende de la clase Persona Esta clase representa un
 * objeto Usuario con sus atributos y métodos
 */
public class Usuario extends Persona {

    String correo, contraseña; // atributos específicos de Usuario
    Conexion conex = new Conexion(); // instancia de la clase Conexion
    Connection connect; // variable que representa la conexión a la base de datos
    Statement sta; // variable para crear las consultas SQL
    ResultSet res; // variable para almacenar el resultado de una consulta

    /**
     * Constructor de la clase Usuario
     *
     * @param nombre Nombre del Usuario
     * @param apellido Apellido del Usuario
     * @param correo Correo electrónico del Usuario
     * @param contraseña Contraseña del Usuario
     */
    public Usuario(String nombre, String apellido, String correo, String contraseña) {
        super(nombre, apellido, correo); // llamamos al constructor de la clase padre Persona
        this.contraseña = contraseña; // asignamos el valor del parámetro contraseña al atributo correspondiente
    }

    /**
     * Constructor sobrecargado de la clase Usuario
     *
     * @param correo Correo electrónico del Usuario
     * @param contraseña Contraseña del Usuario
     */
    public Usuario(String correo, String contraseña) {
        this.correo = correo; // asignamos el valor del parámetro correo al atributo correspondiente
        this.contraseña = contraseña; // asignamos el valor del parámetro contraseña al atributo correspondiente
    }

    /**
     * Método para crear un nuevo Usuario en la base de datos
     *
     * @return true si se crea el Usuario correctamente, false en caso contrario
     */
    public boolean crear() {
        String sql = "INSERT INTO users ( nombre, apellido, correo , "
                + " contraseña)"
                + " VALUES ('" + getNombre() + "','" + getApellido() + "','" + getCorreo() + "',"
                + "'" + this.contraseña + "')"; // consulta SQL para insertar un nuevo Usuario en la base de datos
        try {
            connect = conex.getConnection(); // establecemos la conexión a la base de datos
            sta = connect.createStatement(); // creamos un Statement para ejecutar la consulta
            sta.executeUpdate(sql); // ejecutamos la consulta
            return true; // si la consulta se ejecuta correctamente, retornamos true
        } catch (SQLException e) { // si hay un error en la consulta, capturamos la excepción SQLException
            System.out.println("error = " + e); // imprimimos el error en la consola
            return false; // retornamos false
        }
    }

    /**
     * Método para iniciar sesión de un Usuario
     *
     * @return ArrayList que contiene los datos del Usuario si el inicio de
     * sesión es correcto, o un ArrayList vacío si es incorrecto
     */
    public ArrayList iniciarSesion() {
        // creamos un ArrayList para almacenar los datos del Usuario
        ArrayList resultado = new ArrayList();
        String sql = "SELECT * FROM users WHERE correo = '" + this.correo + "' AND contraseña = " + "'" +this.contraseña + "'";
        try {
            // Obtenemos la conexión a la base de datos
            connect = conex.getConnection();
            // Creamos un objeto Statement a través de la conexión
            sta = connect.createStatement();
            // Ejecutamos la consulta SQL y almacenamos el resultado en un objeto ResultSet
            res = sta.executeQuery(sql);
            // Obtenemos la información de las columnas de la tabla
            ResultSetMetaData md = res.getMetaData();
            int columnas = md.getColumnCount();
            // Iteramos sobre las filas del resultado
            while (res.next()) {
                // Iteramos sobre las columnas de cada fila y las almacenamos en un ArrayList
                for (int i = 1; i <= columnas; i++) {
                    resultado.add(res.getString(i));
                }
            }
            // Cerramos los recursos abiertos
            res.close();
            sta.close();
        } catch (SQLException e) {
            // Si se produce una excepción, imprimimos el mensaje de error en la consola
            System.out.println("error = " + e);
        }
        // Devolvemos el resultado obtenido
        return resultado;
    }
}
