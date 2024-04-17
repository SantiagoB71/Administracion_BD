    
package Config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    
    Connection con;

    public Conexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String conexionUrl 
                    = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=admindb;"
                    + "user=sa;"
                    + "password =123;"
                    + "encrypt=true;"
                    + "trustServerCetificate=true";
            con = DriverManager.getConnection(conexionUrl);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }   
    }
   
    public Connection getConnection() {
       return con;
    } 
}
