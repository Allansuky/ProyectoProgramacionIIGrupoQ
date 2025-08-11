/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package capapresentacion;


import capadatos.CDCiudad;
import capalogica.CLCiudad;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class JFraCiudad extends javax.swing.JInternalFrame {

    public JFraCiudad() throws SQLException {
        initComponents();
        poblarTablaCiudades();
        encontrarCorrelativo();
        this.jTFNombreCiudad.requestFocus();
    }


    // Limpiar tabla
    private void limpiarTabla() {
        DefaultTableModel dtm = (DefaultTableModel) this.jTblCiudades.getModel();
        
        while(dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }
    
    // Metodo de poblar tablas
    private void poblarTablaCiudades() throws SQLException {
        limpiarTabla();
        
        CDCiudad cdc = new CDCiudad();
        List<CLCiudad> miLista = cdc.obtenerListaCiudades();
        DefaultTableModel temp = (DefaultTableModel) this.jTblCiudades.getModel();
        
        miLista.stream().map((CLCiudad cl) -> {
            Object[] fila = new Object[2];
            fila[0] = cl.getIdCiudad();
            fila[1] = cl.getNombreCiudad();
            return fila;
        }).forEachOrdered(temp::addRow);
    }
    // Metodo para encontrar el correlativo.
    private void encontrarCorrelativo() throws SQLException {
        CDCiudad cdc = new CDCiudad();
        CLCiudad cl = new CLCiudad();
        
        cl.setIdCiudad(cdc.auntoIncrementarCiudadID());
        this.jTFIdCiudad.setText(String.valueOf(cl.getIdCiudad()));
    }
    
    // Metodo para habilitar y desabilitar botones.
    private void habilitarBotones(boolean guardar, boolean editar, boolean eliminar, boolean limpiar) {
        this.jBtnGuardar.setEnabled(guardar);
        this.jBtnEditar.setEnabled(editar);
        this.jBtnEliminar.setEnabled(eliminar);
        this.jBtnLimpiar.setEnabled(limpiar);
    }
    
    // Metodo para limpiar.
    private boolean limpiarTextField() {
        this.jTFIdCiudad.setText("");
        this.jTFNombreCiudad.setText("");
        this.jTFNombreCiudad.requestFocus();
        return false;
    }
    
    // Metodo para insertar una ciudad en la tabla.
    private boolean validarTextField() {
        boolean estado;
        
        estado = !this.jTFNombreCiudad.getText().equals("");
        
        return estado;
    }
    
    //Metodo para insertar una ciudad en la tabla.
    private void insertarCiudad() {
        if(!validarTextField()) {
            JOptionPane.showMessageDialog(null, "tiene que ingresar el nombre de la ciudad", "Control Credito", 
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreCiudad.requestFocus();
        } else {
            try {
                CDCiudad cdc = new CDCiudad();
                CLCiudad cl = new CLCiudad();
                cl.setNombreCiudad(this.jTFNombreCiudad.getText().trim());
                cdc.insertarCiudad(cl);
                JOptionPane.showMessageDialog(null, "Registro almacenado satisfactoriamente.", "Control Credito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
            this.jTFNombreCiudad.requestFocus();
            }
        }
    }
    
    // Metodo para llamar el metodo insertar ciudad.
    private void guardar() throws SQLException {
        insertarCiudad();
        poblarTablaCiudades();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
        
    }
    
    // Metodo para actualizar un registro de la tabla ciudad.
    private void actualizarCiudad() {
        if(!validarTextField()) {
            JOptionPane.showMessageDialog(null, "tiene que ingresar el nombre de la ciudad", "Control Credito", 
                    JOptionPane.INFORMATION_MESSAGE);
            this.jTFNombreCiudad.requestFocus();
        } else {
            try {
                CDCiudad cdc = new CDCiudad();
                CLCiudad cl = new CLCiudad();
                cl.setIdCiudad(Integer.parseInt(this.jTFIdCiudad.getText().trim()));
                cl.setNombreCiudad(this.jTFNombreCiudad.getText().trim());
                cdc.actualizarCiudad(cl);
                
                JOptionPane.showMessageDialog(null, "Registro actualizadp satisfactoriamente.", "Control Credito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el registro" + ex);
            this.jTFNombreCiudad.requestFocus();
            }
        }
    }
    
    // Metodo para seleccionar los datos de la fila y modificarlo
    private void filaSeleccionada() {
        if (this.jTblCiudades.getSelectedRow() != -1) {
            this.jTFIdCiudad.setText(String.valueOf(this.jTblCiudades.getValueAt(this.jTblCiudades.getSelectedRow(), 0)));
            this.jTFNombreCiudad.setText(String.valueOf(this.jTblCiudades.getValueAt(this.jTblCiudades.getSelectedRow(), 1)));
        }
    }
    
    // Metodo para llenar el metodo actualizar registro de la tabla.
    private void editar() throws SQLException {
        actualizarCiudad();
        poblarTablaCiudades();
        habilitarBotones(true, false, false, true);
        limpiarTextField();
        encontrarCorrelativo();
        
    }
    
    // Metodo para eliminar.
    private void eliminarCiudad() {
        try {
            CDCiudad cdc = new CDCiudad();
            CLCiudad cl = new CLCiudad();
            cl.setIdCiudad(Integer.parseInt(this.jTFIdCiudad.getText().trim()));
            cdc.eliminarCiudad(cl);

            JOptionPane.showMessageDialog(null, "Registro eliminado satisfactoriamente.", "Control Credito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro" + ex);
            this.jTFNombreCiudad.requestFocus();
        }
    }
    
    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "Esta seguro que desea Eliminar el registro", "Control Credito",
                JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            try {
                eliminarCiudad();
                poblarTablaCiudades();
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
        jTblCiudades = new javax.swing.JTable();
        jTFNombreCiudad = new javax.swing.JTextField();
        jTFIdCiudad = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Ciudad", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jTblCiudades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Ciudad", "Nombre Ciudad"
            }
        ));
        jTblCiudades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblCiudadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblCiudades);

        jTFIdCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFIdCiudadActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Ciudad");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre Ciudad");

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
                            .addComponent(jTFIdCiudad, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jTFNombreCiudad)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel1)))
                .addGap(36, 36, 36)
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
                        .addComponent(jTFIdCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFNombreCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap(34, Short.MAX_VALUE))
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

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        try {
            editar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al almacenar el registro" + ex);
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

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

    private void jTFIdCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFIdCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFIdCiudadActionPerformed

    private void jTblCiudadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblCiudadesMouseClicked
        filaSeleccionada();
        habilitarBotones(false,true,true,true);
    }//GEN-LAST:event_jTblCiudadesMouseClicked

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
    private javax.swing.JTextField jTFIdCiudad;
    private javax.swing.JTextField jTFNombreCiudad;
    private javax.swing.JTable jTblCiudades;
    // End of variables declaration//GEN-END:variables
}
