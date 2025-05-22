import java.util.ArrayList;
import java.util.List;

public class CatalogoProductos {
    private List<Producto> productos;

    public CatalogoProductos() {
        productos = new ArrayList<>();
        productos.add(new Producto("FocusMax", "concentración", 12.5, 20));
        productos.add(new Producto("EnergyRush", "energía", 10.0, 15));
        productos.add(new Producto("SleepEase", "relajación", 8.75, 10));
        productos.add(new Producto("HydraBoost", "hidratación", 6.99, 25));
        productos.add(new Producto("GamerFuel", "energía", 9.5, 30));
        productos.add(new Producto("NeuroShot", "concentración", 11.2, 12));
    }

    public List<Producto> getProductosPorCategoria(String categoria) {
        List<Producto> recomendados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                recomendados.add(p);
            }
        }
        return recomendados;
    }

    public List<Producto> getTodos() {
        return productos;
    }
}
