package TCPConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerManager extends CommInterface implements Runnable {

    private List<ServerManager> servers;
    private ServerSocket socket;
    private boolean finished;

    public ServerManager() {}

    public ServerManager(Socket sock) {
        super(sock);
    }

    public ServerManager(int port) throws IOException {
        this(port, 1);
    }

    public ServerManager(int port, int maxLogs) throws IOException {
        socket = new ServerSocket(port, maxLogs);
        finished = false;
    }

    @Override
    public void run() {
        init();
        preprocess();
        while(!hasFinished()) {
            process();
        }
    }

    public boolean hasFinished() { return finished; }
    public void setFinished() { finished = true; }

    @Override
    public void init() {

    }

    @Override
    public void preprocess() {

    }

    @Override
    public void postprocess() {

    }

    @Override
    public void process() {
        try {
            Socket sock = socket.accept();
            Server server = new Server(sock);
            servers.add(server);
        } catch (IOException e) {
            e.printStackTrace();
//            setFinished();
        }
    }
}
