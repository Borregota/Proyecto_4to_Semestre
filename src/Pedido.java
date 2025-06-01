import java.util.*;

public class Pedido {
    private final Queue<String> productos;
    private int puntos;

    public Pedido() {
        productos = new LinkedList<>();
        puntos = 0;
    }

    public void agregarProducto(String producto) {
        productos.add(producto);
        puntos += 10;
    }

    public Queue<String> getProductos() {
        return new LinkedList<>(productos);
    }

    public int getPuntos() {
        return puntos;
    }
}