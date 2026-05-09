package calculoFibraOptica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class VentanaClientes extends JDialog{

		private static final String[] columnas = {"Nombre","Provincia","Latitud","Longitud"};
		
		private final DefaultTableModel modeloTabla;
		
		public VentanaClientes(JFrame padre,List<DatosIngresadosPorElCliente> clientes) {
			super(padre, "Clientes registrados",true);
			modeloTabla = new DefaultTableModel(columnas,0) {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
		
	
		initialize();
		cargarDatos(clientes);
		pack();
		setMinimumSize(new Dimension(500,300));
		setLocationRelativeTo(padre);
		}
		
		private void initialize() {
			setLayout(new BorderLayout(10,10));
			JLabel lblTitulo = new JLabel("Clientes registrados", SwingConstants.CENTER);
			lblTitulo.setFont(new Font("SansSerif", Font.BOLD,15));
			lblTitulo.setBorder(BorderFactory.createEmptyBorder(12,0,4,0));
			add(lblTitulo, BorderLayout.NORTH);
			
			JTable tabla = new JTable(modeloTabla);
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			tabla.getTableHeader().setReorderingAllowed(false);
			tabla.setRowHeight(24);
			tabla.setFillsViewportHeight(true);
			
			JScrollPane scroll = new JScrollPane(tabla);
			scroll.setBorder(BorderFactory.createEmptyBorder(0, 12, 0,12));
			add(scroll, BorderLayout.CENTER);
			
			JButton btnCerrar = new JButton("Cerrar");
			btnCerrar.addActionListener(e -> dispose());
			
			JPanel panelSur = new JPanel();
			panelSur.setBorder(BorderFactory.createEmptyBorder(4,0,10,0));
			panelSur.add(btnCerrar);
			add(panelSur, BorderLayout.SOUTH);
			
		}
		
		private void cargarDatos(List<DatosIngresadosPorElCliente> clientes) {
			modeloTabla.setRowCount(0);
			for (DatosIngresadosPorElCliente c : clientes) {
				modeloTabla.addRow(new Object[] { c.obtenerNombre(), c.obtenerProvincia(), c.obtenerLatitud(), c.obtenerLongitud() });
			}
		}
		
}
