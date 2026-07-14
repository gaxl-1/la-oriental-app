package com.fortis.laoriental.vista;

import com.fortis.laoriental.controlador.PedidoController;
import com.fortis.laoriental.dao.ProductoDAO;
import com.fortis.laoriental.modelo.DetallePedido;
import com.fortis.laoriental.modelo.Mesa;
import com.fortis.laoriental.modelo.PedidoResumen;
import com.fortis.laoriental.modelo.Producto;
import com.fortis.laoriental.modelo.Usuario;
import com.fortis.laoriental.util.SesionUsuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PedidosFrame extends javax.swing.JFrame {

    private final transient PedidoController pedidoController = new PedidoController();
    private final transient ProductoDAO productoDAO = new ProductoDAO();
    private int idPedidoSeleccionado;
    private int idDetalleSeleccionado;
    private javax.swing.JTextField txtFiltroPedidos;
    private javax.swing.JButton btnEditarDetalle;
    private javax.swing.JButton btnEliminarDetalle;

    public PedidosFrame() {
        initComponents();
        configurarVista();
        cargarMesas();
        cargarPedidos();
        cargarProductos();
    }

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Pedido por Mesa");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));
        javax.swing.JPanel tablas = new javax.swing.JPanel(new java.awt.GridLayout(2, 2, 16, 16));
        tablas.setOpaque(false);
        tablas.add(panelTabla("Mesas disponibles", tblMesas));
        javax.swing.JPanel panelPedidos = panelTabla("Pedidos activos", tblPedidos);
        txtFiltroPedidos = new javax.swing.JTextField();
        txtFiltroPedidos.setToolTipText("Buscar pedido, mesa o estado");
        panelPedidos.add(txtFiltroPedidos, java.awt.BorderLayout.NORTH);
        EstiloSwing.filtroTexto(txtFiltroPedidos, tblPedidos);
        tablas.add(panelPedidos);
        tablas.add(panelTabla("Productos disponibles", tblProductos));
        tablas.add(panelTabla("Detalle del pedido", tblDetalle));
        javax.swing.JPanel acciones = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        acciones.setOpaque(false);
        btnEditarDetalle = new javax.swing.JButton("Editar producto");
        btnEliminarDetalle = new javax.swing.JButton("Quitar producto");
        btnEditarDetalle.addActionListener(e -> editarDetalle());
        btnEliminarDetalle.addActionListener(e -> eliminarDetalle());
        for (javax.swing.JButton b : new javax.swing.JButton[]{btnCrearPedido, btnAgregarProducto, btnEnviarCocina, btnRefrescar}) {
            EstiloSwing.botonPrimario(b);
            acciones.add(b);
        }
        EstiloSwing.botonSecundario(btnEditarDetalle);
        EstiloSwing.botonSecundario(btnEliminarDetalle);
        acciones.add(btnEditarDetalle);
        acciones.add(btnEliminarDetalle);
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
        jScrollPaneMesas = new javax.swing.JScrollPane();
        tblMesas = new javax.swing.JTable();
        jScrollPanePedidos = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        jScrollPaneProductos = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jScrollPaneDetalle = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        btnCrearPedido = new javax.swing.JButton();
        btnAgregarProducto = new javax.swing.JButton();
        btnEnviarCocina = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();

        setTitle("Pedidos por mesa");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblMesas.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"ID Mesa", "Numero", "Capacidad", "Estado", "Ubicacion"}));
        jScrollPaneMesas.setViewportView(tblMesas);

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"ID Pedido", "Mesa", "Mesero", "Cuenta", "Cocina", "Total"}));
        tblPedidos.getSelectionModel().addListSelectionListener(e -> seleccionarPedido());
        jScrollPanePedidos.setViewportView(tblPedidos);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"ID Producto", "Nombre", "Categoria", "Precio", "Stock"}));
        jScrollPaneProductos.setViewportView(tblProductos);

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {"ID", "Producto", "Cantidad", "P. Unitario", "Subtotal"}));
        tblDetalle.getSelectionModel().addListSelectionListener(e -> seleccionarDetalle());
        jScrollPaneDetalle.setViewportView(tblDetalle);

        btnCrearPedido.setText("Crear pedido");
        btnCrearPedido.addActionListener(e -> crearPedido());
        btnAgregarProducto.setText("Agregar producto");
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        btnEnviarCocina.setText("Enviar a cocina");
        btnEnviarCocina.addActionListener(e -> enviarACocina());
        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(e -> refrescar());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPaneMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPanePedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPaneProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCrearPedido).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarProducto).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviarCocina).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefrescar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPanePedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearPedido).addComponent(btnAgregarProducto).addComponent(btnEnviarCocina).addComponent(btnRefrescar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cargarMesas() {
        DefaultTableModel m = (DefaultTableModel) tblMesas.getModel();
        m.setRowCount(0);
        for (Mesa mesa : pedidoController.listarMesasDisponibles()) {
            m.addRow(new Object[]{mesa.getIdMesa(), mesa.getNumero(), mesa.getCapacidad(), mesa.getEstado(), mesa.getUbicacion()});
        }
    }

    private void cargarPedidos() {
        DefaultTableModel m = (DefaultTableModel) tblPedidos.getModel();
        m.setRowCount(0);
        for (PedidoResumen p : pedidoController.listarPedidosActivos()) {
            m.addRow(new Object[]{p.getIdPedido(), p.getNumeroMesa(), p.getMesero(), p.getEstado(), p.getEstadoCocina(), p.getTotal()});
        }
    }

    private void cargarProductos() {
        DefaultTableModel m = (DefaultTableModel) tblProductos.getModel();
        m.setRowCount(0);
        List<Producto> productos = productoDAO.listarActivos();
        for (Producto p : productos) {
            m.addRow(new Object[]{p.getIdProducto(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStock()});
        }
    }

    private void seleccionarPedido() {
        int fila = EstiloSwing.filaModelo(tblPedidos);
        if (fila < 0) return;
        javax.swing.table.TableModel modelo = tblPedidos.getModel();
        idPedidoSeleccionado = Integer.parseInt(String.valueOf(modelo.getValueAt(fila, 0)));
        String estado = String.valueOf(modelo.getValueAt(fila, 3));
        btnAgregarProducto.setEnabled("ABIERTO".equals(estado));
        btnEnviarCocina.setEnabled("ABIERTO".equals(estado));
        cargarDetalle();
    }

    private void cargarDetalle() {
        DefaultTableModel m = (DefaultTableModel) tblDetalle.getModel();
        m.setRowCount(0);
        for (DetallePedido d : pedidoController.listarDetallePedido(idPedidoSeleccionado)) {
            m.addRow(new Object[]{d.getIdDetallePedido(), d.getNombreProducto(), d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal()});
        }
        idDetalleSeleccionado = 0;
    }

    private void seleccionarDetalle() {
        int fila = EstiloSwing.filaModelo(tblDetalle);
        if (fila < 0) return;
        idDetalleSeleccionado = Integer.parseInt(String.valueOf(tblDetalle.getModel().getValueAt(fila, 0)));
    }

    private void crearPedido() {
        int fila = EstiloSwing.filaModelo(tblMesas);
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una mesa.");
            return;
        }
        Usuario usuario = SesionUsuario.getUsuarioActual();
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario en sesion.");
            return;
        }
        try {
            int idMesa = Integer.parseInt(String.valueOf(tblMesas.getModel().getValueAt(fila, 0)));
            int idPedido = pedidoController.crearPedidoPorMesa(idMesa, usuario.getIdUsuario());
            JOptionPane.showMessageDialog(this, "Pedido creado: " + idPedido);
            refrescar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void agregarProducto() {
        if (idPedidoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }
        if (!pedidoSeleccionadoAbierto()) {
            JOptionPane.showMessageDialog(this, "La cuenta no esta abierta.");
            return;
        }
        int fila = EstiloSwing.filaModelo(tblProductos);
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }
        String cantidad = JOptionPane.showInputDialog(this, "Cantidad:", "1");
        if (cantidad == null) return;
        int idProducto = Integer.parseInt(String.valueOf(tblProductos.getModel().getValueAt(fila, 0)));
        if (pedidoController.agregarProducto(idPedidoSeleccionado, idProducto, cantidad)) {
            refrescarPedidoActual();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo agregar el producto.");
        }
    }

    private void editarDetalle() {
        if (idDetalleSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del detalle.");
            return;
        }
        String cantidad = JOptionPane.showInputDialog(this, "Nueva cantidad:", "1");
        if (cantidad == null) return;
        if (pedidoController.editarDetallePendiente(idDetalleSeleccionado, cantidad)) {
            refrescarPedidoActual();
        } else {
            JOptionPane.showMessageDialog(this, "Solo puede editar productos que aun no se enviaron a cocina y con stock suficiente.");
        }
    }

    private void eliminarDetalle() {
        if (idDetalleSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del detalle.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(this, "Quitar este producto del pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;
        if (pedidoController.eliminarDetallePendiente(idDetalleSeleccionado)) {
            refrescarPedidoActual();
        } else {
            JOptionPane.showMessageDialog(this, "Solo puede quitar productos que aun no se enviaron a cocina.");
        }
    }

    private void enviarACocina() {
        if (idPedidoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido.");
            return;
        }
        if (pedidoController.enviarACocina(idPedidoSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Comanda enviada a cocina.");
            refrescar();
        } else {
            JOptionPane.showMessageDialog(this, "No hay productos pendientes por enviar a cocina.");
        }
    }

    private void refrescar() {
        idPedidoSeleccionado = 0;
        btnAgregarProducto.setEnabled(true);
        btnEnviarCocina.setEnabled(true);
        cargarMesas();
        cargarPedidos();
        cargarProductos();
        ((DefaultTableModel) tblDetalle.getModel()).setRowCount(0);
    }

    private void refrescarPedidoActual() {
        int idPedido = idPedidoSeleccionado;
        cargarMesas();
        cargarPedidos();
        cargarProductos();
        idPedidoSeleccionado = idPedido;
        cargarDetalle();
    }

    private boolean pedidoSeleccionadoAbierto() {
        int fila = EstiloSwing.filaModelo(tblPedidos);
        return fila >= 0 && "ABIERTO".equals(String.valueOf(tblPedidos.getModel().getValueAt(fila, 3)));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnCrearPedido;
    private javax.swing.JButton btnEnviarCocina;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JScrollPane jScrollPaneDetalle;
    private javax.swing.JScrollPane jScrollPaneMesas;
    private javax.swing.JScrollPane jScrollPanePedidos;
    private javax.swing.JScrollPane jScrollPaneProductos;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTable tblMesas;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables
}
