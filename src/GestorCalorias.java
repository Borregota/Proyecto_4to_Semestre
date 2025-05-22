import java.util.*;

public class GestorCalorias {
    private Stack<Integer> alimentosDelDia;
    private Queue<MetaCalorica> historialDias;
    private HashMap<String, MetaCalorica> metasUsuario;

    public GestorCalorias() {
        alimentosDelDia = new Stack<>();
        historialDias = new LinkedList<>();
        metasUsuario = new HashMap<>();
    }

    public void asignarMeta(String usuario, int meta) {
        MetaCalorica nueva = new MetaCalorica(meta);
        metasUsuario.put(usuario, nueva);
    }

    public void consumirAlimento(String usuario, int calorias) {
        alimentosDelDia.push(calorias);
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta != null) meta.consumir(calorias);
    }

    public String estadoMeta(String usuario) {
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta == null) return "Meta no registrada";

        return "Has consumido " + meta.getConsumidas() + "/" + meta.getMeta() + " calorías. " +
                (meta.metaSuperada() ? "¡Has superado la meta!" : "Aún puedes consumir " + meta.getRestante() + " calorías.");
    }

    public void cerrarDia(String usuario) {
        MetaCalorica meta = metasUsuario.get(usuario);
        if (meta != null) {
            historialDias.add(meta);
            alimentosDelDia.clear();
        }
    }
}

