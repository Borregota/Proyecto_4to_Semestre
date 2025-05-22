import java.util.LinkedList;

public class GestorDirecciones {
    private LinkedList<Direccion> direcciones;

    public GestorDirecciones() {
        direcciones = new LinkedList<>();
    }

    public void agregarDireccion(Direccion d) {
        direcciones.add(d);
    }

    public void modificarDireccion(int index, Direccion nueva) {
        if (index >= 0 && index < direcciones.size()) {
            direcciones.set(index, nueva);
        }
    }
}

