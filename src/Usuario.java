import java.util.Random;

public class Usuario {
    private final String id;
    private String nombre;
    private final String correo;
    private String contrasena;
    private final String codigoRecuperacion;
    private boolean esAdmin;

    public Usuario(String id, String nombre, String correo, String contrasena, boolean esAdmin) {
        if (contrasena == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        if (contrasena.length() != 12) {
            throw new IllegalArgumentException(
                    "La contraseña debe tener exactamente 12 caracteres. Longitud actual: " + contrasena.length());
        }
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.codigoRecuperacion = generarCodigo();
        this.esAdmin = esAdmin;
    }

    private String generarCodigo() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    public boolean verificarCodigoRecuperacion(String codigo) {
        return this.codigoRecuperacion.equals(codigo);
    }

    public void cambiarContrasena(String nuevaContrasena) {
        if (nuevaContrasena.length() != 12) {
            throw new IllegalArgumentException("La contraseña debe tener 12 caracteres");
        }
        this.contrasena = nuevaContrasena;
    }

    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getCodigoRecuperacion() { return codigoRecuperacion; }
    public String getNombre() { return nombre; }
    public String getId() { return id; }
    public boolean isAdmin() { return esAdmin; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAdmin(boolean esAdmin) { this.esAdmin = esAdmin; }
}