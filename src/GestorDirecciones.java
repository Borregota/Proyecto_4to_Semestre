import java.util.*;

public class GestorDirecciones {
    private static GestorDirecciones instance;
    private final LinkedList<Direccion> direcciones;

    private GestorDirecciones() {
        direcciones = new LinkedList<>();
    }

    public static synchronized GestorDirecciones getInstance() {
        if (instance == null) {
            instance = new GestorDirecciones();
        }
        return instance;
    }

    public void agregarDireccion(Direccion d) {
        direcciones.add(d);
    }

    public void modificarDireccion(int index, Direccion nueva) {
        if (index >= 0 && index < direcciones.size()) {
            direcciones.set(index, nueva);
        }
    }

    public List<Direccion> getDirecciones() {
        return new ArrayList<>(direcciones);
    }
}