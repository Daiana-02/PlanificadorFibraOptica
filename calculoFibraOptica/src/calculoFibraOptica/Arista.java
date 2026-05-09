package calculoFibraOptica;

public class Arista {
	private int origen;
    private int destino;
    private double costo;

    public Arista(int origen, int destino, double costo) {
        this.origen = origen;
        this.destino = destino;
        this.costo = costo;
    }
    
    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public double getCosto() {
        return costo;
    }
}
