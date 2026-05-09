package calculoFibraOptica;

import static org.junit.Assert.*;
import org.junit.Test;
import calculoFibraOptica.CalculoDistancia;

public class CalculoDistanciaTest {

    private static final double DELTA = 0.01; // Margen de error para comparar doubles

    @Test
    public void calcularCosto_MenosDe300KmYmismasProvincias_NoAplicaRecargos() {
        // Preparación (Arrange)
        double distancia = 100.0;
        double costoKm = 10.0;
        double porcentaje = 20.0;
        double costoFijo = 500.0;
        boolean distintasProvincias = false;

        // Ejecución (Act)
        double costoTotal = CalculoDistancia.calcularCosto(distancia, costoKm, porcentaje, costoFijo, distintasProvincias);

        // Comprobación (Assert)
        // 100km * $10 = $1000. No supera 300, no cambia provincia.
        assertEquals(1000.0, costoTotal, DELTA); 
    }

    @Test
    public void calcularCosto_MasDe300Km_AplicaPorcentajeDeAumento() {
        double distancia = 400.0; 
        double costoKm = 10.0;
        double porcentaje = 10.0; // 10% de aumento
        double costoFijo = 500.0;
        boolean distintasProvincias = false;

        double costoTotal = CalculoDistancia.calcularCosto(distancia, costoKm, porcentaje, costoFijo, distintasProvincias);

        // 400km * $10 = $4000. Más 10% de aumento ($400) = $4400.
        assertEquals(4400.0, costoTotal, DELTA);
    }

    @Test
    public void calcularCosto_MasDe300KmYDistintaProvincia_AplicaAmbosRecargos() {
        double distancia = 400.0; 
        double costoKm = 10.0;
        double porcentaje = 10.0; 
        double costoFijo = 500.0;
        boolean distintasProvincias = true;

        double costoTotal = CalculoDistancia.calcularCosto(distancia, costoKm, porcentaje, costoFijo, distintasProvincias);

        // Costo base: $4000. Aumento 10%: +$400. Costo fijo provincia: +$500. Total = $4900.
        assertEquals(4900.0, costoTotal, DELTA);
    }
    
    @Test
    public void calcularDistancia_MismoPunto_RetornaCero() {
        double latitud = -34.6037;
        double longitud = -58.3816;
        
        double distancia = CalculoDistancia.calcularDistancia(latitud, longitud, latitud, longitud);
        
        assertEquals(0.0, distancia, DELTA);
    }
}