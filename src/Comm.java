import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class Comm extends Util implements Runnable {

    private InetAddress ipClient;
    private int portClient;

    public Comm(InetAddress ip, int port, DatagramSocket ds) {
        super(ds);
        ipClient = ip;
        portClient = port;
    }

    @Override
    public void run() {
        boolean connected = true;
        System.out.println("Running");
        boolean temp = true;
        while(connected) {
            int length = 10000;
            if(temp) {
                System.out.println("Boucle lancée");
                temp = false;
            }
            byte[] buf = new byte[length];
            DatagramPacket p = new DatagramPacket(buf, length);
            try {
                ds.receive(p);
                System.out.println("Un client a envoyé un message");
                System.out.println("IP paq : " + p.getAddress());
                System.out.println("");
                if(p.getAddress().getAddress() == ipClient.getAddress() && p.getPort() == portClient) {
                    connected = traitement(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean traitement(DatagramPacket p) {
        String text = null;
        try {
            text = new String(p.getData(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            text = "Erreur décryptage, message incorrectement reçu";
        }
        System.out.println(text);
        envoyer("Message reçu : '"+text+"'", ipClient, portClient);

        if(text.equalsIgnoreCase("Goodbye serveur RX302")) {
            return false;
        }
        return true;
    }

    // ============ STATIC ===================

    private static List<Comm> clients;
}
