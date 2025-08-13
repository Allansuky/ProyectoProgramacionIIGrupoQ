/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLDetalleFactura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CDDetalleFactura {

    private final Connection cn;

    public CDDetalleFactura() throws SQLException {
        this.cn = Conexion.conectar();
    }

    /**
     * Inserta un nuevo detalle de factura, incluyendo la fecha.
     * Llama a usp_insertarDetalleFactura(cantidad, precioUnitario, idFactura, codVehiculo, fecha)
     */
    public void insertarDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_insertarDetalleFactura(?,?,?,?,?)}";
        try (CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, df.getCantidad());
            cs.setDouble(2, df.getPrecioUnitario());
            cs.setInt(3, df.getIdFactura());
            cs.setString(4, df.getCodVehiculo());
            cs.setString(5, df.getFecha());
            cs.execute();
        }
    }

    /**
     * Actualiza todos los campos de un detalle de factura, incluida la fecha.
     * Llama a usp_actualizarDetalleFactura(idDetalle, cantidad, precioUnitario, fecha, codVehiculo)
     */
    public void actualizarDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_actualizarDetalleFactura(?,?,?,?,?)}";
        try (CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, df.getDetalle());
            cs.setInt(2, df.getCantidad());
            cs.setDouble(3, df.getPrecioUnitario());
            cs.setString(4, df.getFecha());
            cs.setString(5, df.getCodVehiculo());
            cs.execute();
        }
    }

    /**
     * Elimina un detalle de factura por su ID.
     * Llama a usp_eliminarDetalleFacturaX(idDetalle)
     */
    public void eliminarDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_eliminarDetalleFacturaX(?)}";
        try (CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, df.getDetalle());
            cs.execute();
        }
    }

    /**
     * Recupera todos los detalles de factura, incluyendo la fecha.
     * Llama a usp_mostrarDetallesFactura()
     */
    public List<CLDetalleFactura> obtenerListaDetalleFactura() throws SQLException {
        List<CLDetalleFactura> lista = new ArrayList<>();
        String sql = "{CALL usp_mostrarDetallesFactura()}";
        try (CallableStatement cs = cn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                CLDetalleFactura df = new CLDetalleFactura();
                df.setDetalle(rs.getInt("idDetalle"));
                df.setCantidad(rs.getInt("cantidad"));
                df.setPrecioUnitario(rs.getDouble("precioUnitario"));
                df.setIdFactura(rs.getInt("idFactura"));
                df.setCodVehiculo(rs.getString("codVehiculo"));
                df.setFecha(rs.getString("fecha"));
                lista.add(df);
            }
        }
        return lista;
    }

    /**
     * Actualiza solamente la fecha de un detalle.
     * Llama a usp_actualizarFechaDetalleFactura(idDetalle, nuevaFecha)
     */
    public void actualizarFechaDetalle(CLDetalleFactura df) throws SQLException {
        String sql = "{CALL usp_actualizarFechaDetalleFactura(?,?)}";
        try (CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, df.getDetalle());
            cs.setString(2, df.getFecha());
            cs.execute();
        }
    }
}
