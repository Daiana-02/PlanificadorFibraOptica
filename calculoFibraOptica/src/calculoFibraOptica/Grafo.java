package calculoFibraOptica;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
	private List<Localidad> localidades = new ArrayList<>();
	private List<Conexion> conexiones = new ArrayList<>();
	private static final double radioDeLaTierra = 6371.0;
	
	public void agregarLocalidad(Localidad vertice) {
		localidades.add(vertice);
	}
	
	public void generarAristas(Double costoPorKm, Double porcentajeExtra, Double costoFijo) {
		for (int actu = 0; actu < localidades.size()-1; actu++) {
			for (int sig = actu + 1; sig < localidades.size(); sig++) {

				Localidad locA = localidades.get(actu);
				Localidad locB = localidades.get(sig);
				String provinciaA = locA.obtenerProvincia();
				String provinciaB = locB.obtenerProvincia();
				boolean mismaProvincia = provinciaA.equals(provinciaB);
				
				Double distancia = calcularDistancia(locA.obtenerLatitud(), locA.obtenerLongitud(), locB.obtenerLatitud(), locB.obtenerLongitud());
				Double costo = calcularCosto(distancia, costoPorKm, porcentajeExtra, costoFijo, mismaProvincia);
				
				System.out.println(costo);
				conexiones.add(new Conexion(provinciaA, provinciaB, costo));
			}
		}
	}
	
	public double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
		double distanciaLatitud = Math.toRadians(latitud2 - latitud1 );
		double distanciaLongitud = Math.toRadians(longitud2 - longitud1 );
		
		double radioLatitud1 = Math.toRadians(latitud1);
		double radioLatitud2 = Math.toRadians(latitud2);
		
		double harversen = Math.pow(Math.sin(distanciaLatitud/2), 2) + Math.cos(radioLatitud1) * Math.cos(radioLatitud2) * Math.pow(Math.sin(distanciaLongitud/2), 2);
		
		double anguloCentral = 2 * Math.atan2(Math.sqrt(harversen), Math.sqrt(1 - harversen));
		
		return radioDeLaTierra * anguloCentral;
	}
	
	public double calcularCosto(double distancia, double costoPorKm, double porcentajeAumento, double costoFijo, boolean distintasProvincias) {
	    double costo = distancia * costoPorKm;

	    if (distancia > 300) {
	        costo += costo * (porcentajeAumento / 100);
	    }

	    if (distintasProvincias) {
	        costo += costoFijo;
	    }

	    return costo;
	}
}
