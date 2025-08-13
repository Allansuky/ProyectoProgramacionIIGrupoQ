/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package capapresentacion;

import capadatos.CDDetalleFactura;
import capalogica.CLDetalleFactura;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JFraDetalleFactura extends javax.swing.JInternalFrame {
    
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }
    
    private boolean limpiarTextField() {
        this.jTFCantidad.setText("");
        this.jTFCodVehiculo.setText("");
        this.jTFPrecioUnitario.setText("");
        this.jTFidDetalle.setText("");
        this.jTFidFactura.setText("");
        this.jTFidDetalle.requestFocus();
        return false;
    }

    public JFraDetalleFactura() throws SQLException {
        initComponents();
        poblarTablaDetalleFactura();
        this.jTFCodVehiculo.requestFocus();
    }

    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblTablaFactura.getModel();
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    private void poblarTablaDetalleFactura() throws SQLException {
        limpiarTabla();
        CDDetalleFactura cd = new CDDetalleFactura();
        List<CLDetalleFactura> lista = cd.obtenerListaDetalleFactura();
        DefaultTableModel temp = (DefaultTableModel) this.jTblTablaFactura.getModel();

        lista.forEach((CLDetalleFactura df) -> {
            Object[] fila = new Object[5];
            fila[0] = df.getDetalle();
            fila[1] = df.getCantidad();
            fila[2] = df.getPrecioUnitario();
            fila[3] = df.getIdFactura();
            fila[4] = df.getCodVehiculo();
            temp.addRow(fila);
        });
    }

    private boolean validarCampos() {
        return !jTFCantidad.getText().trim().isEmpty()
            && !jTFPrecioUnitario.getText().trim().isEmpty()
            && !jTFidFactura.getText().trim().isEmpty()
            && !jTFCodVehiculo.getText().trim().isEmpty();
    }

    private void insertarDetalleFactura() {
        if (!validarCampos()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Detalle Factura", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            CDDetalleFactura cd = new CDDetalleFactura();
            CLDetalleFactura df = new CLDetalleFactura();
            df.setCantidad(Integer.parseInt(jTFCantidad.getText().trim()));
            df.setPrecioUnitario(Double.parseDouble(jTFPrecioUnitario.getText().trim()));
            df.setIdFactura(Integer.parseInt(jTFidFactura.getText().trim()));
            df.setCodVehiculo(jTFCodVehiculo.getText().trim());
            cd.insertarDetalle(df);
            JOptionPane.showMessageDialog(null, "Detalle guardado correctamente.", "Detalle Factura", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage());
        }
    }

    private void guardar() throws SQLException {
        insertarDetalleFactura();
        poblarTablaDetalleFactura();
        limpiarCampos();
    }

    private void actualizarDetalleFactura() {
        if (!validarCampos()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos", "Detalle Factura", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            CDDetalleFactura cd = new CDDetalleFactura();
            CLDetalleFactura df = new CLDetalleFactura();
            df.setDetalle(Integer.parseInt(jTFidDetalle.getText().trim()));
            df.setCantidad(Integer.parseInt(jTFCantidad.getText().trim()));
            df.setPrecioUnitario(Double.parseDouble(jTFPrecioUnitario.getText().trim()));
            df.setIdFactura(Integer.parseInt(jTFidFactura.getText().trim()));
            df.setCodVehiculo(jTFCodVehiculo.getText().trim());
            cd.actualizarDetalle(df);
            JOptionPane.showMessageDialog(null, "Detalle actualizado correctamente.", "Detalle Factura", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void editar() throws SQLException {
        actualizarDetalleFactura();
        poblarTablaDetalleFactura();
        limpiarCampos();
    }

    private void eliminarDetalleFactura() {
        try {
            CDDetalleFactura cd = new CDDetalleFactura();
            CLDetalleFactura df = new CLDetalleFactura();
            df.setDetalle(Integer.parseInt(jTFidDetalle.getText().trim()));
            cd.eliminarDetalle(df);
            JOptionPane.showMessageDialog(null, "Detalle eliminado correctamente.", "Detalle Factura", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el detalle?", "Detalle Factura", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            eliminarDetalleFactura();
            poblarTablaDetalleFactura();
            limpiarCampos();
        }
    }

    private void filaSeleccionada() {
        if (this.jTblTablaFactura.getSelectedRow() != -1) {
            this.jTFidDetalle.setText(String.valueOf(this.jTblTablaFactura.getValueAt(this.jTblTablaFactura.getSelectedRow(), 0)));
            this.jTFCantidad.setText(String.valueOf(this.jTblTablaFactura.getValueAt(this.jTblTablaFactura.getSelectedRow(), 1)));
            this.jTFPrecioUnitario.setText(String.valueOf(this.jTblTablaFactura.getValueAt(this.jTblTablaFactura.getSelectedRow(), 2)));
            this.jTFidFactura.setText(String.valueOf(this.jTblTablaFactura.getValueAt(this.jTblTablaFactura.getSelectedRow(), 3)));
            this.jTFCodVehiculo.setText(String.valueOf(this.jTblTablaFactura.getValueAt(this.jTblTablaFactura.getSelectedRow(), 4)));
        }
    }

    private void limpiarCampos() {
        jTFidDetalle.setText("");
        jTFCantidad.setText("");
        jTFPrecioUnitario.setText("");
        jTFidFactura.setText("");
        jTFCodVehiculo.setText("");
        jTFCantidad.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblTablaFactura = new javax.swing.JTable();
        jTFCantidad = new javax.swing.JTextField();
        jTFidDetalle = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTFPrecioUnitario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTFidFactura = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFCodVehiculo = new javax.swing.JTextField();

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jTblTablaFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID detalle", "Cantidad", "Precio Unitario", "ID Factura", "ID Codigo Factura"
            }
        ));
        jTblTablaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblTablaFacturaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblTablaFactura);

        jTFidDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFidDetalleActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Detalle Factura");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Cantidad");

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

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Codigo Vehiculo");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Precio Unitario");

        jTFidFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFidFacturaActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ID Factura");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTFCodVehiculo)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFCantidad)
                            .addComponent(jLabel1)
                            .addComponent(jTFidDetalle)
                            .addComponent(jLabel2)
                            .addComponent(jTFPrecioUnitario)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jTFidFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jBtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnLimpiar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEliminar)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFidDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFPrecioUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFidFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFCodVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnEditar)
                    .addComponent(jBtnLimpiar)
                    .addComponent(jBtnEliminar))
                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTblTablaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblTablaFacturaMouseClicked
        filaSeleccionada();
        habilitarBotones(false,true,true,true);
    }//GEN-LAST:event_jTblTablaFacturaMouseClicked

    private void jTFidDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFidDetalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFidDetalleActionPerformed

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

    private void jTFidFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFidFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFidFacturaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCodVehiculo;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTextField jTFidDetalle;
    private javax.swing.JTextField jTFidFactura;
    private javax.swing.JTable jTblTablaFactura;
    // End of variables declaration//GEN-END:variables
}
