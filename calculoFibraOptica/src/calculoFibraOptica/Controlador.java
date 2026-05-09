package calculoFibraOptica;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Controlador {

	private final VentanaPrincipal vista;
	
	public Controlador(VentanaPrincipal vista) {
		this.vista = vista;
		DatosIngresadosPorElCliente.cargarDesdeJson();
	}
	
	public boolean procesarDatos(String nombre, String provincia, String latitudCadena, String longitudCadena) {
		if(estaVacio(nombre) || estaVacio(provincia) || estaVacio(latitudCadena) || estaVacio(longitudCadena)) {
			mostrarError("Todos los campos son obligatorios.");
			return false;
		}
		
		double latitud, longitud;
		try {
			latitud = Double.parseDouble(latitudCadena.replace(",",".").trim());
			longitud = Double.parseDouble(longitudCadena.replace(",",".").trim());
		} catch (NumberFormatException e) {
			mostrarError("Latitud y longitud deben ser numeros validos. \nEjemplo: -34.303722");
			return false;
		}
		
		DatosIngresadosPorElCliente cliente;
		try {
			cliente = new DatosIngresadosPorElCliente(nombre, provincia, latitud, longitud);
		} catch (IllegalArgumentException e) {
			mostrarError(e.getMessage());
			return false;
		}
		
		try {
			DatosIngresadosPorElCliente.guardarEnJson();
		} catch (Exception e) {
			mostrarError("No se pudo guardar el archivo:\n" + e.getMessage());
			return false;
		}
		
		mostrarInfo("Cliente guardado correctamente.\n" + cliente);
		return true;
	}
	
	private boolean estaVacio(String s) {
		return s == null || s.isBlank();
	}
	
	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(vista, mensaje, "Error de validacion", JOptionPane.ERROR_MESSAGE);
	}
	
	private void mostrarInfo(String mensaje) {
		JOptionPane.showMessageDialog(vista, mensaje, "Operacion exitosa", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public List<DatosIngresadosPorElCliente> obtenerHistorial(){
		return DatosIngresadosPorElCliente.obtenerHistorial();
	}
	
	public String obtenerRutaArchivo() {
		return new File("clientes.json").getAbsolutePath();
	}
	
	public void mostrarClientes() {
		List<DatosIngresadosPorElCliente> historial = DatosIngresadosPorElCliente.obtenerHistorial();
		if(historial.isEmpty()) {
			mostrarInfo("No hay clientes registrados todavia");
			return;
		}
		VentanaClientes ventana = new VentanaClientes(vista.frame, historial);
		ventana.setVisible(true);
	}
	
	public void procesarCalculoRed(String costoKmCadena, String porcentajeCadena, String costoProvinciaCadena) {
		try {
			double costoKm = Double.parseDouble(costoKmCadena.trim());
			double porcentaje = Double.parseDouble(porcentajeCadena.trim());
			double costoFijo = Double.parseDouble(costoProvinciaCadena.trim());
			
			List<DatosIngresadosPorElCliente> localidades = DatosIngresadosPorElCliente.obtenerHistorial();
			
			if (localidades.size() < 2) {
				mostrarError("Se necesitan al menos 2 localidades registradas para calcular la red.");
				return;
			}

			// 1. Calculamos la matemática pura (El Algoritmo de Prim)
			List<Conexion> redResultante = AGM.calcularPrim(localidades, costoKm, porcentaje, costoFijo);
			
			// 2. Calculamos el costo total para mostrarlo
			double costoTotal = 0;
			for(Conexion c : redResultante) {
				costoTotal += c.getCosto();
			}
			
			// 3. Mostramos el mensaje de éxito
			mostrarInfo(String.format("¡Red calculada con éxito!\nCosto Total: $%.2f", costoTotal));
			
			// 4. ABRIMOS EL MAPA PASÁNDOLE LOS DATOS
			VentanaMapa mapaVisual = new VentanaMapa(localidades, redResultante);
			mapaVisual.setVisible(true);
			
		} catch (NumberFormatException e) {
			mostrarError("Los parámetros de costo deben ser valores numéricos válidos.");
		}
	}
}
