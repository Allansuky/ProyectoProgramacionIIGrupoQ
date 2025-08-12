/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capadatos;

import capalogica.CLCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CDCliente {

    private final Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    public CDCliente() throws SQLException {
        this.cn = Conexion.conectar();
    }

    public void insertarCliente(CLCliente cl) throws SQLException {
        String sql = "{CALL usp_insertarCliente(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setString(1, cl.getNombreCliente());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void actualizarCliente(CLCliente cl) throws SQLException {
        String sql = "{CALL usp_actualizarCliente(?,?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdCliente());
            ps.setString(2, cl.getNombreCliente());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public void eliminarCliente(CLCliente cl) throws SQLException {
        String sql = "{CALL usp_eliminarCliente(?)}";

        try {
            ps = cn.prepareCall(sql);
            ps.setInt(1, cl.getIdCliente());
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
    }

    public int auntoIncrementarClienteID() throws SQLException {
        int idCliente = 0;

        String sql = "{CALL usp_autoIncrementarClienteID()}";

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();

            idCliente = rs.getInt("idCliente");

            if (idCliente == 0) {
                idCliente = 1;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }
        return idCliente;
    }
    
    private String obtenerNombreCiudad(int idCiudad) throws SQLException {
        String nombre = "";
        String sql = "SELECT nombreCiudad FROM Ciudad WHERE idCiudad = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCiudad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombreCiudad");
                }
            }
        }
        return nombre;
    }

    private String obtenerDescripcionSexo(int idSexo) throws SQLException {
        String descripcion = "";
        String sql = "SELECT descripcionSexo FROM Sexo WHERE idSexo = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idSexo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    descripcion = rs.getString("descripcionSexo");
                }
            }
        }
        return descripcion;
    }

    public List<CLCliente> obtenerListaCliente() throws SQLException {
        String sql = "{CALL usp_mostrarCliente()}";
        List<CLCliente> miLista = new ArrayList<>();

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                CLCliente cl = new CLCliente();
                cl.setIdCliente(rs.getInt("idCliente"));
                cl.setNombreCliente(rs.getString("nombreCliente"));
                cl.setApellidos(rs.getString("apellidos"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setEmail(rs.getString("email"));
                cl.setRtn(rs.getString("RTN"));
                cl.setIdCiudad(rs.getInt("idCiudad"));
                cl.setIdSexo(rs.getInt("idSexo"));

                // Obtener nombre de ciudad
                cl.setNombreCiudad(obtenerNombreCiudad(cl.getIdCiudad()));

                // Obtener descripción de sexo
                cl.setDescripcionSexo(obtenerDescripcionSexo(cl.getIdSexo()));

                miLista.add(cl);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return miLista;
    }

    public List<String> cargarComboCliente() throws SQLException {
        String sql = "{CALL usp_mostrarCliente()}";

        List<String> miLista = null;

        try {
            st = cn.createStatement();
            rs = st.executeQuery(sql);

            miLista = new ArrayList<>();
            miLista.add("--Seleccione--");

            while (rs.next()) {
                miLista.add(rs.getString("nombreCliente"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());
        }

        return miLista;
    }
}

