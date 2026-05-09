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
	
	
	public static void main(String[] Argentina) {
		double[] buenosAires = {-34.6037, -58.3816};
		double[] cordoba = {-31.4135, -64.1811};
		double[] mendoza = {-32.8908, -68.8272};
		double[] salta = {-24.7859, -65.4117};
		double[] ushuaia = {-54.8019, -68.3030};
		
		System.out.println("Distancias entre provincias cHEEEEEEEEEE\n");
		System.out.println("Buenos Aires a Cordoba :" + calcularDistancia(buenosAires[0],buenosAires[1],cordoba[0],cordoba[1]));
		System.out.println("Buenos Aires a Mendoza :" + calcularDistancia(buenosAires[0],buenosAires[1],mendoza[0],mendoza[1]));
		System.out.println("Buenos Aires a Salta :" + calcularDistancia(buenosAires[0],buenosAires[1],salta[0],salta[1]));
		System.out.println("Buenos Aires a Ushuaia :" + calcularDistancia(buenosAires[0],buenosAires[1],ushuaia[0],ushuaia[1]));
		System.out.println("Cordoba a Mendoza :" + calcularDistancia(cordoba[0],cordoba[1],mendoza[0],mendoza[1]));
		System.out.println("Salta a Ushuaia :" + calcularDistancia(salta[0],salta[1],ushuaia[0],ushuaia[1]));
	}
}