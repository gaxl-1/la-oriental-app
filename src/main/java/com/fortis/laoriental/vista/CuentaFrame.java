package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.PedidoController;
import com.fortis.laoriental.modelo.DetallePedido;
import com.fortis.laoriental.modelo.PedidoResumen;
import com.fortis.laoriental.modelo.Usuario;
import com.fortis.laoriental.util.SesionUsuario;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CuentaFrame extends javax.swing.JFrame {

    private final transient PedidoController controller = new PedidoController();
    private int idPedidoSeleccionado;

    public CuentaFrame() {
        initComponents();
        configurarVista();
        cargarPedidos();
    }

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Cuenta y Cobro");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));
        javax.swing.JPanel tablas = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 16, 16));
        tablas.setOpaque(false);
        tablas.add(panelTabla("Pedidos listos para cobro", tblPedidos));
        tablas.add(panelTabla("Detalle", tblDetalle));
        javax.swing.JPanel acciones = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        acciones.setOpaque(false);
        EstiloSwing.botonPrimario(btnCerrarPedido);
        EstiloSwing.botonSecundario(btnRefrescar);
        acciones.add(btnCerrarPedido);
        acciones.add(btnRefrescar);
        javax.swing.JButton btnVolver = new javax.swing.JButton("Volver al menu");
        EstiloSwing.botonSecundario(btnVolver);
        btnVolver.addActionListener(e -> EstiloSwing.volverAlMenu(this));
        acciones.add(btnVolver);
        contenido.add(tablas, java.awt.BorderLayout.CENTER);
        contenido.add(acciones, java.awt.BorderLayout.SOUTH);
        raiz.add(contenido, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private javax.swing.JPanel panelTabla(String titulo, javax.swing.JTable tabla) {
        javax.swing.JPanel panel = EstiloSwing.panel(titulo);
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(EstiloSwing.scroll(tabla), java.awt.BorderLayout.CENTER);
        return panel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPanePedidos = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        jScrollPaneDetalle = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        btnCerrarPedido = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();

        setTitle("Cuenta y cierre de pedido");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"ID", "Mesa", "Estado", "Total"}));
        tblPedidos.getSelectionModel().addListSelectionListener(e -> seleccionarPedido());
        jScrollPanePedidos.setViewportView(tblPedidos);
        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"Producto", "Cantidad", "P. Unitario", "Subtotal"}));
        jScrollPaneDetalle.setViewportView(tblDetalle);
        btnCerrarPedido.setText("Cerrar y registrar venta");
        btnCerrarPedido.addActionListener(e -> cerrarPedido());
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(e -> cargarPedidos());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPanePedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup().addComponent(btnCerrarPedido).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btnRefrescar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPanePedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnCerrarPedido).addComponent(btnRefrescar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cargarPedidos() {
        DefaultTableModel m = (DefaultTableModel) tblPedidos.getModel();
        m.setRowCount(0);
        for (PedidoResumen p : controller.listarPedidosParaCuenta()) {
            m.addRow(new Object[]{p.getIdPedido(), p.getNumeroMesa(), p.getEstado(), p.getTotal()});
        }
        ((DefaultTableModel) tblDetalle.getModel()).setRowCount(0);
    }

    private void seleccionarPedido() {
        int fila = tblPedidos.getSelectedRow();
        if (fila < 0) return;
        idPedidoSeleccionado = Integer.parseInt(String.valueOf(tblPedidos.getValueAt(fila, 0)));
        DefaultTableModel m = (DefaultTableModel) tblDetalle.getModel();
        m.setRowCount(0);
        for (DetallePedido d : controller.listarDetallePedido(idPedidoSeleccionado)) {
            m.addRow(new Object[]{d.getNombreProducto(), d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal()});
        }
    }

    private void cerrarPedido() {
        if (idPedidoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }
        Usuario usuario = SesionUsuario.getUsuarioActual();
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario en sesion.");
            return;
        }
        String obs = JOptionPane.showInputDialog(this, "Observaciones:", "Pago registrado");
        if (obs == null) obs = "";
        if (controller.cerrarPedido(idPedidoSeleccionado, usuario.getIdUsuario(), obs)) {
            JOptionPane.showMessageDialog(this, "Venta registrada y mesa liberada.");
            idPedidoSeleccionado = 0;
            cargarPedidos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cerrar. Verifique que tenga productos y que no existan comandas pendientes o en cocina.");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarPedido;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JScrollPane jScrollPaneDetalle;
    private javax.swing.JScrollPane jScrollPanePedidos;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblPedidos;
    // End of variables declaration//GEN-END:variables
}
