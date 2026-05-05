package calculoFibraOptica;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

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
        frame.setSize(500, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());
        
        // DATOS DE FORMULARIO
        txtNombre = new JTextField();
        txtProvincia = new JTextField();
        txtLatitud = new JTextField();
        txtLongitud = new JTextField();
        
        JPanel panelForm = new JPanel(new GridLayout(7,2,10, 10)); // cant filas, cant columnas, espacio horizontal, espacio vertical

        panelForm.add(new JLabel("Nombre", SwingConstants.RIGHT));
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Provincia", SwingConstants.RIGHT));
        panelForm.add(txtProvincia);

        panelForm.add(new JLabel("Latitud", SwingConstants.RIGHT));
        panelForm.add(txtLatitud);

        panelForm.add(new JLabel("Longitud", SwingConstants.RIGHT));
        panelForm.add(txtLongitud);

        btnAgregar = new JButton("Agregar");
        btnLimpiar = new JButton("Limpiar");
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER,8,0));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);
        panelForm.add(new JLabel());
        panelForm.add(panelBotones);
        
        btnVerClientes = new JButton("Ver clientes registrados");
        panelForm.add(new JLabel());
        panelForm.add(btnVerClientes);
        
        lblRutaArchivo = new JLabel(" ");
        lblRutaArchivo.setFont(new Font("Monospaced", Font.PLAIN,10));
        lblRutaArchivo.setForeground(Color.DARK_GRAY);
        panelForm.add(new JLabel());
        panelForm.add(lblRutaArchivo);
        
        btnAgregar.addActionListener(e -> onAgregar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerClientes.addActionListener(e -> controlador.mostrarClientes());
        // CENTRO LOS DATOS EN EL PANEL
        panelForm.setBorder(BorderFactory.createEmptyBorder(16,20,16,20));
        frame.add(panelForm, BorderLayout.CENTER);

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
