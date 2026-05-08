package calculoFibraOptica;

import org.junit.Test;
import calculoFibraOptica.DatosIngresadosPorElCliente;

public class DatosIngresadosPorElClienteTest {

    @Test(expected = IllegalArgumentException.class)
    public void instanciarCliente_NombreVacio_LanzaExcepcion() {
        // Se espera que esto "rompa" y lance la excepción programada en la clase
        new DatosIngresadosPorElCliente("", "Buenos Aires", -34.0, -58.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void instanciarCliente_LatitudFueraDeRango_LanzaExcepcion() {
        // La latitud máxima es 90, mandamos 100 para forzar el error
        new DatosIngresadosPorElCliente("Juan", "Cordoba", 100.0, -64.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void instanciarCliente_LongitudFueraDeRango_LanzaExcepcion() {
        // La longitud mínima es -180, mandamos -200
        new DatosIngresadosPorElCliente("Ana", "Mendoza", -32.0, -200.0);
    }
    
    // Si la creación es correcta, NO debe lanzar excepción y el test pasa verde
    @Test
    public void instanciarCliente_DatosCorrectos_CreaObjetoExitosamente() {
        new DatosIngresadosPorElCliente("Gabriel", "Tierra del Fuego", -54.8, -68.3);
    }
}
