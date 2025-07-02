import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    private Usuario usuarioActual;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        crearPanelLogin();
        crearPanelRegistro();

        cardLayout.show(cardPanel, "login");
    }

    private void crearPanelLogin() {
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.gridwidth = 1;

        gbc.gridx = 1;

        gbc.gridx = 0;

        gbc.gridx = 1;

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnLogin.addActionListener(e -> {

                } else {
            }
        });


    }

    private void crearPanelRegistro() {
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitulo = new JLabel("Registro de Usuario");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        gbc.gridwidth = 1;

        gbc.gridx = 1;

        gbc.gridx = 0;

        gbc.gridx = 1;

        gbc.gridx = 0;

        gbc.gridx = 1;

        gbc.gridx = 0;

        gbc.gridx = 1;

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        btnRegistrar.addActionListener(e -> {

                return;
            }

                return;
            }

                return;
            }


                JOptionPane.showMessageDialog(this,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
        });





    }



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        });
    }
}