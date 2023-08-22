package redes;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;

public class ComunicacionXMPP {

    private AbstractXMPPConnection connection;

    public ComunicacionXMPP(String host, int port, String domain) throws XmppStringprepException {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost(host)
                .setPort(port)
                .setXmppDomain(domain)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();

        connection = new XMPPTCPConnection(config);
    }

    public boolean iniciarSesion(String username, String password) {
        try {
            connection.connect();
            connection.login(username, password);
            return true;
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cerrarConexion() {
        if (connection != null && connection.isConnected()) {
            connection.disconnect();
        }
    }

    private Chat chat;

    public void iniciarChat(String recipient) throws SmackException.NotConnectedException, XmppStringprepException, InterruptedException {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        EntityBareJid jid = JidCreate.entityBareFrom(recipient + "@alumchat.xyz");
        chat = chatManager.chatWith(jid);

        // Agregar un listener para manejar los mensajes entrantes
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                String body = message.getBody();
                System.out.println(from + ": " + body);
            }
        });

    }

    public void enviarMensaje(String message) throws SmackException.NotConnectedException, InterruptedException {
        Message newMessage = new Message();
        newMessage.setBody(message);
        chat.send(newMessage);
    }


    public void cerrarChat() {
        // No es necesario cerrar el chat en esta versión de Smack
    }

    public boolean registrarCuenta(String newUser, String newPassword) {
    try {
        AccountManager accountManager = AccountManager.getInstance(connection);
        accountManager.sensitiveOperationOverInsecureConnection(true);
        accountManager.createAccount(Localpart.from(newUser), newPassword);
        return true;
    } catch (SmackException | InterruptedException | XMPPException | XmppStringprepException e) {
        e.printStackTrace();
        return false;
        }
    }
    public boolean eliminarCuenta() {
        try {
            AccountManager accountManager = AccountManager.getInstance(connection);
            accountManager.deleteAccount();
            return true;
        } catch (SmackException | InterruptedException | XMPPException e) {
            e.printStackTrace();
            return false;
        }
    }


}