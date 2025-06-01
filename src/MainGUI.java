import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {
    private final GestorUsuarios gestorUsuarios;
    private final CatalogoProductos catalogo;
    private Usuario usuarioActual;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainGUI(GestorUsuarios gestorUsuarios, CatalogoProductos catalogo) {
        this.gestorUsuarios = gestorUsuarios;
        this.catalogo = catalogo;
        initUI();
    }

    private void initUI() {
        setTitle("Sistema PlusUltra");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del sistema de cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crear las diferentes vistas
        crearPanelLogin();
        crearPanelRegistro();

        add(cardPanel);
        cardLayout.show(cardPanel, "login");
    }

    private void crearPanelLogin() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Componentes del login
        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblEmail = new JLabel("Correo electrónico:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField(20);

        JButton btnLogin = new JButton("Ingresar");
        JButton btnRegistro = new JButton("Registrarse");

        // Configuración del layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        loginPanel.add(lblContrasena, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(txtContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.add(btnLogin);
        btnPanel.add(btnRegistro);
        loginPanel.add(btnPanel, gbc);

        // Acción del botón login
        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());

            if (email.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (gestorUsuarios.autenticarUsuario(email, contrasena)) {
                usuarioActual = gestorUsuarios.buscarPorCorreo(email);

                if (usuarioActual.isAdmin()) {
                    abrirPanelAdmin();
                } else {
                    abrirPanelUsuario();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Credenciales incorrectas",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción del botón registro
        btnRegistro.addActionListener(e -> {
            cardLayout.show(cardPanel, "registro");
        });

        cardPanel.add(loginPanel, "login");
    }

    private void crearPanelRegistro() {
        JPanel registroPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Componentes del registro
        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblNombre = new JLabel("Nombre completo:");
        JTextField txtNombre = new JTextField(20);

        JLabel lblEmail = new JLabel("Correo electrónico:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblContrasena = new JLabel("Contraseña (12 caracteres):");
        JPasswordField txtContrasena = new JPasswordField(20);

        JLabel lblConfirmar = new JLabel("Confirmar contraseña:");
        JPasswordField txtConfirmar = new JPasswordField(20);

        JButton btnRegistrar = new JButton("Registrarse");
        JButton btnVolver = new JButton("Volver al Login");

        // Configuración del layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        registroPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        registroPanel.add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registroPanel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        registroPanel.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registroPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        registroPanel.add(lblContrasena, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registroPanel.add(txtContrasena, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        registroPanel.add(lblConfirmar, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        registroPanel.add(txtConfirmar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.add(btnRegistrar);
        btnPanel.add(btnVolver);
        registroPanel.add(btnPanel, gbc);

        // Acción del botón registrar
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String contrasena = new String(txtContrasena.getPassword());
            String confirmacion = new String(txtConfirmar.getPassword());

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contrasena.equals(confirmacion)) {
                JOptionPane.showMessageDialog(this,
                        "Las contraseñas no coinciden",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contrasena.length() != 12) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener exactamente 12 caracteres",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Generar ID único
                String id = "USER-" + System.currentTimeMillis();
                Usuario nuevoUsuario = new Usuario(id, nombre, email, contrasena, false);
                gestorUsuarios.registrarUsuario(nuevoUsuario);

                JOptionPane.showMessageDialog(this,
                        "Registro exitoso! Por favor inicie sesión",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al registrar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción del botón volver
        btnVolver.addActionListener(e -> {
            cardLayout.show(cardPanel, "login");
        });

        cardPanel.add(registroPanel, "registro");
    }

    private void abrirPanelAdmin() {
        // Crear el panel de administración
        JFrame adminFrame = new JFrame("Panel de Administración");
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setSize(900, 650);
        adminFrame.setLocationRelativeTo(this);

        PanelAdmin panelAdmin = new PanelAdmin(gestorUsuarios, catalogo);
        adminFrame.add(panelAdmin);

        adminFrame.setVisible(true);
    }

    private void abrirPanelUsuario() {
        // Crear el panel de usuario
        JFrame userFrame = new JFrame("Panel de Usuario");
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userFrame.setSize(900, 650);
        userFrame.setLocationRelativeTo(this);

        UsuarioPanel usuarioPanel = new UsuarioPanel(usuarioActual, catalogo);
        userFrame.add(usuarioPanel);

        userFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorUsuarios gestor = GestorUsuarios.getInstance();
            CatalogoProductos catalogo = CatalogoProductos.getInstance();
            new MainGUI(gestor, catalogo).setVisible(true);
        });
    }
}