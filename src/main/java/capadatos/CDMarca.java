/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLMarca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDMarca {

    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDMarca() throws SQLException {
        this.cn = Conexion.conectar();
    }

    public void insertarMarca(CLMarca cl) throws SQLException {
        String sql = "{CALL usp_insertarMarca(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNombreMarca());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void actualizarMarca(CLMarca cl) throws SQLException {
        String sql = "{CALL usp_actualizarMarca(?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdMarca());
            ps.setString(2, cl.getNombreMarca());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void eliminarMarca(CLMarca cl) throws SQLException {
        String sql = "{CALL usp_eliminarMarca(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdMarca());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public int auntoIncrementarMarcaID() throws SQLException {
        int idMarca = 0;

        String sql = "{CALL usp_autoIncrementarMarcaID()}";

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();

            idMarca = rs.getInt("idMarca");

            if (idMarca == 0) {
                idMarca = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        return idMarca;
    }

    public List<CLMarca> obtenerListaMarca() throws SQLException {
        String sql = "{CALL usp_mostrarMarca()}";

        List<CLMarca> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();

            while (rs.next()) {
                CLMarca cl = new CLMarca();

                cl.setIdMarca(rs.getInt("idMarca"));
                cl.setNombreMarca(rs.getString("nombreMarca"));
                miLista.add(cl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

        return miLista;
    }

    public List<String> cargarComboMarca() throws SQLException {
        String sql = "{CALL usp_mostrarMarca()}";

        List<String> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();
            miLista.add("--Seleccione--");

            while (rs.next()) {
                miLista.add(rs.getString("nombreMarca"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

        return miLista;
    }
}
