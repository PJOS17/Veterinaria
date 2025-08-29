import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class Clinica {
    private ArrayList<Dueno>    duenos;
    private ArrayList<Mascota>  mascotas;
    private ArrayList<Consulta> consultas;

    public Clinica() {
        this.duenos = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.consultas = new ArrayList<>();
    }

    public void agregarDueno(Dueno d) {
        if (d != null) duenos.add(d);
    }

    public void agregarMascota(Mascota m) {
        if (m != null) {
            mascotas.add(m);
            if (m.getDueno() != null) {
                m.getDueno().agregarMascota(m);
            }
        }
    }

    public void agregarConsulta(Consulta c) {
        if (c != null) {
            consultas.add(c);
            if (c.getMascota() != null) {
                c.getMascota().agregarConsulta(c);
            }
        }
    }

    public Dueno buscarDuenoPorNombre(String nombre) {
        for (Dueno d : duenos) {
            if (d.getNombre().equalsIgnoreCase(nombre)) return d;
        }
        return null;
    }

    public Mascota buscarMascotaPorNombreYDueno(String nombreMascota, String nombreDueno) {
        for (Mascota m : mascotas) {
            boolean matchNombre = m.getNombre().equalsIgnoreCase(nombreMascota);
            boolean matchDueno = (m.getDueno() != null && m.getDueno().getNombre().equalsIgnoreCase(nombreDueno));
            if (matchNombre && matchDueno) return m;
        }
        return null;
    }

    public List<Dueno> getDuenos() { return Collections.unmodifiableList(duenos); }
    public List<Mascota> getMascotas() { return Collections.unmodifiableList(mascotas); }
    public List<Consulta> getConsultas() { return Collections.unmodifiableList(consultas); }

    public String especieMasAtendida() {
        if (consultas.isEmpty()) return null;
        Map<String, Integer> conteo = new HashMap<>();
        for (Consulta c : consultas) {
            Mascota m = c.getMascota();
            String especie = (m != null && m.getEspecie() != null) ? m.getEspecie().trim() : "Desconocida";
            conteo.put(especie, conteo.getOrDefault(especie, 0) + 1);
        }
        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Mascota mascotaConMasConsultas() {
        if (consultas.isEmpty()) return null;
        Map<Mascota, Integer> conteo = new HashMap<>();
        for (Consulta c : consultas) {
            Mascota m = c.getMascota();
            if (m != null) conteo.put(m, conteo.getOrDefault(m, 0) + 1);
        }
        return conteo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public double promedioConsultasPorMes() {
        if (consultas.isEmpty()) return 0.0;
        Map<YearMonth, Long> porMes = consultas.stream()
                .collect(Collectors.groupingBy(c -> YearMonth.from(c.getFecha()), Collectors.counting()));
        double suma = porMes.values().stream().mapToLong(Long::longValue).sum();
        return suma / porMes.size();
    }
}
