package capapresentacion;


import capadatos.CDVehiculo;
import capalogica.CLVehiculo;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */

    public class JFraVehiculo extends javax.swing.JInternalFrame {

    public JFraVehiculo() throws SQLException {
        initComponents();
        poblarTablaVehiculos();
        encontrarCorrelativo();
        this.jTFMarcaVehiculo.requestFocus();
    }

    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblVehiculos.getModel();
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    private void poblarTablaVehiculos() throws SQLException {
        limpiarTabla();
        CDVehiculo cdVehiculo = new CDVehiculo();
        List<CLVehiculo> lista = cdVehiculo.obtenerListaVehiculo();
        DefaultTableModel temp = (DefaultTableModel) this.jTblVehiculos.getModel();

        lista.forEach((CLVehiculo v) -> {
            Object[] fila = new Object[5];
            fila[0] = v.getCodVehiculo();
            fila[1] = v.getMarcaVehiculo();
            fila[2] = v.getModelo();
            fila[3] = v.getPrecioUnitario();
            fila[4] = v.getCantidad();
            temp.addRow(fila);
        });
    }

    private void encontrarCorrelativo() throws SQLException {
        CDVehiculo cdVehiculo = new CDVehiculo();
        this.jTFCodVehiculo.setText(String.valueOf(cdVehiculo.autoIncrementarVehiculoID()));
    }
    
     private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }
    
    private boolean limpiarTextField() {
        this.jTFCodVehiculo.setText("");
        this.jTFMarcaVehiculo.setText("");
        this.jTFModeloVehiculo.setText("");
        this.jTFPrecioUnitario.setText("");
        this.jTFCantidad.setText("");
        this.jTFCantidad.requestFocus();
        
        return false;
    }

    private boolean validarCampos() {
        return !jTFMarcaVehiculo.getText().trim().isEmpty()
                && !jTFModeloVehiculo.getText().trim().isEmpty()
                && !jTFPrecioUnitario.getText().trim().isEmpty()
                && !jTFCantidad.getText().trim().isEmpty();
    }

    private void insertarVehiculo() {
        if (!validarCampos()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Stock", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            CDVehiculo cdVehiculo = new CDVehiculo();
            CLVehiculo v = new CLVehiculo();
            v.setMarcaVehiculo(jTFMarcaVehiculo.getText().trim());
            v.setModelo(jTFModeloVehiculo.getText().trim());
            v.setPrecioUnitario(Double.parseDouble(jTFPrecioUnitario.getText().trim()));
            v.setCantidad(Integer.parseInt(jTFCantidad.getText().trim()));
            cdVehiculo.insertarVehiculo(v);
            JOptionPane.showMessageDialog(null, "Vehículo guardado correctamente.", "Stock", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage());
        }
    }

    private void guardar() throws SQLException {
        insertarVehiculo();
        poblarTablaVehiculos();
        limpiarCampos();
        encontrarCorrelativo();
    }

    private void actualizarVehiculo() {
        if (!validarCampos()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Stock", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            CDVehiculo cdVehiculo = new CDVehiculo();
            CLVehiculo v = new CLVehiculo();
            v.setCodVehiculo(jTFCodVehiculo.getText().trim());
            v.setMarcaVehiculo(jTFMarcaVehiculo.getText().trim());
            v.setModelo(jTFModeloVehiculo.getText().trim());
            v.setPrecioUnitario(Double.parseDouble(jTFPrecioUnitario.getText().trim()));
            v.setCantidad(Integer.parseInt(jTFCantidad.getText().trim()));
            cdVehiculo.actualizarVehiculo(v);
            JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente.", "Stock", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
        }
    }
    
    private void filaSeleccionada() {
        if (this.jTblVehiculos.getSelectedRow() != -1) {
            this.jTFCodVehiculo.setText(String.valueOf(this.jTblVehiculos.getValueAt(this.jTblVehiculos.getSelectedRow(), 0)));
            this.jTFMarcaVehiculo.setText(String.valueOf(this.jTblVehiculos.getValueAt(this.jTblVehiculos.getSelectedRow(), 1)));
            this.jTFModeloVehiculo.setText(String.valueOf(this.jTblVehiculos.getValueAt(this.jTblVehiculos.getSelectedRow(), 2)));
            this.jTFPrecioUnitario.setText(String.valueOf(this.jTblVehiculos.getValueAt(this.jTblVehiculos.getSelectedRow(), 3)));
            this.jTFCantidad.setText(String.valueOf(this.jTblVehiculos.getValueAt(this.jTblVehiculos.getSelectedRow(), 4)));
        }
    }

    private void editar() throws SQLException {
        actualizarVehiculo();
        poblarTablaVehiculos();
        limpiarCampos();
        encontrarCorrelativo();
    }

    private void eliminarVehiculo() {
        try {
            CDVehiculo cdVehiculo = new CDVehiculo();
            CLVehiculo v = new CLVehiculo();
            v.setCodVehiculo(jTFCodVehiculo.getText().trim());
            cdVehiculo.eliminarVehiculo(v);
            JOptionPane.showMessageDialog(null, "Vehículo eliminado correctamente.", "Stock", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el vehículo?", "Stock", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            eliminarVehiculo();
            poblarTablaVehiculos();
            limpiarCampos();
            encontrarCorrelativo();
        }
    }

    private void limpiarCampos() {
        jTFCodVehiculo.setText("");
        jTFMarcaVehiculo.setText("");
        jTFModeloVehiculo.setText("");
        jTFPrecioUnitario.setText("");
        jTFCantidad.setText("");
        jTFMarcaVehiculo.requestFocus();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTFMarca1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblVehiculos = new javax.swing.JTable();
        jTFModeloVehiculo = new javax.swing.JTextField();
        jTFCodVehiculo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFPrecioUnitario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();
        jTFMarcaVehiculo = new javax.swing.JTextField();

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Vehiculo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jTblVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod Vehiculo", "Marca", "Modelo", "Precio Unitario", "Cantidad"
            }
        ));
        jTblVehiculos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblVehiculosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblVehiculos);

        jTFCodVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFCodVehiculoActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cod Vehiculo");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Marca");

        jBtnGuardar.setText("Guardar");
        jBtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarActionPerformed(evt);
            }
        });

        jBtnLimpiar.setText("Limpiar");
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });

        jBtnEditar.setText("Editar");
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnEliminar.setText("Eliminar");
        jBtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEliminarActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Modelo");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cantidad");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Precio Unitario");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jTFCodVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jTFModeloVehiculo)
                            .addComponent(jTFPrecioUnitario)
                            .addComponent(jTFCantidad, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jTFMarcaVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 415, Short.MAX_VALUE)
                .addComponent(jBtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEliminar)
                .addGap(263, 263, 263))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(jLabel5)
                    .addContainerGap(779, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCodVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTFMarcaVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFModeloVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTFPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnEditar)
                        .addComponent(jBtnLimpiar)
                        .addComponent(jBtnEliminar))
                    .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(145, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(243, 243, 243)
                    .addComponent(jLabel5)
                    .addContainerGap(248, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTblVehiculosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblVehiculosMouseClicked
        filaSeleccionada();
        habilitarBotones(false,true,true,true);
    }//GEN-LAST:event_jTblVehiculosMouseClicked

    private void jTFCodVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFCodVehiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFCodVehiculoActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        try {
            guardar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpiarTextField();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            editar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
        try {
            eliminar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCodVehiculo;
    private javax.swing.JTextField jTFMarca1;
    private javax.swing.JTextField jTFMarcaVehiculo;
    private javax.swing.JTextField jTFModeloVehiculo;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTable jTblVehiculos;
    // End of variables declaration//GEN-END:variables
}
