/**
 * 
 */
/**
 * 
 */
module calculoFibraOptica {
	requires java.desktop;
	requires com.google.gson;
	requires org.junit.jupiter.api;
	requires JMapViewer;
	requires junit;
	exports calculoFibraOptica;
	opens calculoFibraOptica to com.google.gson;
}