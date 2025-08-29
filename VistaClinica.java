import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class VistaClinica {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Clinica clinica = new Clinica();
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1:
                    registrarDueno(clinica);
                    break;
                case 2:
                    registrarMascota(clinica);
                    break;
                case 3:
                    registrarConsulta(clinica);
                    break;
                case 4:
                    mostrarReportes(clinica); 
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
            System.out.println();
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("Veterinaria");
        System.out.println("1. Registrar dueño");
        System.out.println("2. Registrar mascota");
        System.out.println("3. Registrar consulta");
        System.out.println("4. Reportes (todos)");
        System.out.println("0. Salir");
    }

    private static void registrarDueno(Clinica clinica) {
        String nombre = leerTexto("Nombre: ");
        String tel = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        clinica.agregarDueno(new Dueno(nombre, tel, correo));
        System.out.println("Dueño registrado.");
    }

    private static void registrarMascota(Clinica clinica) {
        String nombreDueno = leerTexto("Nombre del dueño: ");
        Dueno d = clinica.buscarDuenoPorNombre(nombreDueno);
        if (d == null) {
            System.out.println("No existe ese dueño.");
            return;
        }
        String nombre = leerTexto("Nombre mascota: ");
        String especie = leerTexto("Especie: ");
        String raza = leerTexto("Raza: ");
        int edad = leerEntero("Edad: ");
        clinica.agregarMascota(new Mascota(nombre, especie, raza, edad, d));
        System.out.println("Mascota registrada.");
    }

    private static void registrarConsulta(Clinica clinica) {
        String dueno = leerTexto("Nombre dueño: ");
        String mascota = leerTexto("Nombre mascota: ");
        Mascota m = clinica.buscarMascotaPorNombreYDueno(mascota, dueno);
        if (m == null) {
            System.out.println("No existe esa mascota con ese dueño.");
            return;
        }
        LocalDate fecha = leerFecha("Fecha (YYYY-MM-DD): ");
        String motivo = leerTexto("Motivo: ");
        String trat = leerTexto("Tratamiento: ");
        clinica.agregarConsulta(new Consulta(fecha, motivo, trat, m));
        System.out.println("Consulta registrada.");
    }

    private static void mostrarReportes(Clinica clinica) {
        System.out.println(" REPORTES ");

        String especie = clinica.especieMasAtendida();
        System.out.println(especie == null ? "Sin datos de especie." : "Especie más atendida: " + especie);

        Mascota m = clinica.mascotaConMasConsultas();
        System.out.println(m == null ? "Sin datos de consultas." :
                "Mascota con más consultas: " + m.getNombre() + " (Dueño: " + m.getDueno().getNombre() + ")");

        double prom = clinica.promedioConsultasPorMes();
        System.out.printf("Promedio consultas/mes: %.2f%n", prom);

        System.out.println(" Lista de dueños ");
        List<Dueno> ds = clinica.getDuenos();
        if (ds.isEmpty()) {
            System.out.println("No hay dueños registrados.");
        } else {
            for (Dueno d : ds) {
                System.out.println("Dueño: " + d.getNombre() + " | Tel: " + d.getTelefono() + " | Correo: " + d.getCorreo());
            }
        }

        System.out.println("Lista de mascotas");
        List<Mascota> ms = clinica.getMascotas();
        if (ms.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
        } else {
            for (Mascota ma : ms) {
                System.out.println(ma.toString());
            }
        }

        System.out.println("Lista de consultas");
        List<Consulta> cs = clinica.getConsultas();
        if (cs.isEmpty()) {
            System.out.println("No hay consultas registradas.");
        } else {
            for (Consulta c : cs) {
                System.out.println("Fecha: " + c.getFecha() +
                        " | Mascota: " + c.getMascota().getNombre() +
                        " | Dueño: " + c.getMascota().getDueno().getNombre() +
                        " | Motivo: " + c.getMotivo() +
                        " | Tratamiento: " + c.getTratamiento());
            }
        }
    }

    private static String leerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido.");
            }
        }
    }

    private static LocalDate leerFecha(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto (usa YYYY-MM-DD).");
            }
        }
    }
}
