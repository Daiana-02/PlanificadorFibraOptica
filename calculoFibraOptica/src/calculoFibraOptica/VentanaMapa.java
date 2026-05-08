package calculoFibraOptica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

public class VentanaMapa extends JFrame {
	
	private JMapViewer mapa;

	// El constructor recibe TODAS las localidades (para los puntos) y la RED ÓPTIMA (para las líneas)
	public VentanaMapa(List<DatosIngresadosPorElCliente> localidades, List<Conexion> redOptima) {
		super("Mapa de Red de Fibra Óptica (AGM)");
		setSize(800, 600);
		setLocationRelativeTo(null); // Centrar en la pantalla
		setLayout(new BorderLayout());

		mapa = new JMapViewer();
		// Desactivamos los controles por defecto para que se vea más limpio
		mapa.setZoomControlsVisible(false);
		add(mapa, BorderLayout.CENTER);

		// 1. DIBUJAR LOS NODOS (Localidades)
		for (DatosIngresadosPorElCliente loc : localidades) {
			Coordinate coord = new Coordinate(loc.obtenerLatitud(), loc.obtenerLongitud());
			// Usamos el nombre de la localidad para que aparezca la etiqueta en el mapa
			MapMarkerDot marker = new MapMarkerDot(loc.obtenerNombre(), coord);
			marker.getStyle().setBackColor(Color.BLUE);
			marker.getStyle().setColor(Color.BLACK);
			mapa.addMapMarker(marker);
		}

		// 2. DIBUJAR LAS ARISTAS (Conexiones de la red óptima)
		for (Conexion conexion : redOptima) {
			Coordinate origen = new Coordinate(conexion.getLocalidad1().obtenerLatitud(), conexion.getLocalidad1().obtenerLongitud());
			Coordinate destino = new Coordinate(conexion.getLocalidad2().obtenerLatitud(), conexion.getLocalidad2().obtenerLongitud());

			// Truco de JMapViewer para dibujar una línea: crear un polígono que va y vuelve (Origen -> Destino -> Origen)
			MapPolygonImpl linea = new MapPolygonImpl(Arrays.asList(origen, destino, origen));
			linea.setColor(Color.RED); // Líneas rojas para que resalten
			mapa.addMapPolygon(linea);
		}

		// 3. CENTRAR LA CÁMARA
		// Nos centramos en la primera localidad cargada, con un zoom alejado (nivel 5) para ver todo el país
		if (!localidades.isEmpty()) {
			Coordinate centro = new Coordinate(localidades.get(0).obtenerLatitud(), localidades.get(0).obtenerLongitud());
			mapa.setDisplayPosition(centro, 5); 
		}
	}
}