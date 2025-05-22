import java.util.LinkedList;
import java.util.Queue;

public class Pedido {
    private Queue<String> productos;
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
        return productos;
    }

    public int getPuntos() {
        return puntos;
    }
}
