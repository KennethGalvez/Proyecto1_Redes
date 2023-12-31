// Import necessary libraries
package redes;

import java.util.Map;
import java.util.Scanner;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

// Main class
public class App {
    public static void main(String[] args) throws XmppStringprepException {
        Scanner scanner = new Scanner(System.in);
        // Initialize XMPP communication with server details
        ComunicacionXMPP xmppCommunication = new ComunicacionXMPP("alumchat.xyz", 5222, "alumchat.xyz");

        // Main loop for user interaction
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Logic to register a new account
                    System.out.print("Nuevo usuario: ");
                    String newUser = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String newPassword = scanner.nextLine();

                    if (xmppCommunication.registrarCuenta(newUser, newPassword)) {
                        System.out.println("Registro exitoso.");
                    } else {
                        System.out.println("No se pudo registrar la cuenta.");
                    }
                    break;
                case 2:
                    // Logic to log in
                    System.out.print("Usuario: ");
                    String username = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String password = scanner.nextLine();
                    if (xmppCommunication.iniciarSesion(username, password)) {
                        System.out.println("Inicio de sesión exitoso.");
                    } else {
                        System.out.println("No se pudo iniciar sesión.");
                    }
                    break;
                case 3:
                    // Logic to log out
                    xmppCommunication.cerrarConexion();
                    System.out.println("Sesión cerrada.");
                    break;
                case 4:
                    // Logic to delete the account
                    if (xmppCommunication.eliminarCuenta()) {
                        System.out.println("Cuenta eliminada exitosamente.");
                    } else {
                        System.out.println("No se pudo eliminar la cuenta.");
                    }
                    break;
                case 5:
                    // Logic to display contacts and their states
                    Map<Jid, Presence> contactosYEstados = xmppCommunication.obtenerContactosYEstados();
                    System.out.println("Lista de contactos y estados:");
                    for (Map.Entry<Jid, Presence> entry : contactosYEstados.entrySet()) {
                        Jid contactJid = entry.getKey();
                        Presence presence = entry.getValue();

                        String estado;
                        if (presence.isAvailable()) {
                            if (presence.getMode() == Presence.Mode.available) {
                                estado = "Disponible";
                            } else if (presence.getMode() == Presence.Mode.away) {
                                estado = "Ausente";
                            } else if (presence.getMode() == Presence.Mode.dnd) {
                                estado = "Ocupado";
                            } else {
                                estado = "No Disponible";
                            }
                        } else {
                            estado = "Desconectado";
                        }

                        System.out.println(contactJid + " - Estado: " + estado);
                    }
                    break;
                case 6:
                    // Logic to add a contact
                    System.out.print("Nombre del usuario que deseas agregar a tus contactos: ");
                    String contactUsername = scanner.nextLine();
                    
                    String contactJid = contactUsername + "@alumchat.xyz";
                    
                    try {
                        xmppCommunication.agregarContacto(contactJid);
                        System.out.println(contactUsername + " ha sido agregado a tus contactos.");
                    } catch (InterruptedException | XMPPErrorException | SmackException e) {
                        e.printStackTrace();
                        System.out.println("No se pudo agregar a " + contactUsername + " a tus contactos.");
                    }
                    break;
                case 7:
                    // Logic to view contact details
                    System.out.print("Nombre de usuario para ver detalles: ");
                    String usernameToViewDetails = scanner.nextLine();

                    try {
                        VCard vCard = xmppCommunication.obtenerVCard(usernameToViewDetails);
                        if (vCard != null) {
                            System.out.println("Detalles de " + usernameToViewDetails + ":");
                            System.out.println("Nombre completo: " + vCard.getFirstName() + " " + vCard.getLastName());
                            System.out.println("Género: " + vCard.getField("GENDER"));
                            System.out.println("Cumpleaños: " + vCard.getField("BDAY"));
                            System.out.println("Correo electrónico: " + vCard.getEmailWork());
                            System.out.println("Teléfono: " + vCard.getPhoneHome("VOICE"));
                            // Agrega más campos de la VCard si es necesario
                        } else {
                            System.out.println("No se pudo obtener los detalles del contacto.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("No se pudo obtener los detalles del contacto.");
                    }
                    break;
                case 8:
                    // Logic for 1-to-1 communication
                    startOneToOneChat(xmppCommunication);
                    break;
                case 9:
                    // Logic to join group conversations
                    System.out.print("Nombre de la sala de chat grupal: ");
                    String roomName = scanner.nextLine();

                    try {
                        MultiUserChat muc = xmppCommunication.unirseConversacionGrupal(roomName);
                        if (muc != null) {
                            System.out.println("Te has unido a la sala: " + roomName);
                            
                            System.out.println("Escribe tu mensaje (escribe /exit para salir del chat grupal):");
                            while (true) {
                                System.out.print("Tú: ");
                                String groupMessage = scanner.nextLine();
                                if (groupMessage.equalsIgnoreCase("/exit")) {
                                    break; // Salir del bucle si el usuario escribe "/exit"
                                }
                                xmppCommunication.enviarMensajeGrupo(muc, groupMessage);
                            }
                            
                            muc.leave(); // Salir de la sala cuando el usuario termina el chat grupal
                        } else {
                            System.out.println("No se pudo unir a la sala de chat grupal.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("No se pudo unir a la sala de chat grupal.");
                    }
                    break;
                case 10:
                    // Logic to set presence message
                    xmppCommunication.mostrarOpcionesEstado(); // Display and handle presence options
                    break;
                case 11:
                    // Logic to send/receive notifications
                    System.out.print("Nombre del usuario al que deseas enviar una notificación: ");
                    String recipientUsername = scanner.nextLine();
                    
                    System.out.print("Mensaje de notificación: ");
                    String notificationMessage = scanner.nextLine();
                    
                    try {
                        xmppCommunication.enviarNotificacion(recipientUsername, notificationMessage);
                        System.out.println("Notificación enviada exitosamente.");
                    } catch (SmackException.NotConnectedException | InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("Error al enviar la notificación.");
                    }
                    break;
                case 12:
                    System.out.println("Saliendo del cliente XMPP. ¡Hasta luego!");
                    xmppCommunication.cerrarConexion();
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
        System.out.println("12. Salir");
        System.out.print("Elige una opción: ");
    }

    private static void startOneToOneChat(ComunicacionXMPP xmppCommunication) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del usuario con el que deseas chatear: ");
        String recipient = scanner.nextLine();

        System.out.println("Comenzando el chat con " + recipient);
        
        System.out.println("Para dejar de enviar mensajes escribe /exit");

        // Iniciar el chat 1 a 1
        try {
            xmppCommunication.iniciarChat(recipient);

            while (true) {
                System.out.print("Tú: ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("/exit")) {
                    break; // Salir del bucle si el usuario escribe "/exit"
                }
                xmppCommunication.enviarMensaje(message);
            }

            xmppCommunication.cerrarChat();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Saliendo del chat 1 a 1.");
    }
}