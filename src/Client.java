import protocol.ConsoleProtocol;
import protocol.DialogProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

public class Client extends Util implements Runnable {

    private static int SERV_PORT = 4000;
    private static String SERV_IP = "127.0.0.2";

    public Client() throws Exception {
        super();
    }

    public Client(int port) throws Exception {
        super(port);
    }

    @Override
    public void run() {
        (new Thread(() -> {
            input();
        })).start();

        while(true) {
            System.out.println("Boucle");
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

    public String input() {
        while(true) {
            Scanner scan = new Scanner(System.in);
            String text = scan.nextLine();
            if(!text.equalsIgnoreCase("")) {
                if(this.envoyer((ConsoleProtocol.getProtocoleMessage(text)).toString(), SERV_IP, SERV_PORT)) {
                    System.out.println("Message envoyé");
                } else {
                    System.out.println("Erreur envoi");
                }
            }
        }
    }

    /* ======================================= */

    public static void main(String[] args) {
        Client client = null;

        try {
            client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(client == null)
            return;

        boolean env = client.envoyer(DialogProtocol.requestConnection(client), SERV_IP, SERV_PORT);
        if(env)
            System.out.println("Connecté au serveur");
        else
            System.out.println("Erreur connexion");

        (new Thread(client)).start();
    }
}
