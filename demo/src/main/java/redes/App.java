package redes;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Lógica para registrar una nueva cuenta
                    break;
                case 2:
                    // Lógica para iniciar sesión
                    break;
                case 3:
                    // Lógica para cerrar sesión
                    break;
                case 4:
                    // Lógica para eliminar la cuenta
                    break;
                case 5:
                    // Lógica para mostrar todos los usuarios/contactos y su estado
                    break;
                case 6:
                    // Lógica para agregar un usuario a los contactos
                    break;
                case 7:
                    // Lógica para mostrar detalles de contacto de un usuario
                    break;
                case 8:
                    // Lógica para la comunicación 1 a 1 con un usuario
                    break;
                case 9:
                    // Lógica para participar en conversaciones grupales
                    break;
                case 10:
                    // Lógica para definir mensaje de presencia
                    break;
                case 11:
                    // Lógica para enviar/recibir notificaciones
                    break;
                case 12:
                    // Lógica para enviar/recibir archivos
                    break;
                case 13:
                    System.out.println("Saliendo del cliente XMPP.");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida. Introduce un número válido.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("=== Cliente XMPP ===");
        System.out.println("1. Registrar una nueva cuenta");
        System.out.println("2. Iniciar sesión con una cuenta");
        System.out.println("3. Cerrar sesión");
        System.out.println("4. Eliminar cuenta");
        System.out.println("5. Mostrar usuarios/contactos y su estado");
        System.out.println("6. Agregar usuario a contactos");
        System.out.println("7. Mostrar detalles de contacto");
        System.out.println("8. Comunicación 1 a 1 con usuario");
        System.out.println("9. Participar en conversaciones grupales");
        System.out.println("10. Definir mensaje de presencia");
        System.out.println("11. Enviar/recibir notificaciones");
        System.out.println("12. Enviar/recibir archivos");
        System.out.println("13. Salir");
        System.out.print("Elige una opción: ");
    }
}
