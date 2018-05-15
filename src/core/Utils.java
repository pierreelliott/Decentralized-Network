package core;

import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class Utils extends CommInterface implements Runnable {

    public Utils(DatagramSocket o) throws Exception {
        super(o);
    }

    public Utils(int port) throws Exception {
        super(port);
    }

    public Utils(int port, InetAddress adr) throws Exception {
        super(port, adr);
    }

    @Override
    public void run() {
        try {
            init();
            while(true) {
                preprocess();
                process();
                postprocess();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
