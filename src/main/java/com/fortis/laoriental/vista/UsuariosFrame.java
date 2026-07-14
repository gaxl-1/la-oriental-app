package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.UsuarioController;
import com.fortis.laoriental.modelo.Usuario;

public class UsuariosFrame extends javax.swing.JFrame {

    private final transient UsuarioController controller = new UsuarioController();
    private javax.swing.JTextField txtFiltroUsuarios;
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnEliminarDefinitivo;

    public UsuariosFrame() {
        initComponents();
        configurarVista();
        cargarUsuarios();
    }

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Gestion de Usuarios");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));

        javax.swing.JPanel form = EstiloSwing.panel("Datos del usuario");
        form.setLayout(new java.awt.GridLayout(0, 2, 8, 8));
        btnDesactivar.setText("Desactivar");
        btnActivar = new javax.swing.JButton("Activar");
        btnEliminarDefinitivo = new javax.swing.JButton("Eliminar");
        btnActivar.addActionListener(e -> activar());
        btnEliminarDefinitivo.addActionListener(e -> eliminarDefinitivo());
        for (java.awt.Component c : new java.awt.Component[]{lblId, txtId, lblNombre, txtNombre, lblUsername, txtUsername, lblContrasena, txtContrasena, lblRol, cmbRol}) {
            form.add(c);
        }
        EstiloSwing.botonPrimario(btnGuardar);
        EstiloSwing.botonSecundario(btnActualizar);
        EstiloSwing.botonSecundario(btnDesactivar);
        EstiloSwing.botonSecundario(btnActivar);
        EstiloSwing.botonSecundario(btnEliminarDefinitivo);
        EstiloSwing.botonSecundario(btnLimpiar);
        form.add(btnGuardar);
        form.add(btnLimpiar);
        form.add(btnActualizar);
        form.add(btnDesactivar);
        form.add(btnActivar);
        form.add(btnEliminarDefinitivo);

        javax.swing.JPanel lista = EstiloSwing.panel("Usuarios registrados");
        lista.setLayout(new java.awt.BorderLayout(8, 8));
        txtFiltroUsuarios = new javax.swing.JTextField();
        txtFiltroUsuarios.setToolTipText("Buscar usuario");
        lista.add(txtFiltroUsuarios, java.awt.BorderLayout.NORTH);
        lista.add(EstiloSwing.scroll(tabla), java.awt.BorderLayout.CENTER);
        EstiloSwing.filtroTexto(txtFiltroUsuarios, tabla);

        contenido.add(form, java.awt.BorderLayout.WEST);
        contenido.add(lista, java.awt.BorderLayout.CENTER);
        javax.swing.JButton btnVolver = new javax.swing.JButton("Volver al menu");
        EstiloSwing.botonSecundario(btnVolver);
        btnVolver.addActionListener(e -> EstiloSwing.volverAlMenu(this));
        javax.swing.JPanel sur = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnVolver);
        contenido.add(sur, java.awt.BorderLayout.SOUTH);
        raiz.add(contenido, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        lblRol = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnDesactivar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestion de usuarios");

        lblId.setText("ID:");

        txtId.setEditable(false);

        lblNombre.setText("Nombre:");

        lblUsername.setText("Username:");

        lblContrasena.setText("Contrasena:");

        lblRol.setText("Rol:");

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "administrador", "mesero", "cocina", "cajero" }));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(e -> actualizar());

        btnDesactivar.setText("Desactivar");
        btnDesactivar.addActionListener(e -> desactivar());

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(e -> limpiar());

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "Nombre", "Username", "Rol", "Activo" }
        ) {
            Class[] types = new Class[] { java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class };
            boolean[] canEdit = new boolean[] { false, false, false, false, false };

            public Class getColumnClass(int columnIndex) { return types[columnIndex]; }
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        tabla.getSelectionModel().addListSelectionListener(e -> seleccionarUsuario());
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblId)
                            .addComponent(lblNombre)
                            .addComponent(lblUsername)
                            .addComponent(lblContrasena)
                            .addComponent(lblRol))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtId)
                            .addComponent(txtNombre)
                            .addComponent(txtUsername)
                            .addComponent(txtContrasena)
                            .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar)
                            .addComponent(btnActualizar)
                            .addComponent(btnDesactivar)
                            .addComponent(btnLimpiar))))
                .addGap(15, 15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblId)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContrasena)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDesactivar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRol)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarUsuarios() {
        try {
            ((javax.swing.table.DefaultTableModel) tabla.getModel()).setRowCount(0);
            for (Usuario u : controller.listar()) {
                ((javax.swing.table.DefaultTableModel) tabla.getModel()).addRow(new Object[]{
                    u.getIdUsuario(), u.getNombre(), u.getUsername(),
                    u.getRol(), EstiloSwing.activoTexto(u.isActivo())
                });
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + ex.getMessage());
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String username = txtUsername.getText().trim();
        String contrasena = String.valueOf(txtContrasena.getPassword()).trim();
        String rol = String.valueOf(cmbRol.getSelectedItem());

        String mensaje = controller.guardar(nombre, username, contrasena, rol);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);

        if (mensaje.contains("correctamente")) {
            limpiar();
            cargarUsuarios();
        }
    }

    private void actualizar() {
        if (txtId.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        String nombre = txtNombre.getText().trim();
        String username = txtUsername.getText().trim();
        String contrasena = String.valueOf(txtContrasena.getPassword()).trim();
        String rol = String.valueOf(cmbRol.getSelectedItem());

        boolean activo = usuarioSeleccionadoActivo();
        String mensaje = controller.actualizar(id, nombre, username, contrasena, rol, activo);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);

        if (mensaje.contains("correctamente")) {
            limpiar();
            cargarUsuarios();
        }
    }

    private void desactivar() {
        if (txtId.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        String mensaje = controller.actualizarActivo(id, false);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);

        if (mensaje.contains("correctamente")) {
            limpiar();
            cargarUsuarios();
        }
    }

    private void activar() {
        if (txtId.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.");
            return;
        }
        String mensaje = controller.actualizarActivo(Integer.parseInt(txtId.getText()), true);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.contains("correctamente")) {
            limpiar();
            cargarUsuarios();
        }
    }

    private void eliminarDefinitivo() {
        if (txtId.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.");
            return;
        }
        int ok = javax.swing.JOptionPane.showConfirmDialog(this, "Eliminar definitivamente el usuario seleccionado?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (ok != javax.swing.JOptionPane.YES_OPTION) return;
        String mensaje = controller.eliminar(Integer.parseInt(txtId.getText()));
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        limpiar();
        cargarUsuarios();
    }

    private void seleccionarUsuario() {
        int fila = EstiloSwing.filaModelo(tabla);
        if (fila >= 0) {
            javax.swing.table.TableModel modelo = tabla.getModel();
            txtId.setText(String.valueOf(modelo.getValueAt(fila, 0)));
            txtNombre.setText(String.valueOf(modelo.getValueAt(fila, 1)));
            txtUsername.setText(String.valueOf(modelo.getValueAt(fila, 2)));
            txtContrasena.setText("");
            cmbRol.setSelectedItem(String.valueOf(modelo.getValueAt(fila, 3)));
            actualizarBotonesEstado();
        }
    }

    private boolean usuarioSeleccionadoActivo() {
        int fila = EstiloSwing.filaModelo(tabla);
        return fila >= 0 && EstiloSwing.textoActivo(tabla.getModel().getValueAt(fila, 4));
    }

    private void actualizarBotonesEstado() {
        boolean activo = usuarioSeleccionadoActivo();
        btnDesactivar.setEnabled(activo);
        btnActivar.setEnabled(!activo);
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtUsername.setText("");
        txtContrasena.setText("");
        cmbRol.setSelectedIndex(0);
        btnDesactivar.setEnabled(true);
        if (btnActivar != null) btnActivar.setEnabled(false);
        tabla.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox cmbRol;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTable tabla;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
