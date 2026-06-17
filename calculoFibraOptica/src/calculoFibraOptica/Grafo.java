package calculoFibraOptica;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
	private List<Localidad> localidades = new ArrayList<>();
	private List<Conexion> conexiones = new ArrayList<>();
	private static final double radioDeLaTierra = 6371.0;
	
	public void agregarLocalidad(Localidad vertice) {
		localidades.add(vertice);
	}
	
	public void generarAristas(Double costoPorKm, Double porcentajeExtra, Double costoFijo) {
		for (int origen = 0; origen < localidades.size(); origen++) {
			for (int destino = origen + 1; destino < localidades.size(); destino++) {

				Localidad locOrigen = localidades.get(origen);
				Localidad locDestino = localidades.get(destino);
				boolean mismaProvincia =locOrigen.obtenerProvincia().equals(locDestino.obtenerProvincia());
				
				Double distancia = calcularDistancia(locOrigen.obtenerLatitud(), locOrigen.obtenerLongitud(), locDestino.obtenerLatitud(), locDestino.obtenerLongitud());
				Double costo = calcularCosto(distancia, costoPorKm, porcentajeExtra, costoFijo, mismaProvincia);
				
				System.out.println(costo);
				conexiones.add(new Conexion(locOrigen, locDestino, costo));
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

	public void calcularArbolGeneradorMinimo() {
		List<Conexion> arbolGeneradorMinimo= new ArrayList<>();
	    Set<Localidad> visitados = new HashSet<>();
	    Double costoTotalGeneradorMinimo = 0.0;
	    Localidad inicio = localidades.get(0);
	    visitados.add(inicio);

	    
	    while (visitados.size() < localidades.size()) {

	        Conexion aristaConMenorPeso = null;
	        double menorPeso = Double.MAX_VALUE;

	       
	        for (Conexion c : conexiones) {
	            Localidad origen = c.obtenerVerticeOrigen();
	            Localidad destino = c.obtenerVerticeDestino();

	            boolean origenVisitado = visitados.contains(origen);
	            boolean destinoVisitado = visitados.contains(destino);

	           
	            if ((origenVisitado && !destinoVisitado) ||
	                (!origenVisitado && destinoVisitado)) {

	                if (c.obtenerPeso() < menorPeso) {
	                    menorPeso = c.obtenerPeso();
	                    costoTotalGeneradorMinimo = c.obtenerPeso();
	                    aristaConMenorPeso = c;
	                }
	            }
	        }

	        if (aristaConMenorPeso != null) {
	        	arbolGeneradorMinimo.add(aristaConMenorPeso);
	            if (visitados.contains(aristaConMenorPeso.obtenerVerticeOrigen())) {
	                visitados.add(aristaConMenorPeso.obtenerVerticeDestino());
	            } else {
	                visitados.add(aristaConMenorPeso.obtenerVerticeOrigen());
	            }
	        }
	    }
	}
}
