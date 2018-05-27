import core.Utils;
import protocol.DialogProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import util.Observer;
import util.Observable;

public class Serveur extends Utils implements Runnable {

    private List<Comm> clients = new ArrayList<>();

    public Serveur(InetAddress adr, int port) throws Exception {
        super(port, adr);
    }

    public void establishConnection(DatagramPacket p) {
        try {
            DatagramSocket sock = new DatagramSocket();
            Comm l = new Comm(p.getAddress(), p.getPort(), sock, this);
            clients.add(l);
            (new Thread(l)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void init() {
        System.out.println("Serveur ready");
    }

    @Override
    public void process() {
        int length = 10000;
        byte[] buf;
        DatagramPacket p;
        buf = new byte[length];
        p = new DatagramPacket(buf, length);
        p = receive(p);
        if(p == null) {
            System.out.println("Problème réception");
            return;
        }
        String msg = (new String(p.getData())).trim();
        System.out.println("(Serveur) Message reçu : '" + msg + "'");
        System.out.println("IP : " + p.getAddress().getHostAddress());
        DialogProtocol message = new DialogProtocol(p);
        if(message.isAskingConnection()) {
            System.out.println("Client connecté");
            establishConnection(p);
        }
    }

    public void broadcast(String message, Comm sender) {
        for(Comm c : clients) {
            if(c != sender) {
                c.sendBroadcast(message);
            }
        }
    }

    // ===================== STATIC ========================

    private static int SERV_PORT = 4000;
    private static String SERV_IP = "127.0.0.2";
    private static InetAddress SERV_INET = null;

    public static void main(String[] args) {
        try {
            InetAddress adr = InetAddress.getByName(SERV_IP);
            SERV_INET = adr;
            Serveur serv = new Serveur(adr, SERV_PORT);
            (new Thread(serv)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
