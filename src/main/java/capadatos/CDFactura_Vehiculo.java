/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLFactura_Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDFactura_Vehiculo {

    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDFactura_Vehiculo() throws SQLException {
        this.cn = Conexion.conectar();
    }

    // Insertar factura
    public void insertarFactura(CLFactura_Vehiculo f) throws SQLException {
        String sql = "{CALL usp_insertarFacturaVehiculo(?,?,?,?,?,?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, f.getFechaVenta());
            ps.setDouble(2, f.getSubtotal());
            ps.setDouble(3, f.getImpuesto());
            ps.setDouble(4, f.getTotalPago());
            ps.setString(5, f.getIdCliente());
            ps.setInt(6, f.getIdTipoPago());
            ps.setInt(7, f.getIdUsuario());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar factura: " + e.getMessage());
        }
    }

    // Actualizar factura
    public void actualizarFactura(CLFactura_Vehiculo f) throws SQLException {
        String sql = "{CALL usp_actualizarFacturaVehiculo(?,?,?,?,?,?,?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, f.getIdFactura());
            ps.setString(2, f.getFechaVenta());
            ps.setDouble(3, f.getSubtotal());
            ps.setDouble(4, f.getImpuesto());
            ps.setDouble(5, f.getTotalPago());
            ps.setString(6, f.getIdCliente());
            ps.setInt(7, f.getIdTipoPago());
            ps.setInt(8, f.getIdUsuario());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar factura: " + e.getMessage());
        }
    }

    // Eliminar factura
    public void eliminarFactura(CLFactura_Vehiculo f) throws SQLException {
        String sql = "{CALL usp_eliminarFacturaVehiculo(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, f.getIdFactura());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar factura: " + e.getMessage());
        }
    }

    // Mostrar lista de facturas
    public List<CLFactura_Vehiculo> obtenerListaFactura() throws SQLException {
        String sql = "{CALL usp_mostrarFacturaVehiculo()}";
        List<CLFactura_Vehiculo> lista = new ArrayList<>();

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                CLFactura_Vehiculo f = new CLFactura_Vehiculo();
                f.setIdFactura(rs.getInt("idFactura"));
                f.setFechaVenta(rs.getString("fechaVenta"));
                f.setSubtotal(rs.getDouble("subtotal"));
                f.setImpuesto(rs.getDouble("impuesto"));
                f.setTotalPago(rs.getDouble("totalPago"));
                f.setIdCliente(rs.getString("idCliente"));
                f.setIdTipoPago(rs.getInt("idTipoPago"));
                f.setIdUsuario(rs.getInt("idUsuario"));
                lista.add(f);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener facturas: " + e.getMessage());
        }
        return lista;
    }
}

