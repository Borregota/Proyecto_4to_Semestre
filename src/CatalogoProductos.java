import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatalogoProductos {
    private static CatalogoProductos instance;
    private final List<Producto> productos;

    private CatalogoProductos() {
        productos = new ArrayList<>();
        // Productos iniciales
        productos.add(new Producto("FocusMax", "concentracion", 12.5, 20));
        productos.add(new Producto("EnergyRush", "energia", 10.0, 15));
        productos.add(new Producto("SleepEase", "relajacion", 8.75, 10));
        productos.add(new Producto("HydraBoost", "hidratacion", 6.99, 25));
    }

    public static synchronized CatalogoProductos getInstance() {
        if (instance == null) {
            instance = new CatalogoProductos();
        }
        return instance;
    }

    public void agregarProducto(Producto producto) {
        Objects.requireNonNull(producto, "El producto no puede ser nulo");
        productos.add(producto);
    }

    public boolean eliminarProducto(String nombre) {
        return productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    public List<Producto> getTodosProductos() {
        return new ArrayList<>(productos);
    }

    public List<Producto> getProductosPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }
}