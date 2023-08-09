import org.jivesoftware.smack.*;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration.Builder;

public class XMPPClient {
    public static void main(String[] args) {
        // XMPP Connection Configuration
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
            .setXmppDomain("alumchat.xyz")
            .setHost("hostname")
            .setPort(5222)
            .build();

        // Create XMPP Connection
        AbstractXMPPConnection connection = new XMPPTCPConnection(config);

        try {
            // Connect to XMPP Server
            connection.connect();
            System.out.println("Connected to XMPP Server");

            // Log in with a user account
            connection.login("your_username", "your_password");
            System.out.println("Login successful");

            // Perform other operations here...

            // Log out and disconnect
            connection.disconnect();
            System.out.println("Disconnected successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
