package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.PedidoController;
import com.fortis.laoriental.modelo.ComandaResumen;
import com.fortis.laoriental.modelo.DetallePedido;
import javax.swing.table.DefaultTableModel;

public class CocinaFrame extends javax.swing.JFrame {

    private final transient PedidoController controller = new PedidoController();
    private int idComandaSeleccionada;

    public CocinaFrame() {
        initComponents();
        cargarPedidos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPanePedidos = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        jScrollPaneDetalle = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        btnRefrescar = new javax.swing.JButton();
        btnListo = new javax.swing.JButton();

        setTitle("Vista de cocina");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"Comanda", "Cuenta", "Mesa", "Mesero", "Estado", "Total"}));
        tblPedidos.getSelectionModel().addListSelectionListener(e -> seleccionarComanda());
        jScrollPanePedidos.setViewportView(tblPedidos);
        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"Producto", "Cantidad", "P. Unitario", "Subtotal"}));
        jScrollPaneDetalle.setViewportView(tblDetalle);
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(e -> cargarPedidos());
        btnListo.setText("Marcar listo");
        btnListo.addActionListener(e -> marcarListo());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPanePedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRefrescar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPanePedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnRefrescar).addComponent(btnListo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
        setLocationRelativeTo(null);
        configurarVista();
    }// </editor-fold>//GEN-END:initComponents

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Cocina");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));
        javax.swing.JPanel tablas = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 16, 16));
        tablas.setOpaque(false);
        tablas.add(panelTabla("Pedidos en cocina", tblPedidos));
        tablas.add(panelTabla("Detalle", tblDetalle));
        javax.swing.JPanel acciones = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        acciones.setOpaque(false);
        EstiloSwing.botonPrimario(btnListo);
        EstiloSwing.botonSecundario(btnRefrescar);
        acciones.add(btnListo);
        acciones.add(btnRefrescar);
        javax.swing.JButton btnVolver = new javax.swing.JButton("Volver al menu");
        EstiloSwing.botonSecundario(btnVolver);
        btnVolver.addActionListener(e -> EstiloSwing.volverAlMenu(this));
        acciones.add(btnVolver);
        contenido.add(tablas, java.awt.BorderLayout.CENTER);
        contenido.add(acciones, java.awt.BorderLayout.SOUTH);
        raiz.add(contenido, java.awt.BorderLayout.CENTER);
    }

    private javax.swing.JPanel panelTabla(String titulo, javax.swing.JTable tabla) {
        javax.swing.JPanel panel = EstiloSwing.panel(titulo);
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(EstiloSwing.scroll(tabla), java.awt.BorderLayout.CENTER);
        return panel;
    }

    private void cargarPedidos() {
        DefaultTableModel m = (DefaultTableModel) tblPedidos.getModel();
        m.setRowCount(0);
        for (ComandaResumen c : controller.listarComandasCocina()) {
            m.addRow(new Object[]{c.getIdComanda(), c.getIdPedido(), c.getNumeroMesa(), c.getMesero(), c.getEstado(), c.getTotal()});
        }
        ((DefaultTableModel) tblDetalle.getModel()).setRowCount(0);
    }

    private void seleccionarComanda() {
        int fila = tblPedidos.getSelectedRow();
        if (fila < 0) return;
        idComandaSeleccionada = Integer.parseInt(String.valueOf(tblPedidos.getValueAt(fila, 0)));
        DefaultTableModel m = (DefaultTableModel) tblDetalle.getModel();
        m.setRowCount(0);
        for (DetallePedido d : controller.listarDetalleComanda(idComandaSeleccionada)) {
            m.addRow(new Object[]{d.getNombreProducto(), d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal()});
        }
    }

    private void marcarListo() {
        if (idComandaSeleccionada <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una comanda.");
            return;
        }
        if (controller.marcarListo(idComandaSeleccionada)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Comanda lista para cuenta.");
            idComandaSeleccionada = 0;
            cargarPedidos();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo marcar como listo.");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnListo;
    private javax.swing.JScrollPane jScrollPaneDetalle;
    private javax.swing.JScrollPane jScrollPanePedidos;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblPedidos;
    // End of variables declaration//GEN-END:variables
}
