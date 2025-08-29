import java.util.ArrayList;

public class Mascota {
    private String nombre;
    private String especie;
    private String raza;
    private int    edad;
    private Dueno  dueno;
    private ArrayList<Consulta> consultas;

    public Mascota(String nombre, String especie, String raza, int edad, Dueno dueno) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.dueno = dueno;
        this.consultas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getEspecie() { return especie; }
    public String getRaza() { return raza; }
    public int getEdad() { return edad; }
    public Dueno getDueno() { return dueno; }

    public ArrayList<Consulta> getConsultas() { return consultas; }

    public void agregarConsulta(Consulta c) {
        if (c != null) {
            consultas.add(c);
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + especie + ", " + raza + ", " + edad + " años) - Dueño: " +
                (dueno != null ? dueno.getNombre() : "N/D");
    }
}
