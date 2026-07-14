package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.LoginController;
import com.fortis.laoriental.modelo.Usuario;
import com.fortis.laoriental.util.SesionUsuario;

public class LoginFrame extends javax.swing.JFrame {

    private final transient LoginController controlador = new LoginController();

    public LoginFrame() {
        initComponents();
        configurarVista();
        configurarEventos();
    }

    private void configurarVista() {
        setTitle("Taqueria La Oriental - Sistema POS");
        setSize(1200, 760);
        setLocationRelativeTo(null);
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Iniciar sesion");
        javax.swing.JPanel centro = new javax.swing.JPanel(new java.awt.GridBagLayout());
        centro.setBackground(EstiloSwing.FONDO);
        javax.swing.JPanel tarjeta = EstiloSwing.panel(null);
        tarjeta.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(8, 12, 8, 12);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        javax.swing.JLabel icono = EstiloSwing.logoLabel(190, 90);
        tarjeta.add(icono, gbc);
        gbc.gridy++;
        jLabel1.setText("Taqueria La Oriental");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        tarjeta.add(jLabel1, gbc);
        gbc.gridy++;
        javax.swing.JLabel sub = new javax.swing.JLabel("Sistema de Punto de Venta", javax.swing.SwingConstants.CENTER);
        sub.setForeground(new java.awt.Color(120, 120, 120));
        tarjeta.add(sub, gbc);
        gbc.gridy++;
        tarjeta.add(new javax.swing.JLabel("Usuario"), gbc);
        gbc.gridy++;
        txtUsuario.setColumns(26);
        tarjeta.add(txtUsuario, gbc);
        gbc.gridy++;
        tarjeta.add(new javax.swing.JLabel("Contrasena"), gbc);
        gbc.gridy++;
        tarjeta.add(txtContrasena, gbc);
        gbc.gridy++;
        EstiloSwing.botonPrimario(btnEntrar);
        tarjeta.add(btnEntrar, gbc);
        gbc.gridy++;
        EstiloSwing.botonSecundario(btnSalir);
        tarjeta.add(btnSalir, gbc);
        centro.add(tarjeta);
        raiz.add(centro, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void configurarEventos() {
        txtUsuario.addActionListener(e -> txtContrasena.requestFocus());
        txtContrasena.addActionListener(e -> iniciarSesion());
        btnEntrar.addActionListener(e -> iniciarSesion());
        btnSalir.addActionListener(e -> dispose());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsuario = new javax.swing.JTextField();
        txtContrasena = new javax.swing.JPasswordField();
        btnEntrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - POS La Oriental");

        btnEntrar.setText("Iniciar sesion");

        btnSalir.setText("Salir");

        jLabel1.setText("Inicio de Sesion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsuario)
                    .addComponent(txtContrasena)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEntrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir)))
                .addGap(100, 100, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(140, 140, 140))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEntrar)
                    .addComponent(btnSalir))
                .addGap(60, 60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iniciarSesion() {
        try {
            String username = txtUsuario.getText();
            String contrasena = String.valueOf(txtContrasena.getPassword());
            Usuario usuario = controlador.iniciarSesion(username, contrasena);
            if (usuario != null) {
                SesionUsuario.setUsuarioActual(usuario);
                new MenuPrincipalFrame(usuario).setVisible(true);
                dispose();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contrasena incorrectos");
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
