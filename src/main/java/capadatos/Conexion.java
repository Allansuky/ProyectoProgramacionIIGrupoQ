
package capadatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    private static String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static String user = "root";
    private static String clave = "12345678";
    
    public static Connection conectar() throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            return DriverManager.getConnection(url, user, clave);
        }catch (ClassNotFoundException e){
            throw new SQLException(e.getMessage());
        }
    }
}


