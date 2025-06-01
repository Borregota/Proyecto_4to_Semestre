import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class MainGUI extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private Usuario usuarioActual;

    private final GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
    private final GestorCalorias gestorCalorias = GestorCalorias.getInstance();
    private final GestorDirecciones gestorDirecciones = GestorDirecciones.getInstance();
    private final GestorPedidos gestorPedidos = GestorPedidos.getInstance();
    private final CatalogoProductos catalogo = CatalogoProductos.getInstance();
    private final Recomendador recomendador = new Recomendador(catalogo);

    public MainGUI() {
        setTitle("Plus Ultra - Sistema de Gestión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        crearPanelLogin();
        crearPanelRegistro();
        crearPanelPrincipal();
        crearPanelRecomendacion();
        crearPanelCalorias();
        crearPanelDirecciones();
        crearPanelPedidos();

        cardLayout.show(cardPanel, "login");

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(menuItemSalir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        add(cardPanel);
    }

    private void crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Correo:"), gbc);

        gbc.gridx = 1;
        JTextField txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        JPasswordField txtPass = new JPasswordField(20);
        panel.add(txtPass, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String pass = new String(txtPass.getPassword());

            if (gestorUsuarios.autenticarUsuario(correo, pass)) {
                usuarioActual = gestorUsuarios.buscarPorCorreo(correo);
                cardLayout.show(cardPanel, "principal");
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnLogin, gbc);

        gbc.gridy++;
        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.addActionListener(e -> cardLayout.show(cardPanel, "registro"));
        panel.add(btnRegistro, gbc);

        cardPanel.add(panel, "login");
    }

    private void crearPanelRegistro() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Correo:"), gbc);

        gbc.gridx = 1;
        JTextField txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        JTextField txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        JPasswordField txtPass = new JPasswordField(20);
        panel.add(txtPass, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Confirmar Contraseña:"), gbc);

        gbc.gridx = 1;
        JPasswordField txtPassConfirm = new JPasswordField(20);
        panel.add(txtPassConfirm, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            String correo = txtCorreo.getText();
            String nombre = txtNombre.getText();
            String pass = new String(txtPass.getPassword());
            String passConfirm = new String(txtPassConfirm.getPassword());

            if (!pass.equals(passConfirm)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (correo.isEmpty() || nombre.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario u = new Usuario(correo.hashCode() + "", nombre, correo, pass);
            gestorUsuarios.registrarUsuario(u);
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "login");
        });
        panel.add(btnRegistrar, gbc);

        gbc.gridy++;
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cardLayout.show(cardPanel, "login"));
        panel.add(btnCancelar, gbc);

        cardPanel.add(panel, "registro");
    }

    private void crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior con bienvenida
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblBienvenida = new JLabel();
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblBienvenida);

        // Actualizar el mensaje de bienvenida cuando se muestra el panel
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (usuarioActual != null) {
                    lblBienvenida.setText("Bienvenido, " + usuarioActual.getNombre());
                }
            }
        });

        // Panel central con opciones
        JPanel panelOpciones = new JPanel(new GridLayout(4, 2, 10, 10));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnRecomendacion = new JButton("Recomendación de Productos");
        btnRecomendacion.addActionListener(e -> cardLayout.show(cardPanel, "recomendacion"));

        JButton btnCalorias = new JButton("Gestión de Calorías");
        btnCalorias.addActionListener(e -> cardLayout.show(cardPanel, "calorias"));

        JButton btnDirecciones = new JButton("Direcciones");
        btnDirecciones.addActionListener(e -> cardLayout.show(cardPanel, "direcciones"));

        JButton btnPedidos = new JButton("Pedidos");
        btnPedidos.addActionListener(e -> cardLayout.show(cardPanel, "pedidos"));

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            usuarioActual = null;
            cardLayout.show(cardPanel, "login");
        });

        panelOpciones.add(btnRecomendacion);
        panelOpciones.add(btnCalorias);
        panelOpciones.add(btnDirecciones);
        panelOpciones.add(btnPedidos);
        panelOpciones.add(btnCerrarSesion);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelOpciones, BorderLayout.CENTER);

        cardPanel.add(panel, "principal");
    }

    private void crearPanelRecomendacion() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior con título y botón de volver
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(cardPanel, "principal"));
        panelSuperior.add(btnVolver);

        JLabel lblTitulo = new JLabel("Recomendación de Productos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblTitulo);
        panel.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con formulario y resultados
        JPanel panelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentral.add(new JLabel("Seleccione una categoría:"), gbc);

        gbc.gridx = 1;
        String[] categorias = {"energia", "concentracion", "relajacion", "hidratacion"};
        JComboBox<String> comboCategorias = new JComboBox<>(categorias);
        panelCentral.add(comboCategorias, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnBuscar = new JButton("Buscar Recomendaciones");
        btnBuscar.addActionListener(e -> {
            String categoria = (String)comboCategorias.getSelectedItem();
            List<Producto> productos = recomendador.recomendarPorCategoria(categoria);

            if (productos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay productos disponibles para esa categoría", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Productos recomendados para ").append(categoria).append(":\n\n");
                for (Producto p : productos) {
                    sb.append(" - ").append(p).append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString(), "Recomendaciones", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panelCentral.add(btnBuscar, gbc);

        panel.add(panelCentral, BorderLayout.CENTER);
        cardPanel.add(panel, "recomendacion");
    }

    private void crearPanelCalorias() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior con título y botón de volver
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(cardPanel, "principal"));
        panelSuperior.add(btnVolver);

        JLabel lblTitulo = new JLabel("Gestión de Calorías");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblTitulo);
        panel.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña para asignar meta
        JPanel panelMeta = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelMeta.add(new JLabel("Meta calórica diaria:"), gbc);

        gbc.gridx = 1;
        JSpinner spinnerMeta = new JSpinner(new SpinnerNumberModel(2000, 500, 5000, 50));
        panelMeta.add(spinnerMeta, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnAsignarMeta = new JButton("Asignar Meta");
        btnAsignarMeta.addActionListener(e -> {
            int meta = (int)spinnerMeta.getValue();
            gestorCalorias.asignarMeta(usuarioActual.getCorreo(), meta);
            JOptionPane.showMessageDialog(this, "Meta calórica asignada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        panelMeta.add(btnAsignarMeta, gbc);

        tabbedPane.addTab("Asignar Meta", panelMeta);

        // Pestaña para registrar consumo
        JPanel panelConsumo = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelConsumo.add(new JLabel("Calorías consumidas:"), gbc);

        gbc.gridx = 1;
        JSpinner spinnerCalorias = new JSpinner(new SpinnerNumberModel(100, 1, 2000, 10));
        panelConsumo.add(spinnerCalorias, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar Consumo");
        btnRegistrar.addActionListener(e -> {
            int calorias = (int)spinnerCalorias.getValue();
            gestorCalorias.consumirAlimento(usuarioActual.getCorreo(), calorias);
            JOptionPane.showMessageDialog(this, "Consumo registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });
        panelConsumo.add(btnRegistrar, gbc);

        tabbedPane.addTab("Registrar Consumo", panelConsumo);

        // Pestaña para ver estado
        JPanel panelEstado = new JPanel(new BorderLayout());
        JTextArea txtEstado = new JTextArea();
        txtEstado.setEditable(false);
        txtEstado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtEstado);

        JButton btnConsultar = new JButton("Consultar Estado");
        btnConsultar.addActionListener(e -> {
            String estado = gestorCalorias.estadoMeta(usuarioActual.getCorreo());
            txtEstado.setText(estado);
        });

        panelEstado.add(scrollPane, BorderLayout.CENTER);
        panelEstado.add(btnConsultar, BorderLayout.SOUTH);
        tabbedPane.addTab("Estado Actual", panelEstado);

        panel.add(tabbedPane, BorderLayout.CENTER);
        cardPanel.add(panel, "calorias");
    }

    private void crearPanelDirecciones() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior con título y botón de volver
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(cardPanel, "principal"));
        panelSuperior.add(btnVolver);

        JLabel lblTitulo = new JLabel("Gestión de Direcciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblTitulo);
        panel.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con formulario y lista
        JPanel panelCentral = new JPanel(new BorderLayout());

        // Formulario para agregar dirección
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Calle:"), gbc);

        gbc.gridx = 1;
        JTextField txtCalle = new JTextField(20);
        panelFormulario.add(txtCalle, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(new JLabel("Ciudad:"), gbc);

        gbc.gridx = 1;
        JTextField txtCiudad = new JTextField(20);
        panelFormulario.add(txtCiudad, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelFormulario.add(new JLabel("Código Postal:"), gbc);

        gbc.gridx = 1;
        JTextField txtCP = new JTextField(10);
        panelFormulario.add(txtCP, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnAgregar = new JButton("Agregar Dirección");
        btnAgregar.addActionListener(e -> {
            String calle = txtCalle.getText();
            String ciudad = txtCiudad.getText();
            String cp = txtCP.getText();

            if (calle.isEmpty() || ciudad.isEmpty() || cp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Direccion d = new Direccion(calle, ciudad, cp);
            gestorDirecciones.agregarDireccion(d);
            JOptionPane.showMessageDialog(this, "Dirección agregada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar campos
            txtCalle.setText("");
            txtCiudad.setText("");
            txtCP.setText("");

            // Actualizar lista
            actualizarListaDirecciones();
        });
        panelFormulario.add(btnAgregar, gbc);

        // Lista de direcciones
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaDirecciones = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaDirecciones);

        // Método para actualizar la lista
        Runnable actualizarLista = () -> {
            modeloLista.clear();
            List<Direccion> direcciones = gestorDirecciones.getDirecciones();
            for (Direccion d : direcciones) {
                modeloLista.addElement(d.toString());
            }
        };

        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        panel.add(panelCentral, BorderLayout.CENTER);
        cardPanel.add(panel, "direcciones");

        // Actualizar lista al mostrar el panel
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                actualizarLista.run();
            }
        });
    }

    private void actualizarListaDirecciones() {
        // Implementación similar a la del Runnable en crearPanelDirecciones
    }

    private void crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());

        // Panel superior con título y botón de volver
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> cardLayout.show(cardPanel, "principal"));
        panelSuperior.add(btnVolver);

        JLabel lblTitulo = new JLabel("Gestión de Pedidos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelSuperior.add(lblTitulo);
        panel.add(panelSuperior, BorderLayout.NORTH);

        // Panel central con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña para agregar pedido
        JPanel panelAgregar = new JPanel(new BorderLayout());

        // Panel para productos
        DefaultListModel<String> modeloProductos = new DefaultListModel<>();
        JList<String> listaProductos = new JList<>(modeloProductos);
        JScrollPane scrollProductos = new JScrollPane(listaProductos);

        // Panel para agregar producto
        JPanel panelAgregarProducto = new JPanel();
        JTextField txtProducto = new JTextField(20);
        JButton btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.addActionListener(e -> {
            String producto = txtProducto.getText();
            if (!producto.isEmpty()) {
                modeloProductos.addElement(producto);
                txtProducto.setText("");
            }
        });
        panelAgregarProducto.add(txtProducto);
        panelAgregarProducto.add(btnAgregarProducto);

        // Botón para crear pedido
        JButton btnCrearPedido = new JButton("Crear Pedido");
        btnCrearPedido.addActionListener(e -> {
            if (modeloProductos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pedido pedido = new Pedido();
            for (int i = 0; i < modeloProductos.size(); i++) {
                pedido.agregarProducto(modeloProductos.getElementAt(i));
            }

            gestorPedidos.agregarPedido(pedido);
            modeloProductos.clear();
            JOptionPane.showMessageDialog(this,
                    String.format("Pedido creado con %d productos. Puntos ganados: %d",
                            pedido.getProductos().size(), pedido.getPuntos()),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });

        panelAgregar.add(scrollProductos, BorderLayout.CENTER);
        panelAgregar.add(panelAgregarProducto, BorderLayout.NORTH);
        panelAgregar.add(btnCrearPedido, BorderLayout.SOUTH);

        tabbedPane.addTab("Agregar Pedido", panelAgregar);

        // Pestaña para procesar pedido
        JPanel panelProcesar = new JPanel(new BorderLayout());
        JTextArea txtDetallePedido = new JTextArea();
        txtDetallePedido.setEditable(false);
        JScrollPane scrollDetalle = new JScrollPane(txtDetallePedido);

        JButton btnProcesar = new JButton("Procesar Siguiente Pedido");
        btnProcesar.addActionListener(e -> {
            if (!gestorPedidos.hayPedidos()) {
                JOptionPane.showMessageDialog(this, "No hay pedidos pendientes", "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Pedido pedido = gestorPedidos.procesarPedido();
            StringBuilder sb = new StringBuilder();
            sb.append("Pedido procesado:\n\n");
            sb.append("Productos:\n");
            for (String producto : pedido.getProductos()) {
                sb.append(" - ").append(producto).append("\n");
            }
            sb.append("\nTotal puntos ganados: ").append(pedido.getPuntos());

            txtDetallePedido.setText(sb.toString());
        });

        panelProcesar.add(scrollDetalle, BorderLayout.CENTER);
        panelProcesar.add(btnProcesar, BorderLayout.SOUTH);

        tabbedPane.addTab("Procesar Pedido", panelProcesar);

        panel.add(tabbedPane, BorderLayout.CENTER);
        cardPanel.add(panel, "pedidos");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}