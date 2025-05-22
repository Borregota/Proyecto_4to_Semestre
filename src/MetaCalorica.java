public class MetaCalorica {
    private int caloriasMeta;
    private int caloriasConsumidas;

    public MetaCalorica(int caloriasMeta) {
        this.caloriasMeta = caloriasMeta;
        this.caloriasConsumidas = 0;
    }

    public void consumir(int calorias) {
        caloriasConsumidas += calorias;
    }

    public int getRestante() {
        return caloriasMeta - caloriasConsumidas;
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
