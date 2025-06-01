import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UsuarioPanel extends JPanel {
    private final Usuario usuario;
    private final CatalogoProductos catalogo;
    private final GestorDirecciones gestorDirecciones;
    private final GestorCalorias gestorCalorias;
    private final GestorPedidos gestorPedidos;
    private final SistemaRecompensas sistemaRecompensas;
    private final Recomendador recomendador;

    public UsuarioPanel(Usuario usuario, CatalogoProductos catalogo) {
        this.usuario = usuario;
        this.catalogo = catalogo;
        this.gestorDirecciones = GestorDirecciones.getInstance();
        this.gestorCalorias = GestorCalorias.getInstance();
        this.gestorPedidos = GestorPedidos.getInstance();
        this.sistemaRecompensas = new SistemaRecompensas(catalogo);
        this.recomendador = new Recomendador(catalogo);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel superior con bienvenida
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Bienvenido, " + usuario.getNombre()));
        add(topPanel, BorderLayout.NORTH);

        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de Productos y Recomendaciones
        tabbedPane.addTab("Productos", crearPanelProductos());

        // Pestaña de Direcciones
        tabbedPane.addTab("Direcciones", crearPanelDirecciones());

        // Pestaña de Pedidos
        tabbedPane.addTab("Pedidos", crearPanelPedidos());

        // Pestaña de Gestión Calórica
        tabbedPane.addTab("Calorías", crearPanelCalorias());

        // Pestaña de Recompensas
        tabbedPane.addTab("Recompensas", crearPanelRecompensas());

        add(tabbedPane, BorderLayout.CENTER);

        // Botón de cerrar sesión
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(e -> ((Window) SwingUtilities.getWindowAncestor(this)).dispose());
        add(btnLogout, BorderLayout.SOUTH);
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Lista de productos por categoría
        JComboBox<String> comboCat = new JComboBox<>(
                new String[]{"energia", "concentracion", "relajacion", "hidratacion"});

        JButton btnFiltrar = new JButton("Filtrar");
        JPanel filtroPanel = new JPanel();
        filtroPanel.add(new JLabel("Categoría:"));
        filtroPanel.add(comboCat);
        filtroPanel.add(btnFiltrar);

        DefaultListModel<Producto> listModel = new DefaultListModel<>();
        JList<Producto> productList = new JList<>(listModel);

        btnFiltrar.addActionListener(e -> {
            listModel.clear();
            recomendador.recomendarPorCategoria((String) comboCat.getSelectedItem())
                    .forEach(listModel::addElement);
        });

        // Panel para hacer pedidos
        JButton btnAgregarPedido = new JButton("Agregar a Pedido");
        btnAgregarPedido.addActionListener(e -> {
            Producto seleccionado = productList.getSelectedValue();
            if (seleccionado != null) {
                // Lógica para agregar a pedido
                Pedido pedido = new Pedido();
                pedido.agregarProducto(seleccionado.getNombre());
                gestorPedidos.agregarPedido(pedido);
                sistemaRecompensas.agregarPuntos(usuario.getId(), pedido.getPuntos());
                JOptionPane.showMessageDialog(this, "Producto agregado al pedido!");
            }
        });

        panel.add(filtroPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(productList), BorderLayout.CENTER);
        panel.add(btnAgregarPedido, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelDirecciones() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<Direccion> listModel = new DefaultListModel<>();
        JList<Direccion> direccionesList = new JList<>(listModel);
        gestorDirecciones.getDirecciones().forEach(listModel::addElement);

        // Panel de controles
        JPanel controlPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        JTextField txtCalle = new JTextField();
        JTextField txtCiudad = new JTextField();
        JTextField txtCodigoPostal = new JTextField();

        controlPanel.add(new JLabel("Calle:"));
        controlPanel.add(txtCalle);
        controlPanel.add(new JLabel("Ciudad:"));
        controlPanel.add(txtCiudad);
        controlPanel.add(new JLabel("Código Postal:"));
        controlPanel.add(txtCodigoPostal);

        JButton btnAgregar = new JButton("Agregar Dirección");
        btnAgregar.addActionListener(e -> {
            Direccion nueva = new Direccion(
                    txtCalle.getText(),
                    txtCiudad.getText(),
                    txtCodigoPostal.getText()
            );
            gestorDirecciones.agregarDireccion(nueva);
            listModel.addElement(nueva);
        });

        JButton btnModificar = new JButton("Modificar Dirección");
        btnModificar.addActionListener(e -> {
            int selectedIndex = direccionesList.getSelectedIndex();
            if (selectedIndex >= 0) {
                Direccion modificada = new Direccion(
                        txtCalle.getText(),
                        txtCiudad.getText(),
                        txtCodigoPostal.getText()
                );
                gestorDirecciones.modificarDireccion(selectedIndex, modificada);
                listModel.set(selectedIndex, modificada);
            }
        });

        panel.add(new JScrollPane(direccionesList), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(controlPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnModificar);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<Pedido> listModel = new DefaultListModel<>();
        JList<Pedido> pedidosList = new JList<>(listModel);

        // Botón para actualizar lista de pedidos
        JButton btnActualizar = new JButton("Actualizar Pedidos");
        btnActualizar.addActionListener(e -> {
            listModel.clear();
            while (gestorPedidos.hayPedidos()) {
                listModel.addElement(gestorPedidos.procesarPedido());
            }
        });

        panel.add(new JScrollPane(pedidosList), BorderLayout.CENTER);
        panel.add(btnActualizar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCalorias() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblEstado = new JLabel();
        JButton btnActualizar = new JButton("Actualizar Estado");
        btnActualizar.addActionListener(e -> {
            lblEstado.setText(gestorCalorias.estadoMeta(usuario.getId()));
        });

        JPanel metaPanel = new JPanel();
        JTextField txtMeta = new JTextField(10);
        JButton btnEstablecerMeta = new JButton("Establecer Meta");
        btnEstablecerMeta.addActionListener(e -> {
            try {
                int meta = Integer.parseInt(txtMeta.getText());
                gestorCalorias.asignarMeta(usuario.getId(), meta);
                JOptionPane.showMessageDialog(this, "Meta calórica establecida!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        metaPanel.add(new JLabel("Meta diaria:"));
        metaPanel.add(txtMeta);
        metaPanel.add(btnEstablecerMeta);

        JPanel registroPanel = new JPanel();
        JTextField txtCalorias = new JTextField(10);
        JButton btnRegistrar = new JButton("Registrar Consumo");
        btnRegistrar.addActionListener(e -> {
            try {
                int calorias = Integer.parseInt(txtCalorias.getText());
                gestorCalorias.consumirAlimento(usuario.getId(), calorias);
                JOptionPane.showMessageDialog(this, "Consumo registrado!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registroPanel.add(new JLabel("Calorías consumidas:"));
        registroPanel.add(txtCalorias);
        registroPanel.add(btnRegistrar);

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        northPanel.add(metaPanel);
        northPanel.add(registroPanel);

        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(btnActualizar, BorderLayout.CENTER);
        panel.add(lblEstado, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelRecompensas() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lblPuntos = new JLabel("Puntos disponibles: " +
                sistemaRecompensas.consultarPuntos(usuario.getId()));

        DefaultListModel<Producto> listModel = new DefaultListModel<>();
        JList<Producto> productosList = new JList<>(listModel);
        catalogo.getTodosProductos().forEach(listModel::addElement);

        JButton btnCanjear = new JButton("Canjear Producto");
        btnCanjear.addActionListener(e -> {
            Producto seleccionado = productosList.getSelectedValue();
            if (seleccionado != null) {
                if (sistemaRecompensas.canjearProducto(usuario.getId(), seleccionado.getNombre())) {
                    JOptionPane.showMessageDialog(this, "Producto canjeado con éxito!");
                    lblPuntos.setText("Puntos disponibles: " +
                            sistemaRecompensas.consultarPuntos(usuario.getId()));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No tienes suficientes puntos o no hay stock",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(lblPuntos, BorderLayout.NORTH);
        panel.add(new JScrollPane(productosList), BorderLayout.CENTER);
        panel.add(btnCanjear, BorderLayout.SOUTH);

        return panel;
    }
}
