import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

public class PanelAdmin extends JPanel {
    private final GestorUsuarios gestorUsuarios;
    private final CatalogoProductos catalogo;

    public PanelAdmin(GestorUsuarios gestorUsuarios, CatalogoProductos catalogo) {
        this.gestorUsuarios = gestorUsuarios;
        this.catalogo = catalogo;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de Usuarios
        tabbedPane.addTab("Usuarios", crearPanelUsuarios());

        // Pestaña de Productos
        tabbedPane.addTab("Productos", crearPanelProductos());

        // Pestaña de Pedidos
        tabbedPane.addTab("Pedidos", crearPanelPedidos());

        // Pestaña de Estadísticas
        tabbedPane.addTab("Estadísticas", crearPanelEstadisticas());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());

        // Modelo de tabla para usuarios
        DefaultTableModel modeloUsuarios = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Correo", "Admin"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 3 ? Boolean.class : String.class;
            }
        };

        // Llenar tabla con usuarios existentes
        for (Usuario u : gestorUsuarios.getTodosUsuarios().values()) {
            modeloUsuarios.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.isAdmin()
            });
        }

        JTable tablaUsuarios = new JTable(modeloUsuarios);
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);

        // Panel de controles
        JPanel controlesPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Campos para nuevo usuario
        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();
        JCheckBox chkAdmin = new JCheckBox("Es administrador");

        controlesPanel.add(new JLabel("ID:"));
        controlesPanel.add(txtId);
        controlesPanel.add(new JLabel("Nombre:"));
        controlesPanel.add(txtNombre);
        controlesPanel.add(new JLabel("Correo:"));
        controlesPanel.add(txtCorreo);
        controlesPanel.add(new JLabel("Contraseña (12 chars):"));
        controlesPanel.add(txtContrasena);
        controlesPanel.add(new JLabel("Administrador:"));
        controlesPanel.add(chkAdmin);

        // Botones
        JButton btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.addActionListener(e -> {
            String contrasena = new String(txtContrasena.getPassword());
            if (contrasena.length() != 12) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener 12 caracteres",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Usuario nuevo = new Usuario(
                        txtId.getText(),
                        txtNombre.getText(),
                        txtCorreo.getText(),
                        contrasena,
                        chkAdmin.isSelected()
                );
                gestorUsuarios.registrarUsuario(nuevo);
                modeloUsuarios.addRow(new Object[]{
                        nuevo.getId(),
                        nuevo.getNombre(),
                        nuevo.getCorreo(),
                        nuevo.isAdmin()
                });
                limpiarCampos(txtId, txtNombre, txtCorreo, txtContrasena, chkAdmin);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al agregar usuario: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> {
            for (int i = 0; i < modeloUsuarios.getRowCount(); i++) {
                String id = (String) modeloUsuarios.getValueAt(i, 0);
                String nuevoNombre = (String) modeloUsuarios.getValueAt(i, 1);
                boolean esAdmin = (Boolean) modeloUsuarios.getValueAt(i, 3);

                Usuario usuario = gestorUsuarios.getTodosUsuarios().values().stream()
                        .filter(u -> u.getId().equals(id))
                        .findFirst()
                        .orElse(null);

                if (usuario != null) {
                    usuario.setNombre(nuevoNombre);
                    usuario.setAdmin(esAdmin);
                }
            }
            JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente");
        });

        JButton btnEliminar = new JButton("Eliminar Usuario");
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaUsuarios.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String id = (String) modeloUsuarios.getValueAt(filaSeleccionada, 0);
                gestorUsuarios.getTodosUsuarios().remove(id);
                modeloUsuarios.removeRow(filaSeleccionada);
            }
        });

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnGuardar);
        botonesPanel.add(btnEliminar);

        panel.add(scrollUsuarios, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(controlesPanel, BorderLayout.NORTH);
        southPanel.add(botonesPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Modelo de tabla para productos
        DefaultTableModel modeloProductos = new DefaultTableModel(
                new Object[]{"Nombre", "Categoría", "Precio", "Stock"}, 0);

        // Llenar tabla con productos existentes
        for (Producto p : catalogo.getTodosProductos()) {
            modeloProductos.addRow(new Object[]{
                    p.getNombre(),
                    p.getCategoria(),
                    p.getPrecio(),
                    p.getStock()
            });
        }

        JTable tablaProductos = new JTable(modeloProductos);
        JScrollPane scrollProductos = new JScrollPane(tablaProductos);

        // Panel para agregar productos
        JPanel agregarPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        agregarPanel.add(new JLabel("Nombre:"));
        JTextField txtNombre = new JTextField();
        agregarPanel.add(txtNombre);

        agregarPanel.add(new JLabel("Categoría:"));
        JComboBox<String> comboCat = new JComboBox<>(
                new String[]{"energia", "concentracion", "relajacion", "hidratacion"});
        agregarPanel.add(comboCat);

        agregarPanel.add(new JLabel("Precio:"));
        JSpinner spinnerPrecio = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 1000.0, 0.5));
        agregarPanel.add(spinnerPrecio);

        agregarPanel.add(new JLabel("Stock:"));
        JSpinner spinnerStock = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
        agregarPanel.add(spinnerStock);

        // Botones
        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.addActionListener(e -> {
            Producto p = new Producto(
                    txtNombre.getText(),
                    (String) comboCat.getSelectedItem(),
                    (Double) spinnerPrecio.getValue(),
                    (Integer) spinnerStock.getValue()
            );
            catalogo.agregarProducto(p);
            modeloProductos.addRow(new Object[]{
                    p.getNombre(),
                    p.getCategoria(),
                    p.getPrecio(),
                    p.getStock()
            });
            limpiarCampos(txtNombre, spinnerPrecio, spinnerStock);
        });

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String nombre = (String) modeloProductos.getValueAt(filaSeleccionada, 0);
                catalogo.eliminarProducto(nombre);
                modeloProductos.removeRow(filaSeleccionada);
            }
        });

        JButton btnActualizar = new JButton("Actualizar Stock");
        btnActualizar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String nombre = (String) modeloProductos.getValueAt(filaSeleccionada, 0);
                int nuevoStock = (Integer) spinnerStock.getValue();

                catalogo.getTodosProductos().stream()
                        .filter(p -> p.getNombre().equals(nombre))
                        .findFirst()
                        .ifPresent(p -> {
                            p.setStock(nuevoStock);
                            modeloProductos.setValueAt(nuevoStock, filaSeleccionada, 3);
                        });
            }
        });

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnEliminar);
        botonesPanel.add(btnActualizar);

        panel.add(scrollProductos, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(agregarPanel, BorderLayout.NORTH);
        southPanel.add(botonesPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Modelo de tabla para pedidos
        DefaultTableModel modeloPedidos = new DefaultTableModel(
                new Object[]{"ID", "Usuario", "Productos", "Total", "Estado"}, 0);

        // Aquí deberías llenar la tabla con los pedidos del sistema
        // Esto es un ejemplo, necesitarías integrar con tu GestorPedidos

        JTable tablaPedidos = new JTable(modeloPedidos);
        JScrollPane scrollPedidos = new JScrollPane(tablaPedidos);

        // Botones
        JButton btnProcesar = new JButton("Procesar Pedido");
        btnProcesar.addActionListener(e -> {
            int filaSeleccionada = tablaPedidos.getSelectedRow();
            if (filaSeleccionada >= 0) {
                modeloPedidos.setValueAt("Completado", filaSeleccionada, 4);
                // Aquí iría la lógica para procesar el pedido
            }
        });

        JButton btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.addActionListener(e -> {
            // Actualizar la lista de pedidos
        });

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnProcesar);
        botonesPanel.add(btnActualizar);

        panel.add(scrollPedidos, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Estadísticas de usuarios
        JPanel usuariosPanel = new JPanel(new BorderLayout());
        usuariosPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas de Usuarios"));
        JLabel lblUsuarios = new JLabel("Total usuarios: " + gestorUsuarios.getTodosUsuarios().size());
        lblUsuarios.setHorizontalAlignment(SwingConstants.CENTER);
        usuariosPanel.add(lblUsuarios, BorderLayout.CENTER);

        // Estadísticas de productos
        JPanel productosPanel = new JPanel(new BorderLayout());
        productosPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas de Productos"));
        JLabel lblProductos = new JLabel("Total productos: " + catalogo.getTodosProductos().size());
        lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
        productosPanel.add(lblProductos, BorderLayout.CENTER);

        // Estadísticas de pedidos
        JPanel pedidosPanel = new JPanel(new BorderLayout());
        pedidosPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas de Pedidos"));
        JLabel lblPedidos = new JLabel("Pedidos pendientes: 0"); // Deberías obtener este dato real
        lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);
        pedidosPanel.add(lblPedidos, BorderLayout.CENTER);

        // Otras estadísticas
        JPanel otrasPanel = new JPanel(new BorderLayout());
        otrasPanel.setBorder(BorderFactory.createTitledBorder("Otras Estadísticas"));
        JLabel lblOtras = new JLabel("Más vendido: Producto X"); // Datos de ejemplo
        lblOtras.setHorizontalAlignment(SwingConstants.CENTER);
        otrasPanel.add(lblOtras, BorderLayout.CENTER);

        panel.add(usuariosPanel);
        panel.add(productosPanel);
        panel.add(pedidosPanel);
        panel.add(otrasPanel);

        return panel;
    }

    private void limpiarCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    private void limpiarCampos(JTextField txtNombre, JSpinner spinnerPrecio, JSpinner spinnerStock) {
        txtNombre.setText("");
        spinnerPrecio.setValue(1.0);
        spinnerStock.setValue(1);
    }

    private void limpiarCampos(JTextField txtId, JTextField txtNombre, JTextField txtCorreo,
                               JPasswordField txtContrasena, JCheckBox chkAdmin) {
        txtId.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContrasena.setText("");
        chkAdmin.setSelected(false);
    }
}