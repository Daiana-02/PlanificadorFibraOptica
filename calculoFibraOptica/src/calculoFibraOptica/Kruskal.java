package calculoFibraOptica;
import java.util.ArrayList;

public class Kruskal {
	private int[] A;
	
	public Grafo obtenerAGM(Grafo grafo) {
		int n = grafo.cantidadVertices();
		// estructura Union-Find
        A = new int[n];
        // cada vértice comienza siendo su propia raíz
        for (int i = 0; i < n; i++) {
            A[i] = i; 
        }
        
        // T = (V, ET)
        Grafo arbol = new Grafo(n);
        
        // ET := ∅
        ArrayList<Arista> ET = new ArrayList<>();
        
        // E
        ArrayList<Arista> aristas = grafo.obtenerAristas();

        // ordenar por costo mínimo
        ArrayList<Arista> usadas = new ArrayList<>();
        
        // mientras i ≤ n − 1
        while (ET.size() < n - 1) {
        	// elegir e ∈ E tal que l(e) sea mínima
        	Arista e = obtenerMenorArista(aristas, usadas);
        	usadas.add(e);
        	
        	int origen = e.getOrigen();
            int destino = e.getDestino();
            
            // que no formen circuito
            if (!find(origen, destino)) {
                // ET := ET ∪ {e}
                arbol.agregarArista(origen, destino, e.getCosto());
                ET.add(e);
                // unir componentes
                union(origen, destino);
            }
        }
        
        // retornar T = (V, ET)
        return arbol;
	}
	
	private Arista obtenerMenorArista( ArrayList<Arista> aristas, ArrayList<Arista> usadas) {
	    Arista menor = null;
	    for (Arista a : aristas) {
	        if (!usadas.contains(a)) {
	            if (menor == null || a.getCosto() < menor.getCosto()) {
	                menor = a;
	            }
	        }
	    }
	    return menor;
	}
	
	private int root(int i) {
	    while (A[i] != i) {
	        i = A[i];
	    }
	    return i;
	}
	
	private boolean find(int i, int j) {
	    return root(i) == root(j);
	}
	
	private void union(int i, int j) {
	    int ri = root(i);
	    int rj = root(j);
	    A[ri] = rj;
	}
}