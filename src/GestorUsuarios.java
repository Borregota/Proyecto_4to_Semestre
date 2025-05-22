import java.util.HashMap;

public class GestorUsuarios {
    private HashMap<String, Usuario> usuarios;

    public GestorUsuarios() {
        usuarios = new HashMap<>();
    }

    public void registrarUsuario(Usuario u) {
        usuarios.put(u.getCorreo(), u);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarios.get(correo);
    }

    public boolean autenticarUsuario(String correo, String contrasena) {
        Usuario u = usuarios.get(correo);
        return u != null && u.getContrasena().equals(contrasena);
    }

    public String recuperarCodigo(String correo) {
        Usuario u = usuarios.get(correo);
        return (u != null) ? u.getCodigoRecuperacion() : null;
    }
}
