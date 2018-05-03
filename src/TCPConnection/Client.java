package TCPConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends CommInterface implements Runnable {

    public Client() {
        super(new Socket());
        InetAddress ad = null;
        try {
            ad = InetAddress.getByName("127.0.0.1");
            getSocket().connect(new InetSocketAddress(ad, 4000));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    }

    @Override
    public void run() {

    }
}
