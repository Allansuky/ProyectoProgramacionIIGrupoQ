/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLCiudad;
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
 * @author user
 */
public class CDCiudad {
    // Delcarar las variables de conexion y de consulta.
    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDCiudad() throws SQLException{
        this.cn = Conexion.conectar();
    }
    
    // Metodo para insertar tabla ciudad.
    public void insertarCiudad(CLCiudad cl) throws SQLException {
        String sql = "{CALL sp_insertarCiudad(?)}";
        
        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNombreCiudad());
            ps.execute();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    
    // Metodo Actualizar Ciudad.
    public void actualizarCiudad(CLCiudad cl) throws SQLException {
        String sql = "{CALL sp_actualizarCiudad(?,?)}";
        
        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdCiudad());
            ps.setString(2, cl.getNombreCiudad());
            ps.execute();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    
    // Metodo para eliminar ciudad de la tabla.
    public void eliminarCiudad(CLCiudad cl) throws SQLException {
        String sql = "{CALL sp_eliminarCiudad(?,?)}";
        
        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdCiudad());
            ps.execute();
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }
    
    // Metodo para obtener el id autoIncrementable.
    public int auntoIncrementarCiudadID() throws SQLException {
        
        int idCiudad = 0;
        
        String sql = "{CALL sp_autoIncrementarCiudadID()}";
        
        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            
            idCiudad = rs.getInt("idCiudad");
            
            if(idCiudad == 0) {
                idCiudad = 1;
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        return idCiudad;
    }
    
    // Metodo para poblar de datos la tabla.
    public List<CLCiudad> obtenerListaCiudades() throws SQLException {
        String sql = "{CALL sp_mostrarCiudades()}";
        
        List<CLCiudad> miLista = null;
        
        try{
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            
            miLista = new ArrayList<>();
            
            while (rs.next()) {
                CLCiudad cl = new CLCiudad();
                
                cl.setIdCiudad(rs.getInt("idCiudad"));
                cl.setNombreCiudad(rs.getString("nombreCiudad"));
                miLista.add(cl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        
        return miLista;
    }
    
    // Metodo que permite llenar el combo ciudad
    public List<String> cargarComboCiudades() throws SQLException {
        String sql = "{CALL sp_mostrarCiudades()}";

        List<String> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();
            miLista.add("--Seleccione--");

            while (rs.next()) {
                miLista.add(rs.getString("nombreCiudad"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

        return miLista;
    }
    
    
}
