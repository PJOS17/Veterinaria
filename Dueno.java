import java.util.ArrayList;

public class Dueno {
    private String nombre;
    private String telefono;
    private String correo;
    private ArrayList<Mascota> mascotas;

    public Dueno(String nombre, String telefono, String correo) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.mascotas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }

    public ArrayList<Mascota> getMascotas() { return mascotas; }

    public void agregarMascota(Mascota m) {
        if (m != null && !mascotas.contains(m)) {
            mascotas.add(m);
        }
    }
}
