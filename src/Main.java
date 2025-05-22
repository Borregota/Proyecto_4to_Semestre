import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CatalogoProductos catalogo = new CatalogoProductos();
        Recomendador recomendador = new Recomendador(catalogo);
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorCalorias gestorCalorias = new GestorCalorias();
        GestorDirecciones gestorDirecciones = new GestorDirecciones();
        GestorPedidos gestorPedidos = new GestorPedidos();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Ver recomendación");
            System.out.println("3. Asignar meta calórica");
            System.out.println("4. Registrar consumo");
            System.out.println("5. Ver estado de calorías");
            System.out.println("6. Agregar dirección");
            System.out.println("7. Agregar pedido");
            System.out.println("8. Procesar pedido");
            System.out.println("9. Salir");
            System.out.print("Selecciona una opción: ");
            int op = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (op) {
                case 1:
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = sc.nextLine();
                    Usuario u = new Usuario(correo.hashCode() + "", nombre, correo, pass);
                    gestorUsuarios.registrarUsuario(u);
                    System.out.println("Usuario registrado.");
                    break;

                case 2:
                    System.out.print("¿Qué necesitas? (energía/concentración/relajación): ");
                    String necesidad = sc.nextLine();
                    recomendador.recomendarPorCategoria(necesidad);
                    break;

                case 3:
                    System.out.print("Correo del usuario: ");
                    String userMeta = sc.nextLine();
                    System.out.print("Meta de calorías: ");
                    int meta = sc.nextInt();
                    gestorCalorias.asignarMeta(userMeta, meta);
                    System.out.println("Meta asignada.");
                    break;

                case 4:
                    System.out.print("Correo del usuario: ");
                    String userComida = sc.nextLine();
                    System.out.print("Calorías consumidas: ");
                    int cal = sc.nextInt();
                    gestorCalorias.consumirAlimento(userComida, cal);
                    System.out.println("Registrado.");
                    break;

                case 5:
                    System.out.print("Correo del usuario: ");
                    String userEstado = sc.nextLine();
                    System.out.println(gestorCalorias.estadoMeta(userEstado));
                    break;

                case 6:
                    System.out.print("Calle: ");
                    String calle = sc.nextLine();
                    System.out.print("Ciudad: ");
                    String ciudad = sc.nextLine();
                    System.out.print("Código postal: ");
                    String cp = sc.nextLine();
                    gestorDirecciones.agregarDireccion(new Direccion(calle, ciudad, cp));
                    System.out.println("Dirección agregada.");
                    break;

                case 7:
                    Pedido pedido = new Pedido();
                    System.out.print("¿Cuántos productos deseas agregar? ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Producto " + (i + 1) + ": ");
                        String prod = sc.nextLine();
                        pedido.agregarProducto(prod);
                    }
                    gestorPedidos.agregarPedido(pedido);
                    System.out.println("Pedido agregado con " + pedido.getPuntos() + " puntos.");
                    break;

                case 8:
                    Pedido procesado = gestorPedidos.procesarPedido();
                    if (procesado != null) {
                        System.out.println("Pedido procesado. Productos:");
                        for (String prod : procesado.getProductos()) {
                            System.out.println(" - " + prod);
                        }
                        System.out.println("Total puntos ganados: " + procesado.getPuntos());
                    } else {
                        System.out.println("No hay pedidos en cola.");
                    }
                    break;

                case 9:
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        System.out.println("Gracias por usar Plus Ultra.");
        sc.close();
    }
}
