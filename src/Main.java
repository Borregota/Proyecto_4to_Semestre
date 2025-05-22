import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Se crea un objeto Scanner para leer entrada del usuario por consola
        Scanner sc = new Scanner(System.in);

        // Se inicializan los distintos gestores del sistema
        CatalogoProductos catalogo = new CatalogoProductos();          // El Catalogo de productos
        Recomendador recomendador = new Recomendador(catalogo);       // Recomendador que usa el catálogo
        GestorUsuarios gestorUsuarios = new GestorUsuarios();         // Maneja los usuarios
        GestorCalorias gestorCalorias = new GestorCalorias();         // Controla las metas calóricas
        GestorDirecciones gestorDirecciones = new GestorDirecciones(); // Gestiona direcciones
        GestorPedidos gestorPedidos = new GestorPedidos();            // Gestiona pedidos

        boolean salir = false; // Variable de control del menú

        // Bucle principal del menú
        while (!salir) {
            // Menú de opciones para el usuario
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

            int op = sc.nextInt(); // Lectura de la opción del menú
            sc.nextLine(); // Limpia el buffer de entrada (por el salto de línea)

            switch (op) {
                case 1:
                    // Crear nuevo usuario
                    System.out.print("Correo: ");
                    String correo = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Contraseña: ");
                    String pass = sc.nextLine();

                    // Se genera un ID a partir del hash del correo
                    Usuario u = new Usuario(correo.hashCode() + "", nombre, correo, pass);
                    gestorUsuarios.registrarUsuario(u);
                    System.out.println("Usuario registrado.");
                    break;

                case 2:
                    // Solicita una recomendación de producto por categoría
                    System.out.print("¿Qué necesitas? (energía/concentración/relajación): ");
                    String necesidad = sc.nextLine();
                    recomendador.recomendarPorCategoria(necesidad);
                    break;

                case 3:
                    // Asigna una meta calórica a un usuario
                    System.out.print("Correo del usuario: ");
                    String userMeta = sc.nextLine();
                    System.out.print("Meta de calorías: ");
                    int meta = sc.nextInt();
                    gestorCalorias.asignarMeta(userMeta, meta);
                    System.out.println("Meta asignada.");
                    break;

                case 4:
                    // Registra calorías consumidas por un usuario
                    System.out.print("Correo del usuario: ");
                    String userComida = sc.nextLine();
                    System.out.print("Calorías consumidas: ");
                    int cal = sc.nextInt();
                    gestorCalorias.consumirAlimento(userComida, cal);
                    System.out.println("Registrado.");
                    break;

                case 5:
                    // Muestra el estado actual de la meta calórica del usuario
                    System.out.print("Correo del usuario: ");
                    String userEstado = sc.nextLine();
                    System.out.println(gestorCalorias.estadoMeta(userEstado));
                    break;

                case 6:
                    // Agrega una nueva dirección
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
                    // Agrega un nuevo pedido con productos seleccionados por el usuario
                    Pedido pedido = new Pedido();
                    System.out.print("¿Cuántos productos deseas agregar? ");
                    int n = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer

                    // Se agregan los productos al pedido
                    for (int i = 0; i < n; i++) {
                        System.out.print("Producto " + (i + 1) + ": ");
                        String prod = sc.nextLine();
                        pedido.agregarProducto(prod);
                    }
                    gestorPedidos.agregarPedido(pedido);
                    System.out.println("Pedido agregado con " + pedido.getPuntos() + " puntos.");
                    break;

                case 8:
                    // Procesa el siguiente pedido en cola
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
                    // Finaliza el programa
                    salir = true;
                    break;

                default:
                    // Opción no válida
                    System.out.println("Opción no válida.");
            }
        }

        // Mensaje de despedida
        System.out.println("Gracias por usar Plus Ultra.");
        sc.close(); // Se cierra el Scanner
    }
}