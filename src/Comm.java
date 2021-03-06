import core.Utils;
import protocol.CommandEnum;
import protocol.DialogProtocol;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class Comm extends Utils implements Runnable {

    private InetAddress ipClient;
    private int portClient;
    private boolean samePortAsParent = false;

    private Serveur parentServeur;

    public Comm(InetAddress ip, int port, DatagramSocket ds, Serveur parent) throws Exception {
        super(ds);
        ipClient = ip;
        portClient = port;
        parentServeur = parent;
    }

    public boolean traitement(DatagramPacket p) {
        DialogProtocol text = new DialogProtocol(p);
        System.out.println("=============================");
        System.out.println("Un client a envoyé un message");

        System.out.println(text);
        if(text.isPing()) {
            envoyer(DialogProtocol.pong(), ipClient, portClient);
        } else if(text.isBroadcast()) {
            envoyer(DialogProtocol.acknowledgeRequest(), ipClient, portClient);
            parentServeur.broadcast(text.toString(), this);
        } else {
            envoyer(DialogProtocol.acknowledgeRequest(), ipClient, portClient);
        }
        System.out.println("Message traité");

        if(text.isAbortingConnection()) {
            return false;
        }

        return true;
    }

    public void sendBroadcast(String message) {
        envoyer(message, ipClient, portClient);
    }

    @Override
    public void init() {
        DialogProtocol response = new DialogProtocol();
        response.setCommand(CommandEnum.CHANGINGPORT);
        response.setContent("");
        envoyer(response.toString(), ipClient, portClient);
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

        traitement(p);
    }

    // ============ STATIC ===================

    private static List<Comm> clients;
}
