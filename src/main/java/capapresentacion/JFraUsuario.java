/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package capapresentacion;

import capadatos.CDUsuario;
import capalogica.CLUsuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class JFraUsuario extends javax.swing.JInternalFrame {
    DefaultTableModel dtm;
    /**
     * Creates new form JFraUsuario
     */
    public JFraUsuario() {
        initComponents();
        dtm = (DefaultTableModel) this.jTblDatosUsuario.getModel();
    }
    
    private void habilitarBotones() {
        this.jBtnGuardar.setEnabled(true);
        this.jBtnEliminar.setEnabled(true);
        this.jBtnEditar.setEnabled(true);
        this.jBtnLimpiar.setEnabled(true);
    }
    
    private void crearArchivos() throws IOException {
        
        File archivo = new File ("archivoUsuario.txt");
        
        if (archivo.exists()){
            habilitarBotones();
        } else {
            JOptionPane.showMessageDialog(null,"No se encuentra el archivo, proceda a crear el archivos }",
                    "Gestion Archivos", 1 );
            archivo.createNewFile();
            habilitarBotones();
        }
    }
    
    private void poblarTablaUsuarios() throws SQLException {
        limpiarTabla(); // método que limpia el modelo de la tabla
        CDUsuario cdUsuario = new CDUsuario(); // capa de datos
        List<CLUsuario> lista = cdUsuario.obtenerListaUsuario(); // obtiene la lista
        DefaultTableModel temp = (DefaultTableModel) this.jTblDatosUsuario.getModel(); // modelo de la tabla

        lista.forEach((CLUsuario u) -> {
            Object[] fila = new Object[5];
            fila[0] = u.getIdUsuario();
            fila[1] = u.getNombreUsuario();
            fila[2] = u.getContraseña(); // o getContraseña() si mantienes la tilde
            fila[3] = u.isEstado() ? "Activo" : "Inactivo"; // muestra estado como texto
            fila[4] = u.getRol();
            temp.addRow(fila);
        });
}

    
    private void guardarArchivo() {
        
        try {
           FileWriter salvarArchivo = new FileWriter("archivoProducto.txt");
           for (int i = 0; i < this.jTblDatosUsuario.getRowCount(); i++) {
               salvarArchivo.write(dtm.getValueAt(i,0).toString()+ "\n");
               salvarArchivo.write(dtm.getValueAt(i,1).toString()+ "\n");
               salvarArchivo.write(dtm.getValueAt(i,2).toString()+ "\n");
           }
           salvarArchivo.close();
           JOptionPane.showMessageDialog(null,"Datos almacenados satisfactoriamente",
                    "Gestion Archivos", 1 );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error" + ex);
        }
    }
    
    private void filaSeleccionada() {
        if (this.jTblDatosUsuario.getSelectedRow() != -1) {
            this.jTFidUsuario.setText(String.valueOf(this.jTblDatosUsuario.getValueAt(this.jTblDatosUsuario.getSelectedRow(), 0)));
            this.jTFnombreUsuario.setText(String.valueOf(this.jTblDatosUsuario.getValueAt(this.jTblDatosUsuario.getSelectedRow(), 1)));
        }
    }
    
    private void limpiarTabla() {
        int fila = this.jTblDatosUsuario.getRowCount();
        for (int i = fila - 1; i>= 0; i--) {
            dtm.removeRow(i);
        }
    }
    private void encontrarCorrelativo() throws SQLException {
        CDUsuario cdUsuario = new CDUsuario();
        this.jTFidUsuario.setText(String.valueOf(cdUsuario.autoIncrementarUsuarioID()));
    }
    
        private void eliminarUsuario() {
        try {
            CDUsuario cdUsuario = new CDUsuario(); 
            CLUsuario u = new CLUsuario();         
            u.setIdUsuario(Integer.parseInt(jTFidUsuario.getText().trim())); 
            cdUsuario.eliminarUsuario(u);          // método que ejecuta el DELETE
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.", "Usuarios", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID inválido. Debe ser un número entero.");
        }
    }

    private void eliminar() throws SQLException {
        int resp = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el usuario?", "Usuarios", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            eliminarUsuario();
            poblarTablaUsuarios(); // método que actualiza la tabla
            encontrarCorrelativo(); // si usas correlativos para nuevos IDs
        }
    }

    
    private boolean limpiarTextField() {
        this.jTFidUsuario.setText("");
        this.jTFnombreUsuario.setText("");
        this.jTFcontraseña.requestFocus();
        return false;
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
        jTblDatosUsuario = new javax.swing.JTable();
        jTFcontraseña = new javax.swing.JTextField();
        jTFidUsuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnGuardar = new javax.swing.JButton();
        jBtnLimpiar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnEliminar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTFnombreUsuario = new javax.swing.JTextField();
        jCBEstado = new javax.swing.JComboBox<>();
        jCBRol = new javax.swing.JComboBox<>();

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jTblDatosUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idUsuario", "nombreUsuario", "contraseña", "estado", "rol"
            }
        ));
        jTblDatosUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblDatosUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblDatosUsuario);

        jTFidUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFidUsuarioActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID Usuario");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre");

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
        jLabel3.setText("Contraseña");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("rol");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Estado");

        jTFnombreUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFnombreUsuarioActionPerformed(evt);
            }
        });

        jCBEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Activo", "Inactivo" }));

        jCBRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Admin", "User" }));

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
                            .addComponent(jTFidUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(jTFcontraseña)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCBEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(jCBRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jTFnombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 418, Short.MAX_VALUE)
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFidUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTFnombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFcontraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(jCBEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBtnGuardar)
                        .addComponent(jBtnEditar)
                        .addComponent(jBtnLimpiar)
                        .addComponent(jBtnEliminar))
                    .addComponent(jCBRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(146, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTblDatosUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblDatosUsuarioMouseClicked
        filaSeleccionada();
        habilitarBotones();
    }//GEN-LAST:event_jTblDatosUsuarioMouseClicked

    private void jTFidUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFidUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFidUsuarioActionPerformed

    private void jBtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarActionPerformed
        guardarArchivo();
    }//GEN-LAST:event_jBtnGuardarActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        limpiarTextField();
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEliminarActionPerformed
       
    }//GEN-LAST:event_jBtnEliminarActionPerformed

    private void jTFnombreUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFnombreUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFnombreUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnEliminar;
    private javax.swing.JButton jBtnGuardar;
    private javax.swing.JButton jBtnLimpiar;
    private javax.swing.JComboBox<String> jCBEstado;
    private javax.swing.JComboBox<String> jCBRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFcontraseña;
    private javax.swing.JTextField jTFidUsuario;
    private javax.swing.JTextField jTFnombreUsuario;
    private javax.swing.JTable jTblDatosUsuario;
    // End of variables declaration//GEN-END:variables
}
