/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLVehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDVehiculo {

    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDVehiculo() throws SQLException {
        this.cn = Conexion.conectar();
    }

    public void insertarVehiculo(CLVehiculo v) throws SQLException {
        String sql = "{CALL usp_insertarVehiculo(?,?,?,?)}"; // codVehiculo es autoincrement

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, v.getMarcaVehiculo());
            ps.setString(2, v.getModelo());
            ps.setDouble(3, v.getPrecioUnitario());
            ps.setInt(4, v.getCantidad());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void actualizarVehiculo(CLVehiculo v) throws SQLException {
        String sql = "{CALL usp_actualizarVehiculo(?,?,?,?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, v.getCodVehiculo());
            ps.setString(2, v.getMarcaVehiculo());
            ps.setString(3, v.getModelo());
            ps.setDouble(4, v.getPrecioUnitario());
            ps.setInt(5, v.getCantidad());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void eliminarVehiculo(CLVehiculo v) throws SQLException {
        String sql = "{CALL usp_eliminarVehiculo(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, v.getCodVehiculo());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public int autoIncrementarVehiculoID() throws SQLException {
        int codVehiculo = 0;
        String sql = "{CALL usp_autoIncrementarVehiculoID()}";

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                codVehiculo = rs.getInt("codVehiculo");
            }
            if (codVehiculo == 0) {
                codVehiculo = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        return codVehiculo;
    }

    public List<CLVehiculo> obtenerListaVehiculo() throws SQLException {
        String sql = "{CALL usp_mostrarVehiculos()}";
        List<CLVehiculo> lista = new ArrayList<>();

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                CLVehiculo v = new CLVehiculo();
                v.setCodVehiculo(rs.getString("codVehiculo"));
                v.setMarcaVehiculo(rs.getString("marcaVehiculo"));
                v.setModelo(rs.getString("modelo"));
                v.setPrecioUnitario(rs.getDouble("precioUnitario"));
                v.setCantidad(rs.getInt("cantidad"));
                lista.add(v);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return lista;
    }
}
