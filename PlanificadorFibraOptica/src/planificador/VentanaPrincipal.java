package planificador;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
	private JFrame frame;
	
	private JTextField txtNombre;
	private JTextField txtProvincia; 
	private JTextField txtLatitud;
	private JTextField txtLongitud;
    private JTextField txtCostoKm;
    private JTextField txtPorcentaje;
    private JTextField txtCostoProvincia;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VentanaPrincipal window = new VentanaPrincipal();
	            window.frame.setVisible(true);
	        } catch (Exception e) { e.printStackTrace(); }
	    });
    }

    public VentanaPrincipal() {
        initialize();
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Planificador Fibra Óptica");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        
        // DATOS DE FORMULARIO
        txtNombre = new JTextField();
        txtProvincia = new JTextField();
        txtLatitud = new JTextField();
        txtLongitud = new JTextField();
        
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10)); // cant filas, cant columnas, espacio horizontal, espacio vertical

        panelForm.add(new JLabel("Nombre", SwingConstants.RIGHT));
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Provincia", SwingConstants.RIGHT));
        panelForm.add(txtProvincia);

        panelForm.add(new JLabel("Latitud", SwingConstants.RIGHT));
        panelForm.add(txtLatitud);

        panelForm.add(new JLabel("Longitud", SwingConstants.RIGHT));
        panelForm.add(txtLongitud);

        JButton btnAgregar = new JButton("Agregar");
        panelForm.add(new JLabel());
        panelForm.add(btnAgregar);

        // CENTRO LOS DATOS EN EL PANEL
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.add(panelForm);

        frame.add(contenedor, BorderLayout.CENTER);
    }
}
