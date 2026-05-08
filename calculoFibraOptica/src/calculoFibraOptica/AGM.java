package calculoFibraOptica;

import java.util.ArrayList;
import java.util.List;

public class AGM {

    public static List<Conexion> calcularPrim(List<DatosIngresadosPorElCliente> localidades, double costoPorKm, double porcentajeAumento, double costoFijoProvincia) {
        
        List<Conexion> redOptima = new ArrayList<>();
        
        // Si hay menos de 2 localidades, no hay conexiones que hacer
        if (localidades == null || localidades.size() < 2) {
            return redOptima;
        }

        // Listas para separar las localidades que ya conectamos de las que faltan
        List<DatosIngresadosPorElCliente> visitados = new ArrayList<>();
        List<DatosIngresadosPorElCliente> noVisitados = new ArrayList<>(localidades);

        // 1. Empezamos por la primera localidad (arbitrario)
        visitados.add(noVisitados.remove(0));

        // 2. Mientras queden localidades sin conectar al árbol
        while (!noVisitados.isEmpty()) {
            Conexion conexionMasBarata = null;
            double costoMinimo = Double.MAX_VALUE;
            DatosIngresadosPorElCliente proximaLocalidadAConectar = null;

            // 3. Revisamos todas las localidades ya conectadas...
            for (DatosIngresadosPorElCliente conectado : visitados) {
                // ... y calculamos el costo hacia TODAS las localidades que faltan
                for (DatosIngresadosPorElCliente candidato : noVisitados) {

                    // A. Calculamos la distancia en Km
                    double distancia = CalculoDistancia.calcularDistancia(
                            conectado.obtenerLatitud(), conectado.obtenerLongitud(),
                            candidato.obtenerLatitud(), candidato.obtenerLongitud()
                    );

                    // B. Verificamos si cruzan límite provincial
                    boolean distintaProvincia = !conectado.obtenerProvincia().equalsIgnoreCase(candidato.obtenerProvincia());

                    // C. Calculamos el PESO (Costo final)
                    double costo = CalculoDistancia.calcularCosto(distancia, costoPorKm, porcentajeAumento, costoFijoProvincia, distintaProvincia);

                    // D. Si es más barata que la mejor que teníamos, la guardamos
                    if (costo < costoMinimo) {
                        costoMinimo = costo;
                        conexionMasBarata = new Conexion(conectado, candidato, costo);
                        proximaLocalidadAConectar = candidato;
                    }
                }
            }

            // 4. Una vez encontrada la conexión más barata, la agregamos a la red oficial
            redOptima.add(conexionMasBarata);
            visitados.add(proximaLocalidadAConectar);
            noVisitados.remove(proximaLocalidadAConectar);
        }

        return redOptima;
    }
}