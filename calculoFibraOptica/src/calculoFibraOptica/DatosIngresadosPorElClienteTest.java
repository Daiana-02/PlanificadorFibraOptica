package calculoFibraOptica;

import static org.junit.Assert.*;
import org.junit.Test;

public class DatosIngresadosPorElClienteTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testNombreVacioLanzaError() {		
		new DatosIngresadosPorElCliente("", "CABA", -34.0, -58.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLatitudFueraDeRangoLanzaError() {
		new DatosIngresadosPorElCliente("A", "B", 100.0, -58.0); 
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLongitudFueraDeRangoLanzaError() {
		new DatosIngresadosPorElCliente("A", "B", -34.0, -200.0); 
	}

	@Test
	public void testCreacionValida() {
		DatosIngresadosPorElCliente d = new DatosIngresadosPorElCliente("Flores", "CABA", -34.6333, -58.4667);
		
		assertEquals("Flores", d.obtenerNombre());
		assertEquals("CABA", d.obtenerProvincia());
		assertEquals(-34.6333, d.obtenerLatitud(), 0.0001);
	}
}