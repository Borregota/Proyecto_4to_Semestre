public class MetaCalorica {
    private final int caloriasMeta;
    private int caloriasConsumidas;

    public MetaCalorica(int caloriasMeta) {
        this.caloriasMeta = caloriasMeta;
        this.caloriasConsumidas = 0;
    }

    public void consumir(int calorias) {
        caloriasConsumidas += calorias;
    }

    public int getRestante() {
        return Math.max(0, caloriasMeta - caloriasConsumidas);
    }

    public boolean metaSuperada() {
        return caloriasConsumidas > caloriasMeta;
    }

    public int getConsumidas() {
        return caloriasConsumidas;
    }

    public int getMeta() {
        return caloriasMeta;
    }
}