package com.fortis.laoriental.vista;

import com.fortis.laoriental.modelo.Usuario;
import com.fortis.laoriental.seguridad.PermisosRol;

public class MenuPrincipalFrame extends javax.swing.JFrame {

    private final Usuario usuario;

    public MenuPrincipalFrame(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
        configurarVista();
        configurarPermisos();
    }

    private void configurarVista() {
        setTitle("Taqueria La Oriental - Sistema POS");
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Menu Principal");
        javax.swing.JPanel centro = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        centro.setBackground(EstiloSwing.FONDO);
        centro.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));
        lblBienvenida.setText("Sesion: " + usuario.getNombre() + "     Rol: " + usuario.getRol());
        lblBienvenida.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(EstiloSwing.BORDE),
            javax.swing.BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        lblBienvenida.setOpaque(true);
        lblBienvenida.setBackground(new java.awt.Color(230, 230, 230));
        centro.add(lblBienvenida, java.awt.BorderLayout.NORTH);

        javax.swing.JPanel grid = new javax.swing.JPanel(new java.awt.GridLayout(0, 4, 16, 16));
        grid.setOpaque(false);
        for (javax.swing.JButton b : new javax.swing.JButton[]{btnUsuarios, btnProductos, btnMesas, btnPedidos, btnCocina, btnCuenta, btnReportes}) {
            b.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
            b.setPreferredSize(new java.awt.Dimension(165, 105));
            EstiloSwing.botonSecundario(b);
            grid.add(b);
        }
        centro.add(grid, java.awt.BorderLayout.CENTER);
        btnSalir.setText("Cerrar sesion");
        EstiloSwing.botonPrimario(btnSalir);
        javax.swing.JPanel sur = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnSalir);
        centro.add(sur, java.awt.BorderLayout.SOUTH);
        raiz.add(centro, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void configurarPermisos() {
        String rol = usuario.getRol();
        btnUsuarios.setEnabled(PermisosRol.puedeAcceder(rol, "USUARIOS"));
        btnProductos.setEnabled(PermisosRol.puedeAcceder(rol, "PRODUCTOS"));
        btnMesas.setEnabled(PermisosRol.puedeAcceder(rol, "MESAS"));
        btnPedidos.setEnabled(PermisosRol.puedeAcceder(rol, "PEDIDOS"));
        btnCocina.setEnabled(PermisosRol.puedeAcceder(rol, "COCINA"));
        btnCuenta.setEnabled(PermisosRol.puedeAcceder(rol, "CUENTA"));
        btnReportes.setEnabled(PermisosRol.puedeAcceder(rol, "REPORTES"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblBienvenida = new javax.swing.JLabel();
        btnUsuarios = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnMesas = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnCocina = new javax.swing.JButton();
        btnCuenta = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setTitle("Menu principal - La Oriental");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBienvenida.setFont(new java.awt.Font("Arial", 1, 16));
        lblBienvenida.setText("Bienvenido: " + usuario.getNombre() + " | Rol: " + usuario.getRol());

        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(e -> abrirModulo(new UsuariosFrame()));

        btnProductos.setText("Productos");
        btnProductos.addActionListener(e -> abrirModulo(new FrmProductos()));

        btnMesas.setText("Mesas");
        btnMesas.addActionListener(e -> abrirModulo(new FrmMesas()));

        btnPedidos.setText("Pedidos");
        btnPedidos.addActionListener(e -> abrirModulo(new PedidosFrame()));
        btnCocina.setText("Cocina");
        btnCocina.addActionListener(e -> abrirModulo(new CocinaFrame()));
        btnCuenta.setText("Cuenta");
        btnCuenta.addActionListener(e -> abrirModulo(new CuentaFrame()));
        btnReportes.setText("Reportes");
        btnReportes.addActionListener(e -> abrirModulo(new ReportesFrame()));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(e -> cerrarSesion());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(30, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(lblBienvenida)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCocina, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(lblBienvenida)
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnCocina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesion() {
        com.fortis.laoriental.util.SesionUsuario.setUsuarioActual(null);
        new LoginFrame().setVisible(true);
        dispose();
    }

    private void abrirModulo(javax.swing.JFrame modulo) {
        modulo.setVisible(true);
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCocina;
    private javax.swing.JButton btnCuenta;
    private javax.swing.JButton btnMesas;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel lblBienvenida;
    // End of variables declaration//GEN-END:variables
}
