import java.net.*;

public class ServerTCP {

    private ServerSocket socket;

    public ServerTCP() throws Exception {
        this(0, 1);
    }

    public ServerTCP(int port) throws Exception {
        this(port, 1);
    }

    /**
     *
     * @param port If port == 0, socket is created on first available port
     * @param maxLogs Max number of connections allowed
     * @throws Exception
     */
    public ServerTCP(int port, int maxLogs) throws Exception {
        socket = new ServerSocket(port, maxLogs);
    }

    public void waitForRequests() {
        (new Thread(() -> {
            // What TODO
        })).start();
    }
}
