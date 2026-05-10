package calculoFibraOptica;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalculoDistanciaTest {

	@Test
	public void testCalcularDistanciaHaversine() {
		double dist = CalculoDistancia.calcularDistancia(-34.6037, -58.3816, -31.4135, -64.1811);
		
		assertEquals(646.0, dist, 5.0); 
	}

	@Test
	public void testCalcularCosto_SinRecargos() {
		Localidad l1 = new Localidad("Caballito", "CABA", -34.6166, -58.4500);
		Localidad l2 = new Localidad("Retiro", "CABA", -34.5911, -58.3750); 
		
		double costoPorKm = 10.0;
		double porcentajeAumento = 50.0;
		double costoFijo = 1000.0;
		
		double costoCalculado = CalculoDistancia.calcularCosto(l1, l2, costoPorKm, porcentajeAumento, costoFijo);
		double distanciaReal = CalculoDistancia.calcularDistancia(l1.getLatitud(), l1.getLongitud(), l2.getLatitud(), l2.getLongitud());
		
		assertEquals(distanciaReal * costoPorKm, costoCalculado, 0.01);
	}

	@Test
	public void testCalcularCosto_ConAmbosRecargos() {
		Localidad l1 = new Localidad("Buenos Aires", "Buenos Aires", -34.6037, -58.3816);
		Localidad l2 = new Localidad("Cordoba", "Cordoba", -31.4135, -64.1811); 
		
		double costoPorKm = 10.0;
		double porcentajeAumento = 50.0; // 50% de recargo
		double costoFijo = 1000.0; // $1000 extra
		
		double costoCalculado = CalculoDistancia.calcularCosto(l1, l2, costoPorKm, porcentajeAumento, costoFijo);
		double distanciaReal = CalculoDistancia.calcularDistancia(l1.getLatitud(), l1.getLongitud(), l2.getLatitud(), l2.getLongitud());
		
		double costoEsperado = (distanciaReal * costoPorKm);
		costoEsperado += (costoEsperado * 0.50); // Aumento del 50%
		costoEsperado += costoFijo; // Aumento por cruzar provincia
		
		assertEquals(costoEsperado, costoCalculado, 0.01);
	}
}
