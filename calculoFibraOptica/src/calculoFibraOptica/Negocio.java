package calculoFibraOptica;


public class Negocio {
	Grafo grafo;
	private Double costoPorKm;
	private Double porcentajeExtra;
	private Double costoFijo;
	
	public Negocio() {
		grafo = new Grafo();
		costoPorKm = 20.0;
		porcentajeExtra = 50.0;
		costoFijo = 150.0;
	}
	
	public void agregarVertices(String nombreStr, String provinciaStr, Double latitud, Double longitud) {
		grafo.agregarLocalidad(new Localidad(nombreStr, provinciaStr, latitud, longitud));
	}
	
	public void agregarLocalidades() {
		grafo.generarAristas(costoPorKm, porcentajeExtra, costoFijo);
	}
}
