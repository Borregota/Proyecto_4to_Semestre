public class Direccion {
    private String calle;
    private String ciudad;
    private String codigoPostal;

    public Direccion(String calle, String ciudad, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public void actualizarDireccion(String calle, String ciudad, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() { return calle; }
    public String getCiudad() { return ciudad; }
    public String getCodigoPostal() { return codigoPostal; }

    @Override
    public String toString() {
        return String.format("%s, %s, CP: %s", calle, ciudad, codigoPostal);
    }
}