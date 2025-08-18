package capapresentacion;

import capadatos.CDDetalleFactura;
import capadatos.CDFactura_Vehiculo;
import capalogica.CLDetalleFactura;
import capalogica.CLFactura_Vehiculo;
import capalogica.Calculos;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JFraDetalleFactura extends javax.swing.JInternalFrame {

    public JFraDetalleFactura() throws SQLException {
        initComponents();
        poblarTablaDetalleFactura();
        poblarTablaFechas();
        this.jTFFecha.requestFocus();
    }

    // ------------------------- Tus métodos originales -------------------------

    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
        this.jBtnActualizarFecha.setEnabled(!guardar);
    }

    private void limpiarCampos() {
        jTFidDetalle.setText("");
        jTFCantidad.setText("");
        jTFPrecioUnitario.setText("");
        jTFidFactura.setText("");
        jTFCodVehiculo.setText("");
        jTFFecha.setText("");
        jTFFecha.requestFocus();
        habilitarBotones(true, false, false, true);
    }

    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) jTblTablaFactura.getModel();
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    private void poblarTablaDetalleFactura() throws SQLException {
        limpiarTabla();
        CDDetalleFactura cd = new CDDetalleFactura();
        List<CLDetalleFactura> lista = cd.obtenerListaDetalleFactura();
        DefaultTableModel model = (DefaultTableModel) jTblTablaFactura.getModel();

        for (CLDetalleFactura df : lista) {
            Object[] fila = new Object[6];
            fila[0] = df.getDetalle();
            fila[1] = df.getCantidad();
            fila[2] = df.getPrecioUnitario();
            fila[3] = df.getIdFactura();
            fila[4] = df.getCodVehiculo();
            fila[5] = df.getFecha();
            model.addRow(fila);
        }

        // ------------------- NUEVO: agregar totales -------------------
        CLFactura_Vehiculo factura = new CLFactura_Vehiculo();
        Calculos.aplicarCalculos(factura, lista);
        model.addRow(new Object[]{"Subtotal", "-", factura.getSubtotal(), "-", "-", "-"});
        model.addRow(new Object[]{"Impuesto (15%)", "-", factura.getImpuesto(), "-", "-", "-"});
        model.addRow(new Object[]{"Total", "-", factura.getTotalPago(), "-", "-", "-"});
        // ---------------------------------------------------------------
    }

    private void limpiarTablaFechas() {
        DefaultTableModel dtm = (DefaultTableModel) jTblFechas.getModel();
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    private void poblarTablaFechas() throws SQLException {
        limpiarTablaFechas();
        CDDetalleFactura cd = new CDDetalleFactura();
        List<CLDetalleFactura> lista = cd.obtenerListaDetalleFactura();
        DefaultTableModel model = (DefaultTableModel) jTblFechas.getModel();

        for (CLDetalleFactura df : lista) {
            Object[] fila = new Object[2];
            fila[0] = df.getDetalle();
            fila[1] = df.getFecha();
            model.addRow(fila);
        }
    }

    private boolean validarCamposDetalle() {
        return !jTFCantidad.getText().trim().isEmpty()
            && !jTFPrecioUnitario.getText().trim().isEmpty()
            && !jTFidFactura.getText().trim().isEmpty()
            && !jTFFecha.getText().trim().isEmpty()
            && !jTFFecha.getText().trim().isEmpty();
    }

    private void insertarDetalleFactura() {
        if (!validarCamposDetalle()) {
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
            df.setFecha(jTFFecha.getText().trim());
            cd.insertarDetalle(df);
            JOptionPane.showMessageDialog(null, "Detalle guardado correctamente.", "Detalle Factura", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage());
        }
    }

    private void guardar() throws SQLException {
        insertarDetalleFactura();
        poblarTablaDetalleFactura(); // <-- aquí se agrega totales automáticamente
        poblarTablaFechas();
        limpiarCampos();
    }

    private void actualizarDetalleFactura() {
        if (!validarCamposDetalle()) {
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
            df.setFecha(jTFFecha.getText().trim());
            cd.actualizarDetalle(df);
            JOptionPane.showMessageDialog(null, "Detalle actualizado correctamente.", "Detalle Factura", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void editar() throws SQLException {
        actualizarDetalleFactura();
        poblarTablaDetalleFactura(); // <-- aquí se agrega totales automáticamente
        poblarTablaFechas();
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
            poblarTablaDetalleFactura(); // <-- aquí se agrega totales automáticamente
            poblarTablaFechas();
            limpiarCampos();
        }
    }

    private void filaSeleccionada() {
        int row = this.jTblTablaFactura.getSelectedRow();
        if (row != -1) {
            jTFidDetalle.setText(String.valueOf(this.jTblTablaFactura.getValueAt(row, 0)));
            jTFCantidad.setText(String.valueOf(this.jTblTablaFactura.getValueAt(row, 1)));
            jTFPrecioUnitario.setText(String.valueOf(this.jTblTablaFactura.getValueAt(row, 2)));
            jTFidFactura.setText(String.valueOf(this.jTblTablaFactura.getValueAt(row, 3)));
            jTFCodVehiculo.setText(String.valueOf(this.jTblTablaFactura.getValueAt(row, 4)));
            jTFFecha.setText(String.valueOf(this.jTblTablaFactura.getValueAt(row, 5)));
            habilitarBotones(false, true, true, true);
        }
    }

    private void filaFechaSeleccionada() {
        int row = this.jTblFechas.getSelectedRow();
        if (row != -1) {
            jTFidDetalle.setText(String.valueOf(this.jTblFechas.getValueAt(row, 0)));
            jTFFecha.setText(String.valueOf(this.jTblFechas.getValueAt(row, 1)));
            habilitarBotones(false, false, false, true);
            this.jBtnActualizarFecha.setEnabled(true);
        }
    }

    private void actualizarSoloFecha() {
        if (jTFidDetalle.getText().trim().isEmpty() || jTFFecha.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete ID Detalle y Fecha", "Actualizar Fecha", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            CDDetalleFactura cd = new CDDetalleFactura();
            CLDetalleFactura df = new CLDetalleFactura();
            df.setDetalle(Integer.parseInt(jTFidDetalle.getText().trim()));
            df.setFecha(jTFFecha.getText().trim());
            cd.actualizarFechaDetalle(df);
            JOptionPane.showMessageDialog(null, "Fecha actualizada correctamente.", "Actualizar Fecha", JOptionPane.INFORMATION_MESSAGE);
            poblarTablaFechas();
            limpiarCampos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar fecha: " + ex.getMessage());
        }
    }

    private void limpiarTextField() {
        jTFidDetalle.setText("");
        jTFCantidad.setText("");
        jTFPrecioUnitario.setText("");
        jTFidFactura.setText("");
        jTFCodVehiculo.setText("");
        jTFFecha.setText("");
        jTFidDetalle.requestFocus();
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
        jTFFecha = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblFechas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTFCodVehiculo = new javax.swing.JTextField();
        jBtnActualizarFecha = new javax.swing.JButton();

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

        jTblFechas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Subtotal", "Impuesto", "Total"
            }
        ));
        jScrollPane2.setViewportView(jTblFechas);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha");

        jBtnActualizarFecha.setText("Actualizar Fecha");
        jBtnActualizarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnActualizarFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFCodVehiculo)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTFFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(jBtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEditar)
                .addGap(18, 18, 18)
                .addComponent(jBtnLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnActualizarFecha)
                .addGap(0, 464, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTFidFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCodVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnEditar)
                    .addComponent(jBtnLimpiar)
                    .addComponent(jBtnEliminar)
                    .addComponent(jBtnActualizarFecha))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void jBtnActualizarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnActualizarFechaActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_jBtnActualizarFechaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnActualizarFecha;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCodVehiculo;
    private javax.swing.JTextField jTFFecha;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTextField jTFidDetalle;
    private javax.swing.JTextField jTFidFactura;
    private javax.swing.JTable jTblFechas;
    private javax.swing.JTable jTblTablaFactura;
    // End of variables declaration//GEN-END:variables
}
