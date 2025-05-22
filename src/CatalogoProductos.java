import java.util.ArrayList;
import java.util.List;

// Clase que representa un catálogo de productos
public class CatalogoProductos {
    // Lista que almacena los productos disponibles
    private List<Producto> productos;

    // Constructor que inicializa el catálogo con productos predefinidos
    public CatalogoProductos() {
        productos = new ArrayList<>();
        // Agregando productos al catálogo con su nombre, categoría, precio y stock
        productos.add(new Producto("FocusMax", "concentracion", 12.5, 20));
        productos.add(new Producto("EnergyRush", "energía", 10.0, 15));
        productos.add(new Producto("SleepEase", "relajacion", 8.75, 10));
        productos.add(new Producto("HydraBoost", "hidratacion", 6.99, 25));
        productos.add(new Producto("GamerFuel", "energia", 9.5, 30));
        productos.add(new Producto("NeuroShot", "concentracion", 11.2, 12));
    }

    // Metodo que devuelve una lista de productos que pertenecen a una categoría específica
    public List<Producto> getProductosPorCategoria(String categoria) {
        List<Producto> recomendados = new ArrayList<>();
        // Recorre todos los productos y añade los que coincidan con la categoría (sin importar mayúsculas o minúsculas)
        for (Producto p : productos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                recomendados.add(p);
            }
        }
        return recomendados; // Devuelve la lista de productos recomendados
    }

    // Metodo que devuelve todos los productos del catálogo
    public List<Producto> getTodos() {
        return productos;
    }
}