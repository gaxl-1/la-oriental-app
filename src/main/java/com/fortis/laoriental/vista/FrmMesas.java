package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.MesaController;
import com.fortis.laoriental.modelo.Mesa;

public class FrmMesas extends javax.swing.JFrame {

    private final transient MesaController controller = new MesaController();
    private int idSeleccionado;
    private javax.swing.JTextField txtFiltroMesas;
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnEliminarDefinitivo;

    public FrmMesas() {
        idSeleccionado = 0;
        initComponents();
        configurarVista();
        cargarMesas();
    }

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Gestion de Mesas");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));

        javax.swing.JPanel form = EstiloSwing.panel("Datos de la mesa");
        form.setLayout(new java.awt.GridLayout(0, 2, 8, 8));
        btnDesactivar.setText("Desactivar");
        btnActivar = new javax.swing.JButton("Activar");
        btnEliminarDefinitivo = new javax.swing.JButton("Eliminar");
        btnActivar.addActionListener(e -> activarMesa());
        btnEliminarDefinitivo.addActionListener(e -> eliminarDefinitivo());
        form.add(lblNumero); form.add(txtNumero);
        form.add(lblCapacidad); form.add(txtCapacidad);
        form.add(lblEstado); form.add(cmbEstado);
        form.add(lblUbicacion); form.add(txtUbicacion);
        form.add(new javax.swing.JLabel("Activa:")); form.add(chkActivo);
        EstiloSwing.botonPrimario(btnGuardar);
        EstiloSwing.botonSecundario(btnEditar);
        EstiloSwing.botonPrimario(btnCambiarEstado);
        EstiloSwing.botonSecundario(btnDesactivar);
        EstiloSwing.botonSecundario(btnActivar);
        EstiloSwing.botonSecundario(btnEliminarDefinitivo);
        EstiloSwing.botonSecundario(btnLimpiar);
        form.add(btnGuardar); form.add(btnLimpiar);
        form.add(btnEditar); form.add(btnDesactivar);
        form.add(btnActivar); form.add(btnEliminarDefinitivo);
        form.add(btnCambiarEstado);

        txtFiltroMesas = new javax.swing.JTextField();
        txtFiltroMesas.setToolTipText("Buscar mesa, estado o ubicacion");
        javax.swing.JPanel lista = EstiloSwing.panel("Mesas registradas");
        lista.setLayout(new java.awt.BorderLayout(8, 8));
        lista.add(txtFiltroMesas, java.awt.BorderLayout.NORTH);
        lista.add(EstiloSwing.scroll(tblMesas), java.awt.BorderLayout.CENTER);
        EstiloSwing.filtroTexto(txtFiltroMesas, tblMesas);

        if (com.fortis.laoriental.util.SesionUsuario.esMesero()) {
            txtNumero.setEnabled(false);
            txtCapacidad.setEnabled(false);
            txtUbicacion.setEnabled(false);
            chkActivo.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnDesactivar.setEnabled(false);
            btnActivar.setEnabled(false);
            btnEliminarDefinitivo.setEnabled(false);
        }

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

        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblCapacidad = new javax.swing.JLabel();
        txtCapacidad = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        lblUbicacion = new javax.swing.JLabel();
        txtUbicacion = new javax.swing.JTextField();
        chkActivo = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMesas = new javax.swing.JTable();
        btnCambiarEstado = new javax.swing.JButton();
        btnDesactivar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setTitle("Fortis IT Solutions - Mesas");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNumero.setText("Numero de mesa:");
        lblCapacidad.setText("Capacidad:");
        lblEstado.setText("Estado:");
        lblUbicacion.setText("Ubicacion:");

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DISPONIBLE", "OCUPADA" }));

        chkActivo.setText("Activo");
        chkActivo.setSelected(true);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(e -> guardarMesa());

        btnEditar.setText("Editar");
        btnEditar.addActionListener(e -> editarMesa());

