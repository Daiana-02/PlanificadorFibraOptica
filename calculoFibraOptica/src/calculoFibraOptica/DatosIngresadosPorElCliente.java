package calculoFibraOptica;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
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
		this.nombre = nombre;
		this.provincia = provincia;
		definirLatitud(latitud);
		definirLongitud(longitud);
		
		
		historial.add(this);
	}
	
	public DatosIngresadosPorElCliente() {		
	}
	
	public static void cargarDesdeJson() {
		File archivo = new File(archivo_json);
		if (!archivo.exists() || archivo.length() == 0) return;
		
		try (Reader reader = new InputStreamReader (new FileInputStream(archivo), StandardCharsets.UTF_8)) {
			Type tipo = new TypeToken<List<DatosIngresadosPorElCliente>>(){}.getType();
			List<DatosIngresadosPorElCliente> cargados = gson.fromJson(reader, tipo);
			if(cargados != null) {
				historial.clear();
				historial.addAll(cargados);
			}
		} catch (IOException e) {
			System.err.println("No se pudo cargar el historial: " + e.getMessage());
		}
	}
	
	public static void guardarEnJson() {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(archivo_json), StandardCharsets.UTF_8)){
			gson.toJson(historial, writer);
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
