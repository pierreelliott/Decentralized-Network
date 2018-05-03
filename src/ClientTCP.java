import java.io.IOException;
import java.net.*;

public class ClientTCP {

    private Socket sock;

    public ClientTCP(InetAddress dest, int port) throws IOException {
        sock = new Socket(dest, port);
    }
}
