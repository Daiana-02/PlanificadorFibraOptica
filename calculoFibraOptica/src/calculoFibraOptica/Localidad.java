package calculoFibraOptica;

public class Localidad {
	private String nombre;
	private String provincia;
	private double latitud;
	private double longitud;

	public Localidad(String nombre, String provincia, double latitud, double longitud) {
		this.nombre = nombre;
		this.provincia = provincia;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	public double obtenerLatitud() {
		return latitud;
	}
	
	public double obtenerLongitud() {
		return longitud;
	}
	
	public String obtenerProvincia() {
		return provincia; 
	}

}
