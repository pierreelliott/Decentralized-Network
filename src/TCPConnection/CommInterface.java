package TCPConnection;

import java.net.ServerSocket;
import java.net.Socket;

public abstract class CommInterface {

    private Socket socket;

    public CommInterface() {

    }

    public CommInterface(Socket sock) {
        socket = sock;
    }

    public Socket getSocket() {
        return socket;
    }

    public abstract void init();

    public abstract void preprocess();

    public abstract void postprocess();

    public abstract void process();
}
