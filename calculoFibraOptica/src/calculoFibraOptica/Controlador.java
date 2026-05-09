package calculoFibraOptica;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//Aca se uso el tipo de archivo csv 
public class Controlador {
	private static final String archivo_CSV = "clientes.csv";
	private static final String separador = ",";
	private static final String cabecera_CSV = "Nombre,Provincia,Latitud,Longitud";
	
	private final VentanaPrincipal vista;
	
	public Controlador(VentanaPrincipal vista) {
		this.vista = vista;
		DatosIngresadosPorElCliente.cargarDesdeJson();
		inicializarArchivo();
	}
	
	public boolean procesarDatos(String nombre, String provincia, String latitudCadena, String longitudCadena) {
		if(estaVacio(nombre) || estaVacio(provincia) || estaVacio(latitudCadena) || estaVacio(longitudCadena )) {
			mostrarError("Todos los campos son obligatorios.");
			return false;
		}
		
		double latitud, longitud;
		try {
			latitud = Double.parseDouble(latitudCadena.replace(",",".").trim());
			longitud = Double.parseDouble(longitudCadena.replace(",",".").trim());
		} catch (NumberFormatException e) {
			mostrarError("Latitud y longitud deben ser numeros validos. \n" + "Ejemplo: -34.303722");
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
			guardarEnCSV(cliente);
			DatosIngresadosPorElCliente.guardarEnJson();
		} catch (IOException e) {
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
	
	private void inicializarArchivo() {
		File archivo = new File(archivo_CSV);
		if(!archivo.exists()) {
			try (BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(archivo,false), StandardCharsets.UTF_8))) {
				bw.write(cabecera_CSV);
				bw.newLine();
			} catch (IOException e) {
				mostrarError("No se pudo crear el archivo CSV>\n" + e.getMessage());
			}
		}
	}
	
	
	private void guardarEnCSV(DatosIngresadosPorElCliente cliente) throws IOException {
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo_CSV,true), StandardCharsets.UTF_8))) {
			String linea = String.join(separador, escaparCSV(cliente.obtenerNombre()), escaparCSV(cliente.obtenerProvincia()), String.valueOf(cliente.obtenerLatitud()),
					String.valueOf(cliente.obtenerLongitud()));
			bw.write(linea);
			bw.newLine();
		}
	}
	
	private String escaparCSV(String valor) {
		if (valor.contains(separador) || valor.contains("\"") || valor.contains("\n")) {
			return "\"" + valor.replace("\"","\"\"") + "\"";
		}
		return valor;
	}
	
	public List<DatosIngresadosPorElCliente> obtenerHistorial(){
		return DatosIngresadosPorElCliente.obtenerHistorial();
	}
	
	public String obtenerRutaArchivo() {
		return new File(archivo_CSV).getAbsolutePath();
	}
	
	public void mostrarClientes() {
		List<DatosIngresadosPorElCliente> historial = DatosIngresadosPorElCliente.obtenerHistorial();
		if(historial.isEmpty()) {
			mostrarInfo("No hay clientes registrados todavia");
			return;
		 }
		VentanaClientes ventana = new VentanaClientes(vista, historial);
		ventana.setVisible(true);
	}
	
	public void calcularAGM(String costoKmTexto, String porcentajeTexto, String costoFijoTexto) {
		if (costoKmTexto.isBlank() || porcentajeTexto.isBlank() || costoFijoTexto.isBlank()) {
			JOptionPane.showMessageDialog(vista,"Completa los campos: Costo por km, Porcentaje y Costo fijo");
		    return;
		}

		double costoKm = Double.parseDouble(costoKmTexto);
		double porcentaje = Double.parseDouble(porcentajeTexto);
		double costoFijo = Double.parseDouble(costoFijoTexto);

		ArrayList<Localidad> localidades = new ArrayList<>();
		for (DatosIngresadosPorElCliente d : DatosIngresadosPorElCliente.obtenerHistorial()) {
	        localidades.add(new Localidad(d.obtenerNombre(),d.obtenerProvincia(),d.obtenerLatitud(),d.obtenerLongitud()));
	    }

		// generar grafo completo
		Grafo grafo = GeneradorGrafo.generarGrafo(localidades,costoKm,porcentaje,costoFijo);
		Kruskal kruskal = new Kruskal(); // KRUSKAL
		Grafo arbol = kruskal.obtenerAGM(grafo); // AGM
		ArrayList<Arista> conexiones = arbol.obtenerAristas(); // conexiones del AGM
		double costoTotal = 0;
		for (Arista a : conexiones) {
		    costoTotal += a.getCosto();
		}
		String mensaje =
		        "=== PARAMETROS DEL CALCULO ===\n\n" +
		        "Costo por kilometro: $" + costoKm + "\n" +
		        "Porcentaje de aumento (+300km): " + porcentaje + "%\n" +
		        "Costo fijo entre provincias: $" + costoFijo + "\n\n" +
		        "=== RESULTADO ===\n\n" +
		        "Cantidad de conexiones: " + conexiones.size() + "\n" +
		        "Costo total de la red: $" + String.format("%.2f", costoTotal);

		JOptionPane.showMessageDialog(vista, mensaje);
		VentanaMapa ventana = new VentanaMapa(localidades, conexiones);
		ventana.setVisible(true);
	}
}
