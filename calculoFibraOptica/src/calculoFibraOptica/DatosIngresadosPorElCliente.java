package calculoFibraOptica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DatosIngresadosPorElCliente {

	private static final double latitudMinima = -90.0;
	private static final double latitudMaxima = 90.0;
	private static final double longitudMinima = -180.0;
	private static final double longitudMaxima = 180.0;
	
	private String nombre;
	private String provincia;
	private double latitud;
	private double longitud;
	
	static final String archivo_json = "clientes.json";
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private static final List<DatosIngresadosPorElCliente> historial = new ArrayList<>();
	
	public DatosIngresadosPorElCliente(String nombre, String provincia, double latitud, double longitud) {
		definirNombre(nombre);       
		definirProvincia(provincia); 
		definirLatitud(latitud);
		definirLongitud(longitud);
		
		historial.add(this);
	}

	public static void cargarDesdeJson() {
		File archivo = new File(archivo_json);
		if (!archivo.exists() || archivo.length() == 0) return;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo_json));
			Type tipo = new TypeToken<List<DatosIngresadosPorElCliente>>(){}.getType();
			List<DatosIngresadosPorElCliente> cargados = gson.fromJson(br, tipo);
			
			if(cargados != null) {
				historial.clear();
				historial.addAll(cargados);
			}
			
			br.close();
		} catch (IOException e) {
			System.err.println("No se pudo cargar el historial: " + e.getMessage());
		}
	}

	public static void guardarEnJson() {
		String json = gson.toJson(historial);
		
		try {
			FileWriter writer = new FileWriter(archivo_json);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<DatosIngresadosPorElCliente> obtenerHistorial(){
		return Collections.unmodifiableList(historial);
	}
	
	public void definirNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre del cliente no puede ser vacío");
		}
		this.nombre = nombre.trim();
	}
	
	public void definirProvincia(String provincia) {
		if(provincia == null || provincia.isBlank()) {
			throw new IllegalArgumentException("La provincia no puede estar vacía");
		}
		this.provincia = provincia.trim();
	}
	
	public void definirLatitud(double latitud) {
		if(latitud < latitudMinima || latitud > latitudMaxima) {
			throw new IllegalArgumentException(String.format("Latitud invalida: %.6f. Debe estar entre %.1f y %.1f ",latitud, latitudMinima, latitudMaxima));
		}
		this.latitud = latitud;
	}
	
	public void definirLongitud(double longitud) {
		if(longitud < longitudMinima || longitud > longitudMaxima) {
			throw new IllegalArgumentException(String.format("longitud invalida: %.6f. Debe estar entre %.1f y %.1f" ,longitud, longitudMinima, longitudMaxima));
		}
		this.longitud = longitud;
	}
	
	public static void limpiarHistorial() {
		historial.clear();
	}
	
	public static List<DatosIngresadosPorElCliente> buscarPorProvincia(String provincia){
		List<DatosIngresadosPorElCliente> buscado = new ArrayList<>();
		for (DatosIngresadosPorElCliente provincias : historial) {
			if(provincias.obtenerProvincia().equalsIgnoreCase(provincia)) {
				buscado.add(provincias);
			}
		}
		return buscado;
	}
	
	String obtenerProvincia() {
		return provincia;
	}
	
	String obtenerNombre() {
		return nombre;
	}
	
	double obtenerLatitud() {
		return latitud;
	}
	
	double obtenerLongitud() {
		return longitud;
	}
	
	@Override
	public String toString() {
		return String.format("DatosCliente { nombre='%s, provincia='%s, latitud = %.6f, longitud =%.6f }", nombre, provincia, latitud, longitud);
	}
	
	@Override
	public boolean equals(Object objeto) {
		if (this == objeto)
			return true;
		if (!(objeto instanceof DatosIngresadosPorElCliente)) return false;
		DatosIngresadosPorElCliente datosCliente = (DatosIngresadosPorElCliente) objeto;
		return Double.compare(datosCliente.latitud, latitud) == 0 && Double.compare(datosCliente.longitud, longitud) == 0 && Objects.equals(nombre, datosCliente.nombre)
				&& Objects.equals(datosCliente.provincia, provincia);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(nombre, provincia, latitud, longitud);
	}
}