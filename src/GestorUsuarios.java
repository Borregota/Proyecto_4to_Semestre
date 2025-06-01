import java.util.HashMap;

public class GestorUsuarios {
    private static GestorUsuarios instance;
    private final HashMap<String, Usuario> usuarios;

    private GestorUsuarios() {
        usuarios = new HashMap<>();
        // Contraseña generada que cumple con los 12 caracteres
        String contrasenaAdmin = "Adm1nP@ss123"; // 12 caracteres exactos
        Usuario admin = new Usuario("0", "Admin", "admin@plusultra.com", contrasenaAdmin, true);
        usuarios.put(admin.getCorreo(), admin);
    }

    public static synchronized GestorUsuarios getInstance() {
        if (instance == null) {
            instance = new GestorUsuarios();
        }
        return instance;
    }

    public void registrarUsuario(Usuario u) {
        if (u.getContrasena().length() != 12) {
            throw new IllegalArgumentException("La contraseña debe tener 12 caracteres");
        }
        usuarios.put(u.getCorreo(), u);
    }

    public boolean autenticarUsuario(String correo, String contrasena) {
        Usuario u = usuarios.get(correo);
        return u != null && u.getContrasena().equals(contrasena);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarios.get(correo);
    }

    public HashMap<String, Usuario> getTodosUsuarios() {
        return new HashMap<>(usuarios);
    }
}