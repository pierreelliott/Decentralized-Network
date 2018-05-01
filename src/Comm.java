import protocol.DialogProtocol;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import util.Observable;
import util.Observer;

public class Comm extends Util implements Runnable, Observer {

    private InetAddress ipClient;
    private int portClient;

    private DatagramPacket dp = null;

    public Comm(InetAddress ip, int port, DatagramSocket ds) {
        super(ds);
        ipClient = ip;
        portClient = port;
    }

    @Override
    public void run() {
        boolean connected = true;
        while(connected) {
            int length = 10000;
            byte[] buf = new byte[length];
            DatagramPacket p = new DatagramPacket(buf, length);
            try {
//                ds.receive(p);
//                System.out.println("Un client a envoyé un message");
//                System.out.println("IP paq : " + p.getAddress());
//                System.out.println("");
//                if(p.getAddress().getAddress() == ipClient.getAddress() && p.getPort() == portClient) {
//                    connected = traitement(p);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean traitement(DatagramPacket p) {
        DialogProtocol text = new DialogProtocol(p);

        System.out.println(text);
        envoyer("Message reçu : '"+text+"'", ipClient, portClient);

        if(text.isAbortingConnection()) {
            return false;
        }

        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Updated !");
        if(arg instanceof DatagramPacket) {
            DatagramPacket p = (DatagramPacket) arg;
            System.out.println("Un client a envoyé un message");
            System.out.println("IP paq : " + p.getAddress());
            System.out.println("");
            if(p.getAddress().getAddress() == ipClient.getAddress() && p.getPort() == portClient) {
                traitement(p);
            }
        }
    }

    // ============ STATIC ===================

    private static List<Comm> clients;
}
