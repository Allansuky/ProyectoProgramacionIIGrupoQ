/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package capapresentacion;

import capadatos.CDProveedor;
import capalogica.CLProveedor;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JFraProveedor extends javax.swing.JInternalFrame {

    public JFraProveedor() throws SQLException {
        initComponents();
        poblarTablaProveedores();
        encontrarCorrelativo();
        this.jTFNombreProveedor.requestFocus();
    }

    // Limpiar tabla
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblProveedores.getModel();
        while(dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    // Poblar tabla proveedores
    private void poblarTablaProveedores() throws SQLException {
        limpiarTabla();

        CDProveedor cdProveedor = new CDProveedor();
        List<CLProveedor> lista = cdProveedor.obtenerListaProveedor();
        DefaultTableModel temp = (DefaultTableModel) this.jTblProveedores.getModel();

        lista.stream().map((CLProveedor cl) -> {
            Object[] fila = new Object[2];
            fila[0] = cl.getIdProveedor();
            fila[1] = cl.getNombreProveedor();
            return fila;
        }).forEachOrdered(temp::addRow);
    }

    // Encontrar correlativo para nuevo proveedor
    private void encontrarCorrelativo() throws SQLException {
        CDProveedor cdProveedor = new CDProveedor();
        CLProveedor clProveedor = new CLProveedor();

        clProveedor.setIdProveedor(cdProveedor.auntoIncrementarProveedorID());
        this.jTFIdProveedor.setText(String.valueOf(clProveedor.getIdProveedor()));
    }

    // Habilitar o deshabilitar botones
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }

    // Limpiar campos de texto
    private boolean limpiarTextField() {
        this.jTFIdProveedor.setText("");
        this.jTFNombreProveedor.setText("");
        this.jTFNombreProveedor.requestFocus();
        return false;
    }

    // Validar que el campo nombre no esté vacío
    private boolean validarTextField() {
        return !this.jTFNombreProveedor.getText().trim().equals("");
    }

    // Insertar proveedor
    private void insertarProveedor() {
        if(!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del proveedor", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreProveedor.requestFocus();
        } else {
            try {
                CDProveedor cdProveedor = new CDProveedor();
                CLProveedor clProveedor = new CLProveedor();
                clProveedor.setNombreProveedor(this.jTFNombreProveedor.getText().trim());
                cdProveedor.insertarProveedor(clProveedor);
                JOptionPane.showMessageDialog(null, "Registro almacenado satisfactoriamente.", "Control Credito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro: " + ex);
                this.jTFNombreProveedor.requestFocus();
            }
        }
    }

    // Guardar proveedor
    private void guardar() throws SQLException {
        insertarProveedor();
        poblarTablaProveedores();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }

    // Actualizar proveedor
    private void actualizarProveedor() {
        if(!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del proveedor", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreProveedor.requestFocus();
        } else {
            try {
                CDProveedor cdProveedor = new CDProveedor();
                CLProveedor clProveedor = new CLProveedor();
                clProveedor.setIdProveedor(Integer.parseInt(this.jTFIdProveedor.getText().trim()));
                clProveedor.setNombreProveedor(this.jTFNombreProveedor.getText().trim());
                cdProveedor.actualizarProveedor(clProveedor);
                JOptionPane.showMessageDialog(null, "Registro actualizado satisfactoriamente.", "Control Credito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el registro: " + ex);
                this.jTFNombreProveedor.requestFocus();
            }
        }
    }

    // Seleccionar fila para editar
    private void filaSeleccionada() {
        if (this.jTblProveedores.getSelectedRow() != -1) {
            this.jTFIdProveedor.setText(String.valueOf(this.jTblProveedores.getValueAt(this.jTblProveedores.getSelectedRow(), 0)));
            this.jTFNombreProveedor.setText(String.valueOf(this.jTblProveedores.getValueAt(this.jTblProveedores.getSelectedRow(), 1)));
            habilitarBotones(false, true, true, true);
        }
    }

    // Editar proveedor
    private void editar() throws SQLException {
        actualizarProveedor();
        poblarTablaProveedores();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }

    // Eliminar proveedor
    private void eliminarProveedor() {
        try {
            CDProveedor cdProveedor = new CDProveedor();
            CLProveedor clProveedor = new CLProveedor();
            clProveedor.setIdProveedor(Integer.parseInt(this.jTFIdProveedor.getText().trim()));
            cdProveedor.eliminarProveedor(clProveedor);
            JOptionPane.showMessageDialog(null, "Registro eliminado satisfactoriamente.", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + ex);
            this.jTFNombreProveedor.requestFocus();
        }
    }

    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Control Credito",
                JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            eliminarProveedor();
            poblarTablaProveedores();
            habilitarBotones(true, false, false, true);
            limpiarTextField();
            encontrarCorrelativo();
        } else {
            limpiarTextField();
        }
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
        jTblProveedores = new javax.swing.JTable();
        jTFNombreProveedor = new javax.swing.JTextField();
        jTFIdProveedor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Proveedor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jTblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Proveedor", "Nombre Proveedor"
            }
        ));
        jTblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblProveedoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblProveedores);

        jTFIdProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdProveedorActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Proveedor");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre Proveedor");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTFIdProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jTFNombreProveedor)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)))
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(327, 327, 327)
                .addComponent(jBtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEliminar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGuardar)
                    .addComponent(jBtnEditar)
                    .addComponent(jBtnLimpiar)
                    .addComponent(jBtnEliminar))
                .addContainerGap(18, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblProveedoresMouseClicked
        filaSeleccionada();
        habilitarBotones(false,true,true,true);
    }//GEN-LAST:event_jTblProveedoresMouseClicked

    private void jTFIdProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdProveedorActionPerformed

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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFIdProveedor;
    private javax.swing.JTextField jTFNombreProveedor;
    private javax.swing.JTable jTblProveedores;
    // End of variables declaration//GEN-END:variables
}
