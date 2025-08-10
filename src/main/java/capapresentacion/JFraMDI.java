/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package capapresentacion;

import javax.swing.JOptionPane;

/**
 *
 * @author quesi
 */
public class JFraMDI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFraMDI.class.getName());

    /**
     * Creates new form JFraMDI
     */
    public JFraMDI() {
        initComponents();
        setExtendedState(JFraMDI.MAXIMIZED_BOTH);
    }
    
    public boolean validarInicioSesion(){
        boolean estado;
        
        if (!this.jTFNombreUsuario.getText().equals("") && !this.jPFContraseña.getPassword().equals("")){
            estado = true;
            
            this.jMenu2.setEnabled(true);
            this.jMenu3.setEnabled(true);
            this.jMenu4.setEnabled(true);
            
        } else {
            estado = false;
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre de usuario y la contraseña", 
                    "Envoice System", JOptionPane.WARNING_MESSAGE);
        }
        return estado;
        
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDPPrincipal = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFNombreUsuario = new javax.swing.JTextField();
        jPFContraseña = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTFIdUsuario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMISalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMIFacturacion = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMICiudad = new javax.swing.JMenuItem();
        jMIProveedor = new javax.swing.JMenuItem();
        jMIProducto = new javax.swing.JMenuItem();
        jMIMarca = new javax.swing.JMenuItem();
        jMICliente = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMIUsuario = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Iniciar Sesion / Registrarse");

        jLabel2.setText("Nombre de Usuario");

        jLabel3.setText("Contraseña");

        jLabel4.setText("idUsuario");

        jTFIdUsuario.setEnabled(false);

        jButton1.setText("Ingresar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jTFNombreUsuario)
                            .addComponent(jLabel3)
                            .addComponent(jPFContraseña)
                            .addComponent(jLabel4)
                            .addComponent(jTFIdUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jButton1)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFIdUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jDPPrincipal.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDPPrincipalLayout = new javax.swing.GroupLayout(jDPPrincipal);
        jDPPrincipal.setLayout(jDPPrincipalLayout);
        jDPPrincipalLayout.setHorizontalGroup(
            jDPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDPPrincipalLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        jDPPrincipalLayout.setVerticalGroup(
            jDPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDPPrincipalLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jMenu1.setText("Archivo");

        jMISalir.setText("Salir");
        jMenu1.add(jMISalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Transacciones");
        jMenu2.setEnabled(false);

        jMIFacturacion.setText("Facturacion");
        jMenu2.add(jMIFacturacion);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Mantenimiento");
        jMenu3.setEnabled(false);

        jMICiudad.setText("Ciudad");
        jMICiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMICiudadActionPerformed(evt);
            }
        });
        jMenu3.add(jMICiudad);

        jMIProveedor.setText("Proveedor");
        jMIProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIProveedorActionPerformed(evt);
            }
        });
        jMenu3.add(jMIProveedor);

        jMIProducto.setText("Producto");
        jMIProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIProductoActionPerformed(evt);
            }
        });
        jMenu3.add(jMIProducto);

        jMIMarca.setText("Marca");
        jMIMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIMarcaActionPerformed(evt);
            }
        });
        jMenu3.add(jMIMarca);

        jMICliente.setText("Cliente");
        jMICliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIClienteActionPerformed(evt);
            }
        });
        jMenu3.add(jMICliente);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Usuario");
        jMenu4.setEnabled(false);

        jMIUsuario.setText("Gestion usuario");
        jMenu4.add(jMIUsuario);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDPPrincipal)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDPPrincipal)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMICiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMICiudadActionPerformed
        JFraCiudad miCiudad = new JFraCiudad();
        jDPPrincipal.add(miCiudad);
        miCiudad.show();
    }//GEN-LAST:event_jMICiudadActionPerformed

    private void jMIProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIProveedorActionPerformed
        JFraProveedor proveedor = new JFraProveedor();
        jDPPrincipal.add(proveedor);
        proveedor.show();
    }//GEN-LAST:event_jMIProveedorActionPerformed

    private void jMIProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIProductoActionPerformed
        JFraProducto producto = new JFraProducto();
        jDPPrincipal.add(producto);
        producto.show();
    }//GEN-LAST:event_jMIProductoActionPerformed

    private void jMIMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIMarcaActionPerformed
        JFraMarca marca = new JFraMarca();
        jDPPrincipal.add(marca);
        marca.show();
    }//GEN-LAST:event_jMIMarcaActionPerformed

    private void jMIClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIClienteActionPerformed
        JFraCliente cliente = new JFraCliente();
        jDPPrincipal.add(cliente);
        cliente.show();
    }//GEN-LAST:event_jMIClienteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        validarInicioSesion();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JFraMDI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDPPrincipal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMICiudad;
    private javax.swing.JMenuItem jMICliente;
    private javax.swing.JMenuItem jMIFacturacion;
    private javax.swing.JMenuItem jMIMarca;
    private javax.swing.JMenuItem jMIProducto;
    private javax.swing.JMenuItem jMIProveedor;
    private javax.swing.JMenuItem jMISalir;
    private javax.swing.JMenuItem jMIUsuario;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPasswordField jPFContraseña;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTFIdUsuario;
    private javax.swing.JTextField jTFNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
