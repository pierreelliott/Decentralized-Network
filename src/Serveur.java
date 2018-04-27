import protocol.DialogProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;

public class Serveur extends Util implements Runnable {

    public Serveur(int port) throws SocketException {
        super(port);
    }

    @Override
    public void run() {
        int length = 10000;
        byte[] buf;
        DatagramPacket p;
        System.out.println("Serveur ready");
        while(true) {
            buf = new byte[length];
            p = new DatagramPacket(buf, length);

            try {
                ds.receive(p);
                System.out.print("Message reçu : '");
                String msg = (new String(p.getData(), "UTF-8")).trim();
                System.out.print(msg);
                System.out.println("'");
                DialogProtocol message = new DialogProtocol(msg);
                if(message.isAskingConnection()) {
                    System.out.println("Client connecté");
                    establishConnection(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void establishConnection(DatagramPacket p) {
        System.out.println("Etablissement connexion");
        Comm l = new Comm(p.getAddress(), p.getPort(), ds);
        (new Thread(l)).start();
    }

    // ===================== STATIC ========================

    public static void main(String[] args) {
        int PORT = 4000;
        try {
            Serveur serv = new Serveur(PORT);
            (new Thread(serv)).start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
