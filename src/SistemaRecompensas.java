import java.util.HashMap;
import java.util.Map;

public class SistemaRecompensas {
    private final Map<String, Integer> puntosUsuarios;
    private final CatalogoProductos catalogo;

    public SistemaRecompensas(CatalogoProductos catalogo) {
        this.puntosUsuarios = new HashMap<>();
        this.catalogo = catalogo;
    }

    public void agregarPuntos(String usuarioId, int puntos) {
        puntosUsuarios.merge(usuarioId, puntos, Integer::sum);
    }

    public int consultarPuntos(String usuarioId) {
        return puntosUsuarios.getOrDefault(usuarioId, 0);
    }

    public boolean canjearProducto(String usuarioId, String productoNombre) {
        Producto producto = catalogo.getTodosProductos().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(productoNombre))
                .findFirst()
                .orElse(null);

        if (producto == null || producto.getStock() <= 0) return false;

        int puntosRequeridos = (int)(producto.getPrecio() * 10);
        int puntosUsuario = consultarPuntos(usuarioId);

        if (puntosUsuario >= puntosRequeridos) {
            puntosUsuarios.put(usuarioId, puntosUsuario - puntosRequeridos);
            producto.setStock(producto.getStock() - 1);
            return true;
        }
        return false;
    }
}