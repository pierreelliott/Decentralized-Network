import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

public class Client extends Util implements Runnable {

    public Client() throws SocketException {
        super();
    }

    public Client(int port) throws SocketException {
        super(port);
    }

    @Override
    public void run() {
        while(true) {
            int length = 10000;
            byte[] buf = new byte[length];
            DatagramPacket p = new DatagramPacket(buf, length);
            try {
                ds.receive(p);
                System.out.print("Message reçu : '");
                String msg = (new String(p.getData(), "UTF-8")).trim();
                System.out.print(msg);
                System.out.println("'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* ======================================= */

    public static void main(String[] args) {
        int SERV_PORT = 4000;
        byte[] SERV_IP = { 0x7F, 0x00, 0x00, 0x01 };
        Client client = null;

        try {
            client = new Client();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if(client == null)
            return;

        boolean env = client.envoyer("Hello serveur RX302", SERV_IP, SERV_PORT);
        if(env)
            System.out.println("Connecté au serveur");
        else
            System.out.println("Erreur connexion");



        while(true) {
            Scanner scan = new Scanner(System.in);
            String text = scan.nextLine();
            if(!text.equalsIgnoreCase("")) {
                if(client.envoyer(text, SERV_IP, SERV_PORT)) {
                    System.out.println("Message envoyé");
                } else {
                    System.out.println("Erreur envoi");
                }
            }
        }
    }
}
