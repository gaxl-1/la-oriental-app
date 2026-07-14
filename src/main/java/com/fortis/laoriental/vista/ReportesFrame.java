package com.fortis.laoriental.vista;

import com.fortis.laoriental.dao.VentaDAO;
import com.fortis.laoriental.modelo.VentaResumen;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla de reportes diarios de ventas.
 * Permite filtrar por fecha y cajero, muestra indicadores simples y ofrece
 * impresion/exportacion mediante el dialogo de impresion del sistema.
 */
public class ReportesFrame extends javax.swing.JFrame {

    private final transient VentaDAO ventaDAO = new VentaDAO();
    private javax.swing.JTextField txtFecha;
    private javax.swing.JComboBox<String> cmbCajero;
    private javax.swing.JLabel lblPedidos;
    private javax.swing.JLabel lblTicket;
    private javax.swing.JButton btnPdf;

    public ReportesFrame() {
        initComponents();
        configurarVista();
        cargarCajeros();
        cargarVentasDia();
    }

    private void configurarVista() {
        javax.swing.JPanel raiz = EstiloSwing.raiz(this, "Taqueria La Oriental - Sistema POS", "Reporte Diario de Ventas");
        javax.swing.JPanel contenido = new javax.swing.JPanel(new java.awt.BorderLayout(16, 16));
        contenido.setBackground(EstiloSwing.FONDO);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 26, 18, 26));

        javax.swing.JPanel filtros = EstiloSwing.panel("Filtros");
        filtros.setLayout(new java.awt.GridLayout(0, 1, 8, 8));
        txtFecha = new javax.swing.JTextField(LocalDate.now().toString());
        cmbCajero = new javax.swing.JComboBox<>(new String[] { "Todos" });
        lblPedidos = new javax.swing.JLabel("Pedidos cerrados: 0");
        lblTicket = new javax.swing.JLabel("Ticket promedio: $0.00");
        btnPdf = new javax.swing.JButton("Generar reporte PDF");
        btnPdf.addActionListener(e -> generarPdf());
        EstiloSwing.botonPrimario(btnRefrescar);
        EstiloSwing.botonPrimario(btnPdf);
        filtros.add(new javax.swing.JLabel("Fecha (yyyy-MM-dd)"));
        filtros.add(txtFecha);
        filtros.add(new javax.swing.JLabel("Cajero"));
        filtros.add(cmbCajero);
        filtros.add(btnRefrescar);
        filtros.add(lblTotal);
        filtros.add(lblPedidos);
        filtros.add(lblTicket);

        javax.swing.JPanel lista = EstiloSwing.panel("Ventas cerradas");
        lista.setLayout(new java.awt.BorderLayout(8, 8));
        lista.add(EstiloSwing.scroll(tblVentas), java.awt.BorderLayout.CENTER);
        javax.swing.JPanel sur = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnPdf);
        javax.swing.JButton btnVolver = new javax.swing.JButton("Volver al menu");
        EstiloSwing.botonSecundario(btnVolver);
        btnVolver.addActionListener(e -> EstiloSwing.volverAlMenu(this));
        sur.add(btnVolver);
        lista.add(sur, java.awt.BorderLayout.SOUTH);

        contenido.add(filtros, java.awt.BorderLayout.WEST);
        contenido.add(lista, java.awt.BorderLayout.CENTER);
        raiz.add(contenido, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVentas = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnRefrescar = new javax.swing.JButton();

        setTitle("Reporte diario de ventas");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] { "ID Venta", "Pedido", "Cajero", "Fecha", "Total", "Observaciones" }
        ));
        jScrollPane1.setViewportView(tblVentas);

        lblTotal.setText("Total del dia: $0.00");

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(e -> cargarVentasDia());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblTotal)
                            .addGap(18, 18, 18)
                            .addComponent(btnRefrescar)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotal)
                        .addComponent(btnRefrescar))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cargarVentasDia() {
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(txtFecha == null ? LocalDate.now().toString() : txtFecha.getText().trim());
        } catch (DateTimeParseException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "La fecha debe tener formato yyyy-MM-dd.");
            return;
        }
        String cajero = cmbCajero == null ? "Todos" : String.valueOf(cmbCajero.getSelectedItem());
        DefaultTableModel model = (DefaultTableModel) tblVentas.getModel();
        model.setRowCount(0);
        double totalDia = 0.0;
        int pedidos = 0;
        for (VentaResumen venta : ventaDAO.listarVentas(fecha, cajero)) {
            totalDia += venta.getTotal();
            pedidos++;
            model.addRow(new Object[]{
                venta.getIdVenta(),
                venta.getIdPedido(),
                venta.getCajero(),
                venta.getFechaHora(),
                venta.getTotal(),
                venta.getObservaciones()
            });
        }
        lblTotal.setText(String.format("Total del dia: $%.2f", totalDia));
        if (lblPedidos != null) lblPedidos.setText("Pedidos cerrados: " + pedidos);
        if (lblTicket != null) lblTicket.setText(String.format("Ticket promedio: $%.2f", pedidos == 0 ? 0.0 : totalDia / pedidos));
    }

    private void cargarCajeros() {
        if (cmbCajero == null) return;
        for (String cajero : ventaDAO.listarCajerosConVenta()) {
            cmbCajero.addItem(cajero);
        }
    }

    private void generarPdf() {
        try {
            tblVentas.print(javax.swing.JTable.PrintMode.FIT_WIDTH,
                new java.text.MessageFormat("Reporte diario de ventas"),
                new java.text.MessageFormat(lblTotal.getText()));
        } catch (java.awt.print.PrinterException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "No se pudo generar/imprimir el reporte: " + e.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblVentas;
    // End of variables declaration//GEN-END:variables
}
