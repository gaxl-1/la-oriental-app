package com.fortis.laoriental.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 * Centraliza colores, tamanos y utilidades visuales de las pantallas Swing.
 * Mantener estos estilos en un solo lugar ayuda a que los modulos del POS
 * se vean consistentes y evita repetir configuracion en cada JFrame.
 */
final class EstiloSwing {

    static final Color FONDO = new Color(238, 238, 238);
    static final Color ENCABEZADO = new Color(42, 42, 42);
    static final Color SUBENCABEZADO = new Color(82, 82, 82);
    static final Color PANEL = Color.WHITE;
    static final Color BORDE = new Color(196, 196, 196);
    static final Color TEXTO = new Color(35, 35, 35);
    static final Color PRIMARIO = new Color(54, 54, 54);
    static final Color SECUNDARIO = new Color(218, 218, 218);

    private EstiloSwing() {}

    /**
     * Crea la estructura base usada por las vistas principales: barra superior,
     * fondo gris y tamano fijo similar al mockup del prototipo.
     */
    static JPanel raiz(JFrame frame, String titulo, String modulo) {
        frame.getContentPane().removeAll();
        frame.setSize(1200, 760);
        frame.setLocationRelativeTo(null);
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(FONDO);
        raiz.add(barra(titulo, modulo), BorderLayout.NORTH);
        frame.setContentPane(raiz);
        return raiz;
    }

    static JPanel barra(String titulo, String modulo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        JLabel principal = new JLabel("  " + titulo);
        principal.setOpaque(true);
        principal.setBackground(ENCABEZADO);
        principal.setForeground(Color.WHITE);
        principal.setFont(new Font("Arial", Font.BOLD, 14));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        JLabel sub = new JLabel("  " + modulo);
        sub.setOpaque(true);
        sub.setBackground(SUBENCABEZADO);
        sub.setForeground(Color.WHITE);
        sub.setFont(new Font("Arial", Font.BOLD, 12));
        sub.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        contenedor.add(principal, BorderLayout.NORTH);
        contenedor.add(sub, BorderLayout.SOUTH);
        return contenedor;
    }

    static JPanel panel(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        if (titulo != null && !titulo.isBlank()) {
            panel.setBorder(BorderFactory.createTitledBorder(panel.getBorder(), titulo));
        }
        return panel;
    }

    static void botonPrimario(JButton boton) {
        boton.setBackground(PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(7, 14, 7, 14));
        boton.setPreferredSize(new java.awt.Dimension(132, 32));
    }

    static void botonSecundario(JButton boton) {
        boton.setBackground(SECUNDARIO);
        boton.setForeground(TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(7, 14, 7, 14));
        boton.setPreferredSize(new java.awt.Dimension(132, 32));
    }

    static void tabla(JTable tabla) {
        tabla.setRowHeight(26);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setSelectionBackground(new Color(210, 210, 210));
        tabla.setSelectionForeground(TEXTO);
        tabla.setAutoCreateRowSorter(true);
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(221, 221, 221));
        header.setForeground(TEXTO);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(247, 247, 247));
                }
                return c;
            }
        });
    }

    static JScrollPane scroll(JTable tabla) {
        tabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(BORDE));
        return scroll;
    }

    static void filtroTexto(JTextField campo, JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);
        campo.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aplicar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aplicar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aplicar(); }
            private void aplicar() {
                String texto = campo.getText().trim();
                sorter.setRowFilter(texto.isEmpty() ? null : RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(texto)));
            }
        });
    }

    /** Convierte un booleano de BD en el indicador visual usado por las tablas. */
    static String activoTexto(boolean activo) {
        return activo ? "✓" : "";
    }

    /** Interpreta el valor visual de activo, tanto si viene como palomita o booleano. */
    static boolean textoActivo(Object valor) {
        return "✓".equals(String.valueOf(valor)) || Boolean.parseBoolean(String.valueOf(valor));
    }

    /** Devuelve la fila real del modelo aunque la tabla este filtrada u ordenada. */
    static int filaModelo(JTable tabla) {
        int fila = tabla.getSelectedRow();
        return fila < 0 ? -1 : tabla.convertRowIndexToModel(fila);
    }

    static JLabel logoLabel(int ancho, int alto) {
        try (java.io.InputStream in = EstiloSwing.class.getResourceAsStream("/img/LaOrientalLogo.png")) {
            if (in != null) {
                java.awt.image.BufferedImage original = recortarImagen(javax.imageio.ImageIO.read(in));
                int originalAncho = Math.max(original.getWidth(), 1);
                int originalAlto = Math.max(original.getHeight(), 1);
                double escala = Math.min((double) ancho / originalAncho, (double) alto / originalAlto);
                int nuevoAncho = Math.max(1, (int) Math.round(originalAncho * escala));
                int nuevoAlto = Math.max(1, (int) Math.round(originalAlto * escala));
                java.awt.Image img = original.getScaledInstance(nuevoAncho, nuevoAlto, java.awt.Image.SCALE_SMOOTH);
                JLabel label = new JLabel(new ImageIcon(img), JLabel.CENTER);
                label.setPreferredSize(new java.awt.Dimension(ancho, alto));
                return label;
            }
        } catch (Exception e) {
            // Si falla el recurso, se usa el distintivo simple de respaldo.
        }
        JLabel fallback = new JLabel("T", JLabel.CENTER);
        fallback.setFont(new Font("Arial", Font.BOLD, 26));
        fallback.setOpaque(true);
        fallback.setBackground(new Color(198, 198, 198));
        return fallback;
    }

    private static java.awt.image.BufferedImage recortarImagen(java.awt.image.BufferedImage imagen) throws java.io.IOException {
        if (imagen == null) throw new java.io.IOException("No se pudo leer el logo");
        int minX = imagen.getWidth();
        int minY = imagen.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < imagen.getHeight(); y++) {
            for (int x = 0; x < imagen.getWidth(); x++) {
                int rgba = imagen.getRGB(x, y);
                int alpha = (rgba >>> 24) & 0xff;
                int r = (rgba >>> 16) & 0xff;
                int g = (rgba >>> 8) & 0xff;
                int b = rgba & 0xff;
                boolean fondoBlanco = r > 245 && g > 245 && b > 245;
                if (alpha > 20 && !fondoBlanco) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        if (maxX < minX || maxY < minY) return imagen;
        int margen = 6;
        minX = Math.max(0, minX - margen);
        minY = Math.max(0, minY - margen);
        maxX = Math.min(imagen.getWidth() - 1, maxX + margen);
        maxY = Math.min(imagen.getHeight() - 1, maxY + margen);
        return imagen.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1);
    }

    static void volverAlMenu(JFrame actual) {
        com.fortis.laoriental.modelo.Usuario usuario = com.fortis.laoriental.util.SesionUsuario.getUsuarioActual();
        if (usuario != null) {
            new MenuPrincipalFrame(usuario).setVisible(true);
        } else {
            new LoginFrame().setVisible(true);
        }
        actual.dispose();
    }
}
