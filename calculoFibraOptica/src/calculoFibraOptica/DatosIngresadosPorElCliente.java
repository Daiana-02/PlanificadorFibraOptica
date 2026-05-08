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
	
	
	
	/*
	public static void cargarDesdeJson() {
	    File archivo = new File(archivo_json);
	    if (!archivo.exists() || archivo.length() == 0) return;
	    
	    try {
	        // 1. Abre el archivo usando FileReader y BufferedReader (estilo cátedra)
	        BufferedReader br = new BufferedReader(new FileReader(archivo_json));
	        
	        // 2. Se define el tipo de dato (Lista) obligatorio para colecciones
	        Type tipo = new TypeToken<List<DatosIngresadosPorElCliente>>(){}.getType();
	        
	        // 3. Se lee y convierte el JSON a la lista de Java
	        List<DatosIngresadosPorElCliente> cargados = gson.fromJson(br, tipo);
	        
	        if(cargados != null) {
	            historial.clear();
	            historial.addAll(cargados);
	        }
	        
	        // 4. Se cierra el archivo manualmente
	        br.close();
	        
	    } catch (Exception e) {
	        System.err.println("No se pudo cargar el historial: " + e.getMessage());
	    }
	}
	*/
	
	/*
	 * 1. FileReader vs InputStreamReader(StandardCharsets.UTF_8) (Problema de
	 * codificación): La cátedra usa FileReader, que toma la codificación por
	 * defecto de tu computadora. La IA usó InputStreamReader forzando UTF-8 para
	 * asegurar que los acentos y las eñes se lean bien sin importar en qué
	 * computadora se abra el archivo.
	 * 
	 * 2. Cierre manual vs Try-with-resources (Manejo de errores): La cátedra abre
	 * el archivo y requiere que pongas br.close() al final. La IA puso el archivo
	 * entre paréntesis al lado del try; esto hace que Java lo cierre
	 * automáticamente de forma segura, incluso si el programa se rompe por la
	 * mitad.
	 * 
	 * 3. Objeto simple vs TypeToken (Lectura de Listas): La cátedra usa .class para
	 * leer un objeto único (ej. ArchivoJSON.class). La IA introdujo TypeToken
	 * porque es la única forma que tiene la librería Gson de entender que debe
	 * armar una List llena de objetos DatosIngresadosPorElCliente
	 */

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
	
	/*
	 * Aca los profes lo hicieron distinto a: public static void guardarEnJson() ,
	 * menos eficiente(Version como lo pensaron los profes en video:
	 * Resumen:convertir primero a String, usar el FileWriter clásico y hacer el
	 * .close() a mano
	 * 
	 * public static void guardarEnJson() { 
	 * // 1. Convierte la lista 'historial' a un String con formato JSON 
	 * String json = gson.toJson(historial);
	 * 
	 * try { // 2. Abre el archivo usando la forma tradicional vista en clase
	 * FileWriter writer = new FileWriter(archivo_json);
	 * 
	 * // 3. Escribe todo el String de una vez 
	 * writer.write(json);
	 * 
	 * // 4. Cierra el archivo manualmente (muy importante en este formato)
	 * writer.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * 
	 * 
	 * 1.La imagen: Primero convierte todo el objeto a un String gigante en la memoria
	 * RAM (String json = gson.toJson(this);) y luego escribe ese texto en el
	 * archivo. Si la lista de clientes fuera enorme, esto consumiría muchísima
	 * memoria innecesariamente.
	 * 
	 * 
	 * 
	 * 2.Tu código: Usa un enfoque de streaming. Al pasarle el writer directamente a
	 * Gson (gson.toJson(historial, writer);), la librería va traduciendo los
	 * objetos y escribiéndolos en el disco duro "al vuelo", por partes. Es
	 * muchísimo más eficiente para listas largas.
	 * 
	 * 2. Codificación de caracteres (El problema de la "ñ" y las tildes) La imagen:
	 * Utiliza FileWriter. Esta clase antigua usa por defecto la codificación del
	 * sistema operativo donde se ejecute. Si un compañero usa Windows y otro Linux,
	 * palabras como "Córdoba" o "Tucumán" pueden guardarse con caracteres raros
	 * (como un rombo con un signo de interrogación) al pasar el archivo de una PC a
	 * otra.
	 * 
	 * Tu código: Utiliza OutputStreamWriter especificando explícitamente
	 * StandardCharsets.UTF_8. Esto garantiza que los acentos y las eñes se van a
	 * guardar y leer perfectamente en cualquier computadora.
	 * 
	 * 
	 * 
	 * 3. El cierre seguro del archivo (Gestión de recursos) La imagen: Tiene el
	 * writer.close(); dentro del bloque try, pero escrito de forma tradicional. Si
	 * ocurre un error justo en la línea writer.write(json);, el programa saltará al
	 * catch y el archivo nunca se cerrará. Esto puede dejar el archivo bloqueado o
	 * corrupto.
	 * 
	 * Tu código: Utiliza una estructura moderna de Java llamada Try-with-resources
	 * (try (Writer writer = ...)). Esto le dice a Java:
	 * "pase lo que pase, haya error o no, cerrá el archivo automáticamente al terminar el bloque"
	 * . Es una excelente práctica de programación.
	 */
	//1.Strem por streaming (eficiencia)
	//2.FileWriter por OutputStreamWriter(StandardCharsets.UTF_8) acentos de Windows a Linux
	//3.writer.close() por Try-with-resources (cerrar a prueba de errores)
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
