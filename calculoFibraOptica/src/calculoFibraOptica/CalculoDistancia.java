package calculoFibraOptica;

public class CalculoDistancia {
	private static final double radioDeLaTierra = 6371.0;
	
	public static double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
		double distanciaLatitud = Math.toRadians(latitud2 - latitud1 );
		double distanciaLongitud = Math.toRadians(longitud2 - longitud1 );
		
		double radioLatitud1 = Math.toRadians(latitud1);
		double radioLatitud2 = Math.toRadians(latitud2);
		
		double harversen = Math.pow(Math.sin(distanciaLatitud/2), 2) + Math.cos(radioLatitud1) * Math.cos(radioLatitud2) * Math.pow(Math.sin(distanciaLongitud/2), 2);
		
		double anguloCentral = 2 * Math.atan2(Math.sqrt(harversen), Math.sqrt(1 - harversen));
		
		return radioDeLaTierra * anguloCentral;
	}
	
	public static double calcularCosto(Localidad l1, Localidad l2, double costoPorKm, double porcentajeAumento, double costoFijoProvincias) {
		double distancia = CalculoDistancia.calcularDistancia(l1.getLatitud(), l1.getLongitud(), l2.getLatitud(), l2.getLongitud());

		double costo = distancia * costoPorKm;

	    // Se incrementa si supera 300km
	    if (distancia > 300) {
	        costo += costo * (porcentajeAumento / 100);
	    }

	    // Si es provincia su costo es fijo
	    if (!l1.getProvincia().equalsIgnoreCase(l2.getProvincia())) {
	        costo += costoFijoProvincias;
	    }

	    return costo;
	}
	
	
}