package calculoFibraOptica;

public class Conexion {
	Localidad origen;
	Localidad destino;
	double costo;

	public Conexion(Localidad origen, Localidad destino, double costo) {
		this.origen = origen;
		this.destino = destino;
		this.costo = costo;
	}

	public Localidad obtenerVerticeOrigen() {
		return origen;
	}
	
	public Localidad obtenerVerticeDestino() {
		return destino;
	}

	public double obtenerPeso() {
		return costo;
	}
}
