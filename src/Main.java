//Código Actividad n9
//Validación de contraseña
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

// Clase principal
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newFixedThreadPool(5); // Pool de hilos

        System.out.println("=== Validación de contraseñas ===");
        System.out.print("¿Cuántas contraseñas desea validar?: ");
        int cantidadContrasenas = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        // Lista para almacenar contraseñas
        List<String> contrasenas = new ArrayList<>();
        for (int i = 0; i < cantidadContrasenas; i++) {
            System.out.printf("Ingrese la contraseña: ", i + 1);
            contrasenas.add(scanner.nextLine());
        }

        System.out.println("\nValidando contraseñas...\n");

        // Lanzar un hilo por cada contraseña
        for (String contrasena : contrasenas) {
            executorService.execute(new ValidadorContrasena(contrasena));
        }

        executorService.shutdown(); // Cerrar el pool de hilos
    }
}

// Clase que representa la tarea de validar contraseñas
class ValidadorContrasena implements Runnable {
    private final String contrasena;

    // Expresiones regulares para validación
    private static final Pattern LONGITUD_MINIMA = Pattern.compile(".{8,}");
    private static final Pattern CARACTER_ESPECIAL = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
    private static final Pattern DOS_MAYUSCULAS = Pattern.compile(".*[A-Z].*[A-Z].*");
    private static final Pattern TRES_MINUSCULAS = Pattern.compile(".*[a-z].*[a-z].*[a-z].*");
    private static final Pattern NUMERO = Pattern.compile(".*\\d.*");

    public ValidadorContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public void run() {
        System.out.printf("Validando contraseña: \"%s\"\n", contrasena);

        boolean esValida = true;

        // Validación de criterios
        if (!LONGITUD_MINIMA.matcher(contrasena).matches()) {
            System.out.println("Error: La contraseña debe tener al menos 8 caracteres.");
            esValida = false;
        }
        if (!CARACTER_ESPECIAL.matcher(contrasena).matches()) {
            System.out.println("Error: La contraseña debe contener al menos 1 carácter especial.");
            esValida = false;
        }
        if (!DOS_MAYUSCULAS.matcher(contrasena).matches()) {
            System.out.println("Error: La contraseña debe contener al menos 2 letras mayúsculas.");
            esValida = false;
        }
        if (!TRES_MINUSCULAS.matcher(contrasena).matches()) {
            System.out.println("Error: La contraseña debe contener al menos 3 letras minúsculas.");
            esValida = false;
        }
        if (!NUMERO.matcher(contrasena).matches()) {
            System.out.println("Error: La contraseña debe contener al menos 1 número.");
            esValida = false;
        }

        // Resultado final
        if (esValida) {
            System.out.printf("La contraseña \"%s\" es válida.\n\n", contrasena);
        } else {
            System.out.printf("La contraseña \"%s\" es inválida.\n\n", contrasena);
        }
    }
}

