// Esta es la clase que representa un producto con nombre, categoría, precio y stock
public class Producto {
    // Atributos privados del producto
    private String nombre;     // Nombre del producto
    private String categoria;  // Categoría del producto (ej: energía, concentración, etc.)
    private double precio;     // Precio del producto
    private int stock;         // Cantidad disponible en inventario

    // Constructor que inicializa todos los atributos del producto
    public Producto(String nombre, String categoria, double precio, int stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos getter para acceder a los atributos del producto
    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}