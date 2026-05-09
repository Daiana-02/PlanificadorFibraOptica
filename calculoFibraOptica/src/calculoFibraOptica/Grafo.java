package calculoFibraOptica;

import java.util.ArrayList;

public class Grafo {
	private double [][] A;	// representamos el grafo por su matriz de adyacencia
	
	// la cantidad de vertices esta predeterminada desde el constructor
	public Grafo(int cantidadVertices) {
		A = new double [cantidadVertices][cantidadVertices];
		
		for (int i = 0; i < cantidadVertices; i++) {
            for (int j = 0; j < cantidadVertices; j++) {
                A[i][j] = -1;
            }
        }
	}
	
	public ArrayList<Arista> obtenerAristas() {
	    ArrayList<Arista> aristas = new ArrayList<>();
	    for (int i = 0; i < A.length; i++) {
	        for (int j = i + 1; j < A.length; j++) {
	            if (A[i][j] != -1) {
	                aristas.add(new Arista(i, j, A[i][j]));
	            }
	        }
	    }
	    return aristas;
	}
	
	public void agregarArista(int i, int j, double costo) {
		A[i][j] = costo;
		A[j][i] = costo;
	}
	
	// Informa si existe la arista especificada
	public boolean existeArista(int i, int j) {
		return A[i][j] != -1; // no existe conexio va a ser igual a -1
	}
	
	// Obtengo el costo
	public double obtenerCosto(int i, int j) {
        return A[i][j];
    }
	
	// Cantidad total de vertices
	public int cantidadVertices() {
        return A.length;
    }
}
