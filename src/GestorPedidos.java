import java.util.LinkedList;
import java.util.Queue;

public class GestorPedidos {
    private Queue<Pedido> pedidos;

    public GestorPedidos() {
        pedidos = new LinkedList<>();
    }

    public void agregarPedido(Pedido p) {
        pedidos.add(p);
    }

    public Pedido procesarPedido() {
        return pedidos.poll();
    }
}