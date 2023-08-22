## Proyecto1_Redes
# Cliente de Chat XMPP - README - Kenneth Galvez 20079
This is an XMPP chat client that utilizes the Smack library to interact with an XMPP server. It allows users to register accounts, log in, engage in 1-to-1 chats, join group chat rooms, set presence states, and more.

# Requirements
Java 8 or higher.
Smack library (included in the project).

# Usage
Clone the repository or download the files to your local system.
Open the project in your preferred IDE.
Ensure you have the Smack library in your classpath.

# Project Structure
ComunicacionXMPP.java: A class responsible for XMPP communication.
App.java: Main class that provides the command-line interface.

# Configuration
In ComunicacionXMPP.java, adjust the connection values in the ComunicacionXMPP constructor based on the instructions of the XMPP server you are using.

# Features
- Register a New Account
Allows registering a new account on the XMPP server.
public boolean registrarCuenta(String newUser, String newPassword) {
    // ...
}

- Log In
Allows logging into an existing account..
public boolean iniciarSesion(String username, String password) {
    // ...
}

- Log Out
Closes the XMPP connection.
public void cerrarConexion() {
    // ...
}

- Delete Account
Deletes the user's account.
public boolean eliminarCuenta() {
    // ...
}

- Display Contacts and States
Displays the list of contacts and their states.
public Map<Jid, Presence> obtenerContactosYEstados() {
    // ...
}

- Add Contact
Adds a user to the contacts list.
public void agregarContacto(Jid contactJid) {
    // ...
}

- Get Contact Details
Retrieves contact details using VCard.
public VCard obtenerVCard(String username) {
    // ...
}

- 1-to-1 Chat
Starts a 1-to-1 chat and sends messages.
public void iniciarChat(String recipient) {
    // ...
}

public void enviarMensaje(String message) {
    // ...
}

public void cerrarChat() {
    // ...
}

- Join Group Chat Room
Allows joining a group chat room and sending messages.
public MultiUserChat unirseConversacionGrupal(String roomName) {
    // ...
}

public void enviarMensajeGrupo(MultiUserChat muc, String message) {
    // ...
}

- Set Presence State
Defines the user's presence state.
public void definirMensajePresencia(Presence.Mode presenceMode) {
    // ...
}

public void mostrarOpcionesEstado() {
    // ...
}

- Send Notifications
Sends notifications to other users.
public void enviarNotificacion(String recipientUsername, String notificationMessage) {
    // ...
}
public void iniciarEscuchaNotificaciones() {
    // ...
}





