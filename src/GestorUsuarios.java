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

    public boolean autenticarUsuario(String correo, String contrasena) {
        Usuario usuario = usuarios.get(correo);
        return usuario != null && usuario.getContrasena().equals(contrasena);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getCorreo(), usuario);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarios.get(correo);
    }

    public Usuario buscarPorId(String id) {
        return usuarios.values().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public HashMap<String, Usuario> getTodosUsuarios() {
        return new HashMap<>(usuarios);
    }
}