/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package capapresentacion;

import capadatos.CDMarca;
import capalogica.CLMarca;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JFraMarca extends javax.swing.JInternalFrame {

    public JFraMarca() throws SQLException {
        initComponents();
        poblarTablaMarcas();
        encontrarCorrelativo();
        this.jTFNombreMarca.requestFocus();
    }

    // Limpiar tabla
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblMarcas.getModel();

        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    // Metodo de poblar tabla marcas
    private void poblarTablaMarcas() throws SQLException {
        limpiarTabla();

        CDMarca cdMarca = new CDMarca();
        List<CLMarca> miLista = cdMarca.obtenerListaMarca();
        DefaultTableModel temp = (DefaultTableModel) this.jTblMarcas.getModel();

        miLista.stream().map((CLMarca cl) -> {
            Object[] fila = new Object[2];
            fila[0] = cl.getIdMarca();
            fila[1] = cl.getNombreMarca();
            return fila;
        }).forEachOrdered(temp::addRow);
    }

    // Metodo para encontrar el correlativo
    private void encontrarCorrelativo() throws SQLException {
        CDMarca cdMarca = new CDMarca();
        CLMarca cl = new CLMarca();

        cl.setIdMarca(cdMarca.auntoIncrementarMarcaID());
        this.jTFIdMarca.setText(String.valueOf(cl.getIdMarca()));
    }

    // Metodo para habilitar y deshabilitar botones
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }

    // Metodo para limpiar los campos de texto
    private boolean limpiarTextField() {
        this.jTFIdMarca.setText("");
        this.jTFNombreMarca.setText("");
        this.jTFNombreMarca.requestFocus();
        return false;
    }

    // Metodo para validar campos
    private boolean validarTextField() {
        boolean estado;

        estado = !this.jTFNombreMarca.getText().equals("");

        return estado;
    }

    // Metodo para insertar marca
    private void insertarMarca() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar el nombre de la marca", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreMarca.requestFocus();
        } else {
            try {
                CDMarca cdMarca = new CDMarca();
                CLMarca cl = new CLMarca();
                cl.setNombreMarca(this.jTFNombreMarca.getText().trim());
                cdMarca.insertarMarca(cl);
                JOptionPane.showMessageDialog(null, "Registro almacenado satisfactoriamente.", "Control Credito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
                this.jTFNombreMarca.requestFocus();
            }
        }
    }

    // Metodo para guardar marca
    private void guardar() throws SQLException {
        insertarMarca();
        poblarTablaMarcas();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }

    // Metodo para actualizar marca
    private void actualizarMarca() {
        if (!validarTextField()) {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar el nombre de la marca", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreMarca.requestFocus();
        } else {
            try {
                CDMarca cdMarca = new CDMarca();
                CLMarca cl = new CLMarca();
                cl.setIdMarca(Integer.parseInt(this.jTFIdMarca.getText().trim()));
                cl.setNombreMarca(this.jTFNombreMarca.getText().trim());
                cdMarca.actualizarMarca(cl);

                JOptionPane.showMessageDialog(null, "Registro actualizado satisfactoriamente.", "Control Credito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el registro" + ex);
                this.jTFNombreMarca.requestFocus();
            }
        }
    }

    // Metodo para seleccionar fila y cargar datos en campos
    private void filaSeleccionada() {
        if (this.jTblMarcas.getSelectedRow() != -1) {
            this.jTFIdMarca.setText(String.valueOf(this.jTblMarcas.getValueAt(this.jTblMarcas.getSelectedRow(), 0)));
            this.jTFNombreMarca.setText(String.valueOf(this.jTblMarcas.getValueAt(this.jTblMarcas.getSelectedRow(), 1)));
        }
    }

    // Metodo para editar registro
    private void editar() throws SQLException {
        actualizarMarca();
        poblarTablaMarcas();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
    }

    // Metodo para eliminar marca
    private void eliminarMarca() {
        try {
            CDMarca cdMarca = new CDMarca();
            CLMarca cl = new CLMarca();
            cl.setIdMarca(Integer.parseInt(this.jTFIdMarca.getText().trim()));
            cdMarca.eliminarMarca(cl);

            JOptionPane.showMessageDialog(null, "Registro eliminado satisfactoriamente.", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro" + ex);
            this.jTFNombreMarca.requestFocus();
        }
    }

    // Metodo para eliminar con confirmación
    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?", "Control Credito",
                JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarMarca();
                poblarTablaMarcas();
                habilitarBotones(true, false, false, true);
                limpiarTextField();
                encontrarCorrelativo();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error" + ex);
            }
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
        jTblMarcas = new javax.swing.JTable();
        jTFNombreMarca = new javax.swing.JTextField();
        jTFIdMarca = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Marca", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jTblMarcas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Marca", "Nombre Marca"
            }
        ));
        jTblMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblMarcasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblMarcas);

        jTFIdMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdMarcaActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Marca");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre Marca");

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
                            .addComponent(jTFIdMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jTFNombreMarca)))
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
                        .addComponent(jTFIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFNombreMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTblMarcasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblMarcasMouseClicked
        filaSeleccionada();
        habilitarBotones(false,true,true,true);
    }//GEN-LAST:event_jTblMarcasMouseClicked

    private void jTFIdMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdMarcaActionPerformed

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
    private javax.swing.JTextField jTFIdMarca;
    private javax.swing.JTextField jTFNombreMarca;
    private javax.swing.JTable jTblMarcas;
    // End of variables declaration//GEN-END:variables
}
