import protocol.ConsoleProtocol;
import protocol.DialogProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

public class Client extends Util implements Runnable {

    private String SERV_IP = STATIC_SERV_IP;
    private int SERV_PORT = STATIC_SERV_PORT;

    public Client() throws Exception {
        super();
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
                traiterReponse(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void traiterReponse(DatagramPacket p) {
        DialogProtocol message = new DialogProtocol(p);
        if(message.isChangingPort()) {
            SERV_IP = p.getAddress().getHostAddress();
            SERV_PORT = p.getPort();
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

    private static int STATIC_SERV_PORT = 4000;
    private static String STATIC_SERV_IP = "127.0.0.1";

    public static void main(String[] args) {
        Client client = null;

        try {
            client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(client == null)
            return;

        boolean env = client.envoyer(DialogProtocol.requestConnection(client), STATIC_SERV_IP, STATIC_SERV_PORT);
        if(env)
            System.out.println("Connecté au serveur");
        else
            System.out.println("Erreur connexion");

        (new Thread(client)).start();
    }
}
