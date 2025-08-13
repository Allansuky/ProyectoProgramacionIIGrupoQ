/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author quesi
 */
public class CDUsuario {
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDUsuario() throws SQLException {
        this.cn = Conexion.conectar();
    }

    public void insertarUsuario(CLUsuario v) throws SQLException {
        String sql = "{CALL usp_insertarUsuario(?,?,?,?,?)}"; // codVehiculo es autoincrement

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, v.getIdUsuario());
            ps.setString(2, v.getNombreUsuario());
            ps.setString(3, v.getContraseña());
            ps.setBoolean(4, v.isEstado());
            ps.setString(5, v.getRol());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void actualizarUsuario(CLUsuario v) throws SQLException {
        String sql = "{CALL usp_actualizarUsuario(?,?,?,?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, v.getIdUsuario());
            ps.setString(2, v.getNombreUsuario());
            ps.setString(3, v.getContraseña());
            ps.setBoolean(4, v.isEstado());
            ps.setString(5, v.getRol());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void eliminarUsuario(CLUsuario v) throws SQLException {
        String sql = "{CALL usp_eliminarusuario(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, v.getIdUsuario());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public int autoIncrementarUsuarioID() throws SQLException {
        int idUsuario = 0;
        String sql = "{CALL usp_autoIncrementarUsuarioID()}";

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                idUsuario = rs.getInt("idUsuario");
            }
            if (idUsuario == 0) {
                idUsuario = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        return idUsuario;
    }

    public List<CLUsuario> obtenerListaUsuario() throws SQLException {
        String sql = "{CALL usp_mostrarUsuarios()}";
        List<CLUsuario> lista = new ArrayList<>();

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                CLUsuario v = new CLUsuario();
                v.setIdUsuario(rs.getInt("idUsuario"));
                v.setNombreUsuario(rs.getString("nombreUsuario"));
                v.setContraseña(rs.getString("contraseña")); 
                v.setEstado(rs.getBoolean("estado"));        
                v.setRol(rs.getString("rol")); 
                lista.add(v);
}

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return lista;
    }
}
