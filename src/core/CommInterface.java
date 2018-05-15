package core;

import java.io.IOException;
import java.net.*;

public abstract class CommInterface {

    private DatagramSocket socket;

    public CommInterface(DatagramSocket sock) throws Exception {
        socket = sock;
    }

    public CommInterface(int port, InetAddress adr) throws Exception {
        socket = new DatagramSocket(port, adr);
    }

    public CommInterface(int port) throws Exception {
        socket = new DatagramSocket(port);
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public DatagramPacket receive(DatagramPacket p) {
        try {
            socket.receive(p);
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean envoyer(String data, InetAddress ip, int port) {
        DatagramPacket dp = new DatagramPacket(data.getBytes(), data.length(), ip, port);
        try {
            socket.send(dp);
        } catch (IOException e) {
            System.err.println("Erreur envoi :\n" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean envoyer(String data, String ip, int port) {
        InetAddress ad = null;
        try {
            ad = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            System.err.println("Erreur résolution adresse IP");
            return false;
        }
        return envoyer(data, ad, port);
    }

    public boolean envoyer(String data, byte[] ip, int port) {
        InetAddress ad = null;
        try {
            ad = InetAddress.getByAddress(ip);
        } catch (UnknownHostException e) {
            System.err.println("Erreur résolution adresse IP");
            return false;
        }
        return envoyer(data, ad, port);
    }

    public abstract void init();

    public abstract void preprocess();

    public abstract void postprocess();

    public abstract void process();
}
