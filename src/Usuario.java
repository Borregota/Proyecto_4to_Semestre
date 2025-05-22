public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String codigoRecuperacion;

    public Usuario(String id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.codigoRecuperacion = generarCodigo();
    }

    private String generarCodigo() {
        return String.valueOf((int)(Math.random() * 1000000));
    }

    public void actualizarDatos(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String recuperarCodigo(String correoIngresado) {
        return correoIngresado.equals(this.correo) ? codigoRecuperacion : null;
    }

    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getCodigoRecuperacion() { return codigoRecuperacion; }
}

