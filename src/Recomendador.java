import java.util.List;

public class Recomendador {
    private CatalogoProductos catalogo;

    public Recomendador(CatalogoProductos catalogo) {
        this.catalogo = catalogo;
    }

    public void recomendarPorCategoria(String categoria) {
        List<Producto> lista = catalogo.getProductosPorCategoria(categoria);

        if (lista.isEmpty()) {
            System.out.println("No hay productos disponibles para esa necesidad.");
        } else {
            System.out.println("Productos recomendados para " + categoria + ":");
            for (Producto p : lista) {
                System.out.println(" - " + p.getNombre() + " ($" + p.getPrecio() + ")");
            }
        }
    }
}



