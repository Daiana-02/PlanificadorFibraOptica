package calculoFibraOptica;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import javax.swing.JFrame;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

public class VentanaMapa extends JFrame {
	private JMapViewer mapa;

    public VentanaMapa(ArrayList<Localidad> localidades, ArrayList<Arista> conexiones) {
        setTitle("Mapa AGM");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        mapa = new JMapViewer();
        add(mapa, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        // NODOS
        for (Localidad l : localidades) {
            double lat = l.getLatitud();
            double lon = l.getLongitud();
            mapa.addMapMarker(new MapMarkerDot(l.getNombre(),new Coordinate(lat, lon)));
        }
        // CENTRAR MAPA
        if (!localidades.isEmpty()) {
            Localidad l = localidades.get(0);
            mapa.setDisplayPosition(
                new Coordinate(l.getLatitud(), l.getLongitud()),
                12
            );
        }
        // ARISTAS
        for (Arista a : conexiones) {
        	Localidad origen = localidades.get(a.getOrigen());
        	Localidad destino = localidades.get(a.getDestino());
            Coordinate coord1 = new Coordinate(origen.getLatitud(),origen.getLongitud());
            Coordinate coord2 = new Coordinate(destino.getLatitud(),destino.getLongitud());
            MapPolygonImpl linea = new MapPolygonImpl(Arrays.asList(coord1, coord2, coord2));
            mapa.addMapPolygon(linea);
        }

        setVisible(true);
    }
}
