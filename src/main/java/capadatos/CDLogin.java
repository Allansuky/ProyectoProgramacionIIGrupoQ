
package capadatos;

import capalogica.CLLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author quesi
 */
public class CDLogin {
    private final Connection cn;
    
    public CDLogin() throws SQLException, ClassNotFoundException{
        this.cn = Conexion.conectar();
    }
    
    public String iniciarSesion(CLLogin ll) throws SQLException{
        
        String sql = "{Call sp_validarUsuario(?,?)}";
        
        int userID = 0;
        String userName = null;
        
        try(PreparedStatement ps = cn.prepareStatement(sql)){
            ps.setString(1, ll.getNombreUsuario());
            ps.setString(2, ll.getContraseña());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                userID = rs.getInt("userID");
                userName = rs.getString("userName");
                
            }
        }
        return (String.valueOf(userID) + "-" + userName);
    }           
}
