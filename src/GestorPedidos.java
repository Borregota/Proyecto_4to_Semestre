import java.util.*;

public class GestorPedidos {
    private static GestorPedidos instance;
    private final Queue<Pedido> pedidos;

    private GestorPedidos() {
        pedidos = new LinkedList<>();
    }

    public static synchronized GestorPedidos getInstance() {
        if (instance == null) {
            instance = new GestorPedidos();
        }
        return instance;
    }

    public void agregarPedido(Pedido p) {
        pedidos.add(p);
    }

    public Pedido procesarPedido() {
        return pedidos.poll();
    }

    public boolean hayPedidos() {
        return !pedidos.isEmpty();
    }
}