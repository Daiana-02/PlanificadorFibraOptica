package calculoFibraOptica;

import java.util.ArrayList;

public class GeneradorGrafo {
	public static Grafo generarGrafo(
            ArrayList<Localidad> localidades,
            double costoPorKm,
            double porcentajeAumento,
            double costoFijoProvincia) {

        Grafo grafo = new Grafo(localidades.size());

        for (int i = 0; i < localidades.size(); i++) {

            for (int j = i + 1; j < localidades.size(); j++) {

                Localidad origen = localidades.get(i);
                Localidad destino = localidades.get(j);

                double costo = CalculoDistancia.calcularCosto(
                        origen,
                        destino,
                        costoPorKm,
                        porcentajeAumento,
                        costoFijoProvincia
                );

                grafo.agregarArista(i, j, costo);
            }
        }

        return grafo;
    }
}
