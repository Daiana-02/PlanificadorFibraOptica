package calculoFibraOptica;

import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
	JFrame frame;
	
	private JTextField txtNombre;
	private JTextField txtProvincia; 
	private JTextField txtLatitud;
	private JTextField txtLongitud;
	
    private JTextField txtCostoKm;
    private JTextField txtPorcentaje;
    private JTextField txtCostoProvincia;
    
    private JButton btnAgregar;
    private JButton btnLimpiar;
    private JButton btnVerClientes;
    
    private JLabel lblRutaArchivo;
    
    private Controlador controlador;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VentanaPrincipal window = new VentanaPrincipal();
	            window.frame.setVisible(true);
	        } catch (Exception e) { e.printStackTrace(); }
	    });
    }

    public VentanaPrincipal() {
    	super("Registro de Clientes");
        initialize();
        controlador = new Controlador(this);
        lblRutaArchivo.setText("Archivo: " + controlador.obtenerRutaArchivo());
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Planificador Fibra Óptica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // CAMPOS
        txtNombre = new JTextField(15);
        txtProvincia = new JTextField(15);
        txtLatitud = new JTextField(15);
        txtLongitud = new JTextField(15);
        
        // BOTONES
        btnAgregar = new JButton("Agregar");
        btnLimpiar = new JButton("Limpiar");
        btnVerClientes = new JButton("Ver clientes registrados");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);
        
        // LABEL ARCHIVO
        lblRutaArchivo = new JLabel(" ");
        lblRutaArchivo.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblRutaArchivo.setForeground(Color.DARK_GRAY);
        
        // PANEL PRINCIPAL
        JPanel panelForm = new JPanel(new BorderLayout(10,10));

        JPanel panelLabels = new JPanel(new GridLayout(7,1,5,5));
        JPanel panelInputs = new JPanel(new GridLayout(7,1,5,5));

        // FILAS
        panelLabels.add(new JLabel("Nombre"));
        panelInputs.add(txtNombre);

        panelLabels.add(new JLabel("Provincia"));
        panelInputs.add(txtProvincia);

        panelLabels.add(new JLabel("Latitud"));
        panelInputs.add(txtLatitud);

        panelLabels.add(new JLabel("Longitud"));
        panelInputs.add(txtLongitud);

        panelLabels.add(new JLabel());
        panelInputs.add(panelBotones);

        panelLabels.add(new JLabel());
        panelInputs.add(btnVerClientes);

        panelLabels.add(new JLabel());
        panelInputs.add(lblRutaArchivo);
        
        // UNIR
        panelForm.add(panelLabels, BorderLayout.WEST);
        panelForm.add(panelInputs, BorderLayout.CENTER);

        panelForm.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame.add(panelForm, BorderLayout.CENTER);

        // EVENTOS
        btnAgregar.addActionListener(e -> onAgregar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerClientes.addActionListener(e -> controlador.mostrarClientes());
    }
    
    private void onAgregar() {
    	boolean exito = controlador.procesarDatos(txtNombre.getText(), txtProvincia.getText(), txtLatitud.getText(), txtLongitud.getText());
    	if (exito) limpiarFormulario();
    }
    
    private void limpiarFormulario() {
    	txtNombre.setText("");
    	txtProvincia.setText("");
    	txtLatitud.setText("");
    	txtLongitud.setText("");
    	txtNombre.requestFocusInWindow();
    }
    
}
