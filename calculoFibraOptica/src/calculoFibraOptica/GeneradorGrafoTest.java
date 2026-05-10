package calculoFibraOptica;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class GeneradorGrafoTest {

	@Test
	public void testGenerarGrafoCompleto() {
		ArrayList<Localidad> locs = new ArrayList<>();
		locs.add(new Localidad("CABA", "CABA", -34.6, -58.4));
		locs.add(new Localidad("La Plata", "Buenos Aires", -34.9, -57.9));
		locs.add(new Localidad("Rosario", "Santa Fe", -32.9, -60.6));

		Grafo g = GeneradorGrafo.generarGrafo(locs, 10.0, 20.0, 500.0);

		assertEquals(3, g.cantidadVertices());
		
		assertEquals(3, g.obtenerAristas().size());
		
		assertTrue(g.existeArista(0, 1)); // CABA - La Plata
		assertTrue(g.existeArista(1, 2)); // La Plata - Rosario
		assertTrue(g.existeArista(0, 2)); // CABA - Rosario
	}
}