        tblMesas.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "Numero", "Capacidad", "Estado", "Ubicacion", "Activo" }
        ));
        tblMesas.getSelectionModel().addListSelectionListener(e -> seleccionarMesa());
        jScrollPane1.setViewportView(tblMesas);

        btnCambiarEstado.setText("Cambiar estado");
        btnCambiarEstado.addActionListener(e -> cambiarEstadoMesa());

        btnDesactivar.setText("Desactivar");
        btnDesactivar.addActionListener(e -> desactivarMesa());

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(15, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNumero)
                                .addComponent(lblCapacidad)
                                .addComponent(lblEstado)
                                .addComponent(lblUbicacion))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNumero)
                                .addComponent(txtCapacidad)
                                .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtUbicacion, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                            .addGap(30, 30, 30)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chkActivo)
                                .addComponent(btnGuardar)
                                .addComponent(btnEditar))))
                    .addContainerGap(15, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(15, Short.MAX_VALUE)
                    .addComponent(btnCambiarEstado)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnDesactivar)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnLimpiar)
                    .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(15, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNumero)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkActivo))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCapacidad)
                        .addComponent(txtCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuardar))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEstado)
                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditar))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUbicacion)
                        .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCambiarEstado)
                        .addComponent(btnDesactivar)
                        .addComponent(btnLimpiar))
                    .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarMesa() {
        String mensaje = controller.guardar(
            txtNumero.getText(), txtCapacidad.getText(),
            cmbEstado.getSelectedItem().toString(), txtUbicacion.getText(),
            chkActivo.isSelected()
        );
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarMesas();
        limpiarFormulario();
    }

    private void editarMesa() {
        String mensaje = controller.actualizar(
            idSeleccionado, txtNumero.getText(), txtCapacidad.getText(),
            cmbEstado.getSelectedItem().toString(), txtUbicacion.getText(),
            chkActivo.isSelected()
        );
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarMesas();
        limpiarFormulario();
    }

    private void cambiarEstadoMesa() {
        String nuevoEstado = cmbEstado.getSelectedItem().toString();
        String mensaje = controller.cambiarEstado(idSeleccionado, nuevoEstado);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarMesas();
        limpiarFormulario();
    }

    private void desactivarMesa() {
        String mensaje = controller.actualizarActivo(idSeleccionado, false);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarMesas();
        limpiarFormulario();
    }

    private void activarMesa() {
        String mensaje = controller.actualizarActivo(idSeleccionado, true);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarMesas();
        limpiarFormulario();
    }

    private void eliminarDefinitivo() {
        int ok = javax.swing.JOptionPane.showConfirmDialog(this, "Eliminar definitivamente la mesa seleccionada?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (ok != javax.swing.JOptionPane.YES_OPTION) return;
        String mensaje = controller.eliminar(idSeleccionado);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarMesas();
        limpiarFormulario();
    }

    private void cargarMesas() {
        ((javax.swing.table.DefaultTableModel) tblMesas.getModel()).setRowCount(0);
        for (Mesa m : controller.listarTodas()) {
            ((javax.swing.table.DefaultTableModel) tblMesas.getModel()).addRow(new Object[]{
                m.getIdMesa(), m.getNumero(), m.getCapacidad(),
                m.getEstado(), m.getUbicacion(), EstiloSwing.activoTexto(m.isActivo())
            });
        }
    }

    private void seleccionarMesa() {
        int fila = EstiloSwing.filaModelo(tblMesas);
        if (fila >= 0) {
            javax.swing.table.TableModel modelo = tblMesas.getModel();
            idSeleccionado = Integer.parseInt(String.valueOf(modelo.getValueAt(fila, 0)));
            txtNumero.setText(String.valueOf(modelo.getValueAt(fila, 1)));
            txtCapacidad.setText(String.valueOf(modelo.getValueAt(fila, 2)));
            cmbEstado.setSelectedItem(String.valueOf(modelo.getValueAt(fila, 3)));
            txtUbicacion.setText(String.valueOf(modelo.getValueAt(fila, 4)));
            chkActivo.setSelected(EstiloSwing.textoActivo(modelo.getValueAt(fila, 5)));
            actualizarBotonesEstado();
        }
    }

    private boolean mesaSeleccionadaActiva() {
        int fila = EstiloSwing.filaModelo(tblMesas);
        return fila >= 0 && EstiloSwing.textoActivo(tblMesas.getModel().getValueAt(fila, 5));
    }

    private void actualizarBotonesEstado() {
        boolean activo = mesaSeleccionadaActiva();
        btnDesactivar.setEnabled(activo && !com.fortis.laoriental.util.SesionUsuario.esMesero());
        btnActivar.setEnabled(!activo && !com.fortis.laoriental.util.SesionUsuario.esMesero());
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtNumero.setText("");
        txtCapacidad.setText("");
        txtUbicacion.setText("");
        cmbEstado.setSelectedItem("DISPONIBLE");
        chkActivo.setSelected(true);
        btnDesactivar.setEnabled(!com.fortis.laoriental.util.SesionUsuario.esMesero());
        if (btnActivar != null) btnActivar.setEnabled(false);
        tblMesas.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarEstado;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCapacidad;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblUbicacion;
    private javax.swing.JTable tblMesas;
    private javax.swing.JTextField txtCapacidad;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtUbicacion;
    // End of variables declaration//GEN-END:variables
}
