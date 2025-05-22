import java.util.List;

// Clase encargada de recomendar productos según una categoría dada
public class Recomendador {
    private CatalogoProductos catalogo; // Referencia al catálogo de productos

    // Constructor que recibe un catálogo de productos para usar en las recomendaciones
    public Recomendador(CatalogoProductos catalogo) {
        this.catalogo = catalogo;
    }

    // Metodo que muestra por consola los productos recomendados según la categoría proporcionada
    public void recomendarPorCategoria(String categoria) {
        // Obtiene la lista de productos de esa categoría desde el catálogo
        List<Producto> lista = catalogo.getProductosPorCategoria(categoria);

        // Si la lista está vacía, muestra un mensaje indicando que no hay productos disponibles
        if (lista.isEmpty()) {
            System.out.println("No hay productos disponibles para esa necesidad.");
        } else {
            // Muestra los productos encontrados con su nombre y precio
            System.out.println("Productos recomendados para " + categoria + ":");
            for (Producto p : lista) {
                System.out.println(" - " + p.getNombre() + " ($" + p.getPrecio() + ")");
            }
        }
    }
}
