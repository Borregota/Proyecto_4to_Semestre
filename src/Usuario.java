// Clase que representa un usuario del sistema
public class Usuario {
    // Atributos privados del usuario
    // Identificador único del usuario
    private String id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String codigoRecuperacion;

    // Constructor que inicializa los datos del usuario y genera automáticamente un código de recuperación
    public Usuario(String id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.codigoRecuperacion = generarCodigo(); // Se genera un código aleatorio al crear el usuario
    }

    // Metodo privado que genera un código numérico aleatorio de 6 dígitos como cadena de texto
    private String generarCodigo() {
        return String.valueOf((int)(Math.random() * 1000000));
    }

    // Permite actualizar el nombre y correo del usuario
    public void actualizarDatos(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    // Devuelve el código de recuperación si el correo ingresado coincide con el registrado
    public String recuperarCodigo(String correoIngresado) {
        return correoIngresado.equals(this.correo) ? codigoRecuperacion : null;
    }

    // Métodos getter para acceder a algunos atributos privados
    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getCodigoRecuperacion() {
        return codigoRecuperacion;
    }
}