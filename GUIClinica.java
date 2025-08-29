import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

public class GUIClinica extends JFrame {
    private Clinica clinica;
    private JTextArea salida;

    public GUIClinica() {
        clinica = new Clinica();
        setTitle("Veterinaria - GUI");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        salida = new JTextArea();
        salida.setEditable(false);
        add(new JScrollPane(salida), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnDueno = new JButton("Registrar dueño");
        JButton btnMascota = new JButton("Registrar mascota");
        JButton btnConsulta = new JButton("Registrar consulta");
        JButton btnReporte = new JButton("Reportes");

        botones.add(btnDueno);
        botones.add(btnMascota);
        botones.add(btnConsulta);
        botones.add(btnReporte);
        add(botones, BorderLayout.SOUTH);

        btnDueno.addActionListener(e -> {
            String n = JOptionPane.showInputDialog("Nombre dueño:");
            String t = JOptionPane.showInputDialog("Teléfono:");
            String c = JOptionPane.showInputDialog("Correo:");
            clinica.agregarDueno(new Dueno(n,t,c));
            salida.append("Dueño registrado: " + n + "\n");
        });

        btnMascota.addActionListener(e -> {
            String dn = JOptionPane.showInputDialog("Nombre dueño:");
            Dueno d = clinica.buscarDuenoPorNombre(dn);
            if (d == null) {
                JOptionPane.showMessageDialog(this,"No existe ese dueño.");
                return;
            }
            String nm = JOptionPane.showInputDialog("Nombre mascota:");
            String esp = JOptionPane.showInputDialog("Especie:");
            String rz = JOptionPane.showInputDialog("Raza:");
            int ed = Integer.parseInt(JOptionPane.showInputDialog("Edad:"));
            clinica.agregarMascota(new Mascota(nm, esp, rz, ed, d));
            salida.append("Mascota registrada: " + nm + "\n");
        });

        btnConsulta.addActionListener(e -> {
            String dn = JOptionPane.showInputDialog("Nombre dueño:");
            String nm = JOptionPane.showInputDialog("Nombre mascota:");
            Mascota m = clinica.buscarMascotaPorNombreYDueno(nm, dn);
            if (m == null) {
                JOptionPane.showMessageDialog(this,"No existe esa mascota.");
                return;
            }
            LocalDate f = LocalDate.parse(JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):"));
            String mot = JOptionPane.showInputDialog("Motivo:");
            String tr = JOptionPane.showInputDialog("Tratamiento:");
            clinica.agregarConsulta(new Consulta(f,mot,tr,m));
            salida.append("Consulta registrada para " + m.getNombre() + "\n");
        });

        btnReporte.addActionListener(e -> mostrarReportes());
    }

    private void mostrarReportes() {
        salida.append("\nREPORTES\n");

        String especie = clinica.especieMasAtendida();
        salida.append("Especie más atendida: " + (especie != null ? especie : "N/D") + "\n");

        Mascota top = clinica.mascotaConMasConsultas();
        salida.append("Mascota con más consultas: " + (top != null ? top.getNombre() : "N/D") + "\n");

        double prom = clinica.promedioConsultasPorMes();
        salida.append(String.format("Promedio consultas/mes: %.2f\n", prom));

        salida.append("Lista de dueños\n");
        List<Dueno> ds = clinica.getDuenos();
        if (ds.isEmpty()) salida.append("No hay dueños registrados.\n");
        else for (Dueno d : ds) salida.append("Dueño: " + d.getNombre() + " | Tel: " + d.getTelefono() + " | Correo: " + d.getCorreo() + "\n");

        salida.append("Lista de mascotas \n");
        List<Mascota> ms = clinica.getMascotas();
        if (ms.isEmpty()) salida.append("No hay mascotas registradas.\n");
        else for (Mascota m : ms) salida.append(m.toString() + "\n");

        salida.append(" Lista de consultas\n");
        List<Consulta> cs = clinica.getConsultas();
        if (cs.isEmpty()) salida.append("No hay consultas registradas.\n");
        else for (Consulta c : cs)
            salida.append("Fecha: " + c.getFecha() +
                    " | Mascota: " + c.getMascota().getNombre() +
                    " | Dueño: " + c.getMascota().getDueno().getNombre() +
                    " | Motivo: " + c.getMotivo() +
                    " | Tratamiento: " + c.getTratamiento() + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIClinica().setVisible(true));
    }
}
