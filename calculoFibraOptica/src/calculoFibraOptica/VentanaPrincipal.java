package calculoFibraOptica;

import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
	private JTextField txtNombre;
	private JTextField txtProvincia; 
	private JTextField txtLatitud;
	private JTextField txtLongitud;
	
    private JTextField txtCostoKm;
    private JTextField txtPorcentaje;
    private JTextField txtCostofijo;
    
    private JButton btnAgregar;
    private JButton btnLimpiar;
    private JButton btnVerClientes;
    private JButton btnCalcularAGM;
    
    private JLabel lblRutaArchivo;
     
    private Controlador controlador;
    
    public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VentanaPrincipal window = new VentanaPrincipal();
	            window.setVisible(true);
	        } catch (Exception e) { e.printStackTrace(); }
	    });
    }

    public VentanaPrincipal() {
    	super("Registro de Clientes");
        initialize();
        controlador = new Controlador(this);
        lblRutaArchivo.setText("Archivo: " + controlador.obtenerRutaArchivo());
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private void initialize() {
        setTitle("Planificador Fibra Óptica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // CAMPOS
        txtNombre = new JTextField(15);
        txtProvincia = new JTextField(15);
        txtLatitud = new JTextField(15);
        txtLongitud = new JTextField(15);
        txtCostoKm = new JTextField(15);
        txtPorcentaje = new JTextField(15);
        txtCostofijo = new JTextField(15);
        
        // BOTONES
        btnAgregar = new JButton("Agregar");
        btnLimpiar = new JButton("Limpiar");
        btnVerClientes = new JButton("Ver clientes registrados");
        btnCalcularAGM = new JButton("Calcular AGM");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCalcularAGM);
        
        // LABEL ARCHIVO
        lblRutaArchivo = new JLabel(" ");
        lblRutaArchivo.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblRutaArchivo.setForeground(Color.DARK_GRAY);
        
        // PANEL PRINCIPAL
        JPanel panelForm = new JPanel(new BorderLayout(10,10));

        JPanel panelLabels = new JPanel(new GridLayout(10,1,5,5));
        JPanel panelInputs = new JPanel(new GridLayout(10,1,5,5));

        // FILAS
        panelLabels.add(new JLabel("Nombre"));
        panelInputs.add(txtNombre);

        panelLabels.add(new JLabel("Provincia"));
        panelInputs.add(txtProvincia);

        panelLabels.add(new JLabel("Latitud"));
        panelInputs.add(txtLatitud);

        panelLabels.add(new JLabel("Longitud"));
        panelInputs.add(txtLongitud);
        
        panelLabels.add(new JLabel("Costo por Km"));
        panelInputs.add(txtCostoKm);
        
        panelLabels.add(new JLabel("Porcentaje"));
        panelInputs.add(txtPorcentaje);

        panelLabels.add(new JLabel("Costo fijo"));
        panelInputs.add(txtCostofijo);
        
        panelLabels.add(new JLabel());
        panelInputs.add(panelBotones);

        panelLabels.add(new JLabel());
        panelInputs.add(btnVerClientes);

        panelLabels.add(new JLabel());
        panelInputs.add(lblRutaArchivo);
        
        // UNIR
        panelForm.add(panelLabels, BorderLayout.WEST);
        panelForm.add(panelInputs, BorderLayout.CENTER);

        panelForm.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(panelForm, BorderLayout.CENTER);

        // EVENTOS
        btnAgregar.addActionListener(e -> onAgregar());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerClientes.addActionListener(e -> controlador.mostrarClientes());
        btnCalcularAGM.addActionListener(e -> { controlador.calcularAGM(txtCostoKm.getText(),txtPorcentaje.getText(),txtCostofijo.getText());});

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
    	txtCostoKm.setText("");
    	txtPorcentaje.setText("");
    	txtCostofijo.setText("");
    	txtNombre.requestFocusInWindow();
    }
    
}
