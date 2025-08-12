
package capadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    
    private static String url = "jdbc:mysql://localhost:3306/clientedatabase?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static String user = "allansuky";
    private static String clave = "Allan123*";
    
    public static Connection conectar() throws SQLException {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url, user, clave);
            System.out.println("Conectado a la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return cn;
    }
}


