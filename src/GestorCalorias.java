import java.util.*;

public class GestorCalorias {
    private static GestorCalorias instance;
    private final Stack<Integer> alimentosDelDia;
    private final Queue<MetaCalorica> historialDias;
    private final HashMap<String, MetaCalorica> metasUsuario;

    private GestorCalorias() {
        alimentosDelDia = new Stack<>();
        historialDias = new LinkedList<>();
        metasUsuario = new HashMap<>();
    }

    public static synchronized GestorCalorias getInstance() {
        if (instance == null) {
            instance = new GestorCalorias();
        }
        return instance;
    }

    public void asignarMeta(String usuario, int meta) {
        metasUsuario.put(usuario, new MetaCalorica(meta));
    }

    public void consumirAlimento(String usuario, int calorias) {
        alimentosDelDia.push(calorias);
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta != null) meta.consumir(calorias);
    }

    public String estadoMeta(String usuario) {
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta == null) return "Meta no registrada";

        return String.format("Has consumido %d/%d calorías. %s",
                meta.getConsumidas(), meta.getMeta(),
                meta.metaSuperada() ? "¡Has superado la meta!" :
                        "Aún puedes consumir " + meta.getRestante() + " calorías.");
    }

    public void cerrarDia(String usuario) {
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta != null) {
            historialDias.add(meta);
            alimentosDelDia.clear();
        }
    }
}