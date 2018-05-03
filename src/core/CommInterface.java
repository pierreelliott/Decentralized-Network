package core;

import java.net.DatagramSocket;
import java.net.ServerSocket;

public abstract class CommInterface {

    private Object socket;

    public CommInterface(Object sock) throws Exception {
        if( !(sock instanceof ServerSocket || sock instanceof DatagramSocket) ) {
            throw new Exception("Invalid socket provided");
        }
        socket = sock;
    }

    public Object getSocket() {
        return socket;
    }

    public abstract void init();

    public abstract void preprocess();

    public abstract void postprocess();

    public abstract void process();
}
