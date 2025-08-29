import java.time.LocalDate;

public class Consulta {
    private LocalDate fecha;
    private String motivo;
    private String tratamiento;
    private Mascota mascota;

    public Consulta(LocalDate fecha, String motivo, String tratamiento, Mascota mascota) {
        this.fecha = fecha;
        this.motivo = motivo;
        this.tratamiento = tratamiento;
        this.mascota = mascota;
    }

    public LocalDate getFecha() { return fecha; }
    public String getMotivo() { return motivo; }
    public String getTratamiento() { return tratamiento; }
    public Mascota getMascota() { return mascota; }
}
