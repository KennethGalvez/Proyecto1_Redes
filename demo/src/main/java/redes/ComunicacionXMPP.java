package redes;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

public class ComunicacionXMPP {

    private XMPPTCPConnection connection;

    public ComunicacionXMPP(String host, int port, String domain) {
        try {
            CustomTrustManager customTrustManager = new CustomTrustManager();

            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost(host)
                .setPort(port)
                .setXmppDomain(domain)
                .setCustomX509TrustManager(customTrustManager)
                .build();

            XMPPTCPConnection connection = new XMPPTCPConnection(config);
            
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
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

}

    