package calculoFibraOptica;

import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    JFrame frame;
    
    private JTextField txtNombre;
    private JTextField txtProvincia; 
    private JTextField txtLatitud;
    private JTextField txtLongitud;
    
    // Estos ya los tenías declarados, ahora los vamos a inicializar
    private JTextField txtCostoKm;
    private JTextField txtPorcentaje;
    private JTextField txtCostoProvincia;
    
    private JButton btnAgregar;
    private JButton btnLimpiar;
    private JButton btnVerClientes;
    private JButton btnCalcularRed; // <-- NUEVO BOTÓN
    
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
        
        // CAMPOS DE LOCALIDAD
        txtNombre = new JTextField(15);
        txtProvincia = new JTextField(15);
        txtLatitud = new JTextField(15);
        txtLongitud = new JTextField(15);
        
        // CAMPOS DE COSTOS (Les puse valores por defecto para que sea más fácil probar)
        txtCostoKm = new JTextField("10", 15);
        txtPorcentaje = new JTextField("20", 15);
        txtCostoProvincia = new JTextField("500", 15);
        
        // BOTONES
        btnAgregar = new JButton("Agregar Localidad");
        btnLimpiar = new JButton("Limpiar");
        btnVerClientes = new JButton("Ver clientes registrados");
        btnCalcularRed = new JButton("Calcular Red (AGM)"); // <-- INICIALIZADO

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnLimpiar);
        
        // LABEL ARCHIVO
        lblRutaArchivo = new JLabel(" ");
        lblRutaArchivo.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblRutaArchivo.setForeground(Color.DARK_GRAY);
        
        // PANEL PRINCIPAL
        JPanel panelForm = new JPanel(new BorderLayout(10,10));

        // ¡IMPORTANTE! Pasamos de 7 a 11 filas en el GridLayout
        JPanel panelLabels = new JPanel(new GridLayout(11, 1, 5, 5));
        JPanel panelInputs = new JPanel(new GridLayout(11, 1, 5, 5));

        // --- FILAS DE CARGA DE DATOS ---
        panelLabels.add(new JLabel("Nombre:"));
        panelInputs.add(txtNombre);

        panelLabels.add(new JLabel("Provincia:"));
        panelInputs.add(txtProvincia);

        panelLabels.add(new JLabel("Latitud:"));
        panelInputs.add(txtLatitud);

        panelLabels.add(new JLabel("Longitud:"));
        panelInputs.add(txtLongitud);

        panelLabels.add(new JLabel()); // Espacio vacío
        panelInputs.add(panelBotones);

        // --- FILAS DE PARÁMETROS DE COSTO ---
        panelLabels.add(new JLabel("Costo por Km ($):"));
        panelInputs.add(txtCostoKm);

        panelLabels.add(new JLabel("Aumento > 300Km (%):"));
        panelInputs.add(txtPorcentaje);

        panelLabels.add(new JLabel("Costo Fijo Dist. Provincia ($):"));
        panelInputs.add(txtCostoProvincia);

        // --- FILAS DE ACCIONES FINALES ---
        panelLabels.add(new JLabel()); // Espacio vacío
        panelInputs.add(btnVerClientes);

        panelLabels.add(new JLabel()); // Espacio vacío
        panelInputs.add(btnCalcularRed);

        panelLabels.add(new JLabel()); // Espacio vacío
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
        btnCalcularRed.addActionListener(e -> onCalcularRed()); // <-- EVENTO NUEVO
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
    
    // Método que captura los precios y se los manda al Controlador
    private void onCalcularRed() {
        controlador.procesarCalculoRed(txtCostoKm.getText(), txtPorcentaje.getText(), txtCostoProvincia.getText());
    }
}