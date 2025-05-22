import java.util.*;

// Clase que gestiona el consumo de calorías y metas diarias de los usuarios
public class GestorCalorias {

    // Pila para registrar las calorías consumidas en el día actual (último alimento ingresado arriba)
    private Stack<Integer> alimentosDelDia;

    // Cola para almacenar el historial de metas calóricas por día (orden cronológico)
    private Queue<MetaCalorica> historialDias;

    // Mapa que relaciona cada usuario con su meta calórica correspondiente
    private HashMap<String, MetaCalorica> metasUsuario;

    // Constructor: inicializa las estructuras de datos
    public GestorCalorias() {
        alimentosDelDia = new Stack<>();
        historialDias = new LinkedList<>();
        metasUsuario = new HashMap<>();
    }

    // Asigna una nueva meta calórica a un usuario
    public void asignarMeta(String usuario, int meta) {
        MetaCalorica nueva = new MetaCalorica(meta); // Crea un objeto MetaCalorica
        metasUsuario.put(usuario, nueva); // Lo asocia al usuario en el mapa
    }

    // Registra la cantidad de calorías consumidas por el usuario
    public void consumirAlimento(String usuario, int calorias) {
        alimentosDelDia.push(calorias); // Agrega las calorías a la pila del día
        MetaCalorica meta = metasUsuario.get(usuario); // Obtiene la meta del usuario
        if (meta != null) meta.consumir(calorias); // Suma las calorías a la meta, si existe
    }

    // Devuelve el estado actual de la meta del usuario (cuántas calorías ha consumido y cuánto le falta)
    public String estadoMeta(String usuario) {
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta == null) return "Meta no registrada"; // Si no hay meta, devuelve un mensaje

        // Devuelve un resumen del estado de la meta: consumido / meta total
        return "Has consumido " + meta.getConsumidas() + "/" + meta.getMeta() + " calorías. " +
                (meta.metaSuperada() ? "¡Has superado la meta!" : "Aún puedes consumir " + meta.getRestante() + " calorías.");
    }

    // Finaliza el día para el usuario: guarda la meta en el historial y reinicia la pila de alimentos
    public void cerrarDia(String usuario) {
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta != null) {
            historialDias.add(meta); // Se añade la meta del día al historial
            alimentosDelDia.clear(); // Se limpian los alimentos consumidos del día actual
        }
    }
}
