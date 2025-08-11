/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLProveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDProveedor {

    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDProveedor() throws SQLException {
        this.cn = Conexion.conectar();
    }

    // Metodo para insertar tabla proveedor.
    public void insertarProveedor(CLProveedor cl) throws SQLException {
        String sql = "{CALL usp_insertarProveedor(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNombreProveedor());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    // Metodo Actualizar proveedor.
    public void actualizarProveedor(CLProveedor cl) throws SQLException {
        String sql = "{CALL usp_actualizarProveedor(?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdProveedor());
            ps.setString(2, cl.getNombreProveedor());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    // Metodo para eliminar proveedor de la tabla.
    public void eliminarProveedor(CLProveedor cl) throws SQLException {
        String sql = "{CALL usp_eliminarProveedor(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdProveedor());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    // Metodo para obtener el id autoIncrementable.
    public int auntoIncrementarProveedorID() throws SQLException {
        int idProveedor = 0;

        String sql = "{CALL usp_autoIncrementarProveedorID()}";

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();

            idProveedor = rs.getInt("idProveedor");

            if (idProveedor == 0) {
                idProveedor = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        return idProveedor;
    }

    // Metodo para poblar de datos la tabla.
    public List<CLProveedor> obtenerListaProveedor() throws SQLException {
        String sql = "{CALL usp_mostrarProveedor()}";

        List<CLProveedor> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();

            while (rs.next()) {
                CLProveedor cl = new CLProveedor();

                cl.setIdProveedor(rs.getInt("idProveedor"));
                cl.setNombreProveedor(rs.getString("nombreProveedor"));
                miLista.add(cl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

        return miLista;
    }

    // Metodo que permite llenar el combo proveedor
    public List<String> cargarComboProveedor() throws SQLException {
        String sql = "{CALL usp_mostrarProveedor()}";

        List<String> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();
            miLista.add("--Seleccione--");

            while (rs.next()) {
                miLista.add(rs.getString("nombreProveedor"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

        return miLista;
    }
}
