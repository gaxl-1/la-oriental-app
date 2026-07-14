package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.ProductoController;
import com.fortis.laoriental.modelo.Producto;

public class FrmProductos extends javax.swing.JFrame {

    private final transient ProductoController controller = new ProductoController();
    private int idSeleccionado;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtFiltroProductos;
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnEliminarDefinitivo;

    public FrmProductos() {
        idSeleccionado = 0;
        initComponents();
        configurarVista();
        cargarProductos();
    }

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Productos / Menu Digital");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));

        javax.swing.JPanel form = EstiloSwing.panel("Datos del producto");
        form.setLayout(new java.awt.GridLayout(0, 2, 8, 8));
        txtStock = new javax.swing.JTextField("0");
        btnDesactivar.setText("Desactivar");
        btnActivar = new javax.swing.JButton("Activar");
        btnEliminarDefinitivo = new javax.swing.JButton("Eliminar");
        btnActivar.addActionListener(e -> activarProducto());
        btnEliminarDefinitivo.addActionListener(e -> eliminarDefinitivo());
        form.add(lblNombre); form.add(txtNombre);
        form.add(lblCategoria); form.add(txtCategoria);
        form.add(lblPrecio); form.add(txtPrecio);
        form.add(new javax.swing.JLabel("Stock:")); form.add(txtStock);
        form.add(new javax.swing.JLabel("Estado:")); form.add(chkActivo);
        EstiloSwing.botonPrimario(btnGuardar);
        EstiloSwing.botonSecundario(btnEditar);
        EstiloSwing.botonSecundario(btnDesactivar);
        EstiloSwing.botonSecundario(btnActivar);
        EstiloSwing.botonSecundario(btnEliminarDefinitivo);
        EstiloSwing.botonSecundario(btnLimpiar);
        form.add(btnGuardar); form.add(btnLimpiar);
        form.add(btnEditar); form.add(btnDesactivar);
        form.add(btnActivar); form.add(btnEliminarDefinitivo);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {}, new String[] { "ID", "Nombre", "Categoria", "Precio", "Stock", "Activo" }
        ) {
            boolean[] canEdit = new boolean[] { false, false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        txtFiltroProductos = new javax.swing.JTextField();
        txtFiltroProductos.setToolTipText("Buscar producto, categoria o estado");
        javax.swing.JPanel lista = EstiloSwing.panel("Lista de productos");
        lista.setLayout(new java.awt.BorderLayout(8, 8));
        lista.add(txtFiltroProductos, java.awt.BorderLayout.NORTH);
        lista.add(EstiloSwing.scroll(tblProductos), java.awt.BorderLayout.CENTER);
        EstiloSwing.filtroTexto(txtFiltroProductos, tblProductos);

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

        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        chkActivo = new javax.swing.JCheckBox();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnDesactivar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setTitle("Fortis IT Solutions - Productos / Menu digital");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNombre.setText("Nombre:");
        lblCategoria.setText("Categoria:");
        lblPrecio.setText("Precio:");

        chkActivo.setText("Activo");
        chkActivo.setSelected(true);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(e -> guardarProducto());

        btnEditar.setText("Editar");
        btnEditar.addActionListener(e -> editarProducto());

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] { "ID", "Nombre", "Categoria", "Precio", "Activo" }
        ));
        tblProductos.getSelectionModel().addListSelectionListener(e -> seleccionarProducto());
        jScrollPane1.setViewportView(tblProductos);

        btnDesactivar.setText("Desactivar");
        btnDesactivar.addActionListener(e -> desactivarProducto());

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
                                .addComponent(lblNombre)
                                .addComponent(lblCategoria)
                                .addComponent(lblPrecio))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombre)
                                .addComponent(txtCategoria)
                                .addComponent(txtPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                            .addGap(30, 30, 30)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chkActivo)
                                .addComponent(btnGuardar)
                                .addComponent(btnEditar)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnDesactivar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLimpiar)))
                    .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(15, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNombre)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(chkActivo))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCategoria)
                        .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGuardar))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPrecio)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditar))
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDesactivar)
                        .addComponent(btnLimpiar))
                    .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarProducto() {
        String mensaje = controller.guardar(
            txtNombre.getText(), txtCategoria.getText(),
            txtPrecio.getText(), chkActivo.isSelected(), txtStock.getText()
        );
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarProductos();
        limpiarFormulario();
    }

    private void editarProducto() {
        String mensaje = controller.actualizar(
            idSeleccionado, txtNombre.getText(), txtCategoria.getText(),
            txtPrecio.getText(), chkActivo.isSelected(), txtStock.getText()
        );
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarProductos();
        limpiarFormulario();
    }

    private void desactivarProducto() {
        String mensaje = controller.actualizarActivo(idSeleccionado, false);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarProductos();
        limpiarFormulario();
    }

    private void activarProducto() {
        String mensaje = controller.actualizarActivo(idSeleccionado, true);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarProductos();
        limpiarFormulario();
    }

    private void eliminarDefinitivo() {
        int ok = javax.swing.JOptionPane.showConfirmDialog(this, "Eliminar definitivamente el producto seleccionado?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (ok != javax.swing.JOptionPane.YES_OPTION) return;
        String mensaje = controller.eliminar(idSeleccionado);
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        cargarProductos();
        limpiarFormulario();
    }

    private void cargarProductos() {
        ((javax.swing.table.DefaultTableModel) tblProductos.getModel()).setRowCount(0);
        for (Producto p : controller.listarTodos()) {
            ((javax.swing.table.DefaultTableModel) tblProductos.getModel()).addRow(new Object[]{
                p.getIdProducto(), p.getNombre(), p.getCategoria(),
                p.getPrecio(), p.getStock(), EstiloSwing.activoTexto(p.isActivo())
            });
        }
    }

    private void seleccionarProducto() {
        int fila = EstiloSwing.filaModelo(tblProductos);
        if (fila >= 0) {
            javax.swing.table.TableModel modelo = tblProductos.getModel();
            idSeleccionado = Integer.parseInt(String.valueOf(modelo.getValueAt(fila, 0)));
            txtNombre.setText(String.valueOf(modelo.getValueAt(fila, 1)));
            txtCategoria.setText(String.valueOf(modelo.getValueAt(fila, 2)));
            txtPrecio.setText(String.valueOf(modelo.getValueAt(fila, 3)));
            txtStock.setText(String.valueOf(modelo.getValueAt(fila, 4)));
            chkActivo.setSelected(EstiloSwing.textoActivo(modelo.getValueAt(fila, 5)));
            actualizarBotonesEstado();
        }
    }

    private boolean productoSeleccionadoActivo() {
        int fila = EstiloSwing.filaModelo(tblProductos);
        return fila >= 0 && EstiloSwing.textoActivo(tblProductos.getModel().getValueAt(fila, 5));
    }

    private void actualizarBotonesEstado() {
        boolean activo = productoSeleccionadoActivo();
        btnDesactivar.setEnabled(activo);
        btnActivar.setEnabled(!activo);
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("0");
        chkActivo.setSelected(true);
        btnDesactivar.setEnabled(true);
        if (btnActivar != null) btnActivar.setEnabled(false);
        tblProductos.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox chkActivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
