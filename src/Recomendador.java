import java.util.List;

public class Recomendador {
    private final CatalogoProductos catalogo;

    public Recomendador(CatalogoProductos catalogo) {
        this.catalogo = catalogo;
    }

    public List<Producto> recomendarPorCategoria(String categoria) {
        return catalogo.getProductosPorCategoria(categoria);
    }
}