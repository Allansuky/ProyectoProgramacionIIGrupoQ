/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLDetalleFactura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDDetalleFactura {

    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDDetalleFactura() throws SQLException {
        this.cn = Conexion.conectar();
    }

    // Insertar detalle de factura
    public void insertarDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_insertarDetalleFactura(?,?,?,?)}"; // idDetalle es autoincrement

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, df.getCantidad());
            ps.setDouble(2, df.getPrecioUnitario());
            ps.setInt(3, df.getIdFactura());
            ps.setString(4, df.getCodVehiculo());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar detalle: " + e.getMessage());
        }
    }

    // Actualizar detalle de factura
    public void actualizarDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_actualizarDetalleFactura(?,?,?,?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, df.getDetalle());
            ps.setInt(2, df.getCantidad());
            ps.setDouble(3, df.getPrecioUnitario());
            ps.setInt(4, df.getIdFactura());
            ps.setString(5, df.getCodVehiculo());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar detalle: " + e.getMessage());
        }
    }

    // Eliminar detalle de factura
    public void eliminarDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_eliminarDetalleFacturaX(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, df.getDetalle());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar detalle: " + e.getMessage());
        }
    }

    // Obtener lista de detalles de factura
    public List<CLDetalleFactura> obtenerListaDetalleFactura() throws SQLException {
        String sql = "{CALL usp_mostrarDetallesFactura()}";
        List<CLDetalleFactura> lista = new ArrayList<>();

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                CLDetalleFactura df = new CLDetalleFactura();
                df.setDetalle(rs.getInt("idDetalle"));
                df.setCantidad(rs.getInt("cantidad"));
                df.setPrecioUnitario(rs.getDouble("precioUnitario"));
                df.setIdFactura(rs.getInt("idFactura"));
                df.setCodVehiculo(rs.getString("codVehiculo"));
                lista.add(df);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener detalles: " + e.getMessage());
        }
        return lista;
    }
}
