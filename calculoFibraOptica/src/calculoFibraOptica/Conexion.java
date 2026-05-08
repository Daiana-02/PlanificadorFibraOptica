package calculoFibraOptica;

public class Conexion {
    private DatosIngresadosPorElCliente localidad1;
    private DatosIngresadosPorElCliente localidad2;
    private double costo;

    public Conexion(DatosIngresadosPorElCliente loc1, DatosIngresadosPorElCliente loc2, double costo) {
        this.localidad1 = loc1;
        this.localidad2 = loc2;
        this.costo = costo;
    }

    public DatosIngresadosPorElCliente getLocalidad1() { return localidad1; }
    public DatosIngresadosPorElCliente getLocalidad2() { return localidad2; }
    public double getCosto() { return costo; }

    @Override
    public String toString() {
        return localidad1.obtenerNombre() + " <---> " + localidad2.obtenerNombre() + " (Costo: $" + String.format("%.2f", costo) + ")";
    }
}