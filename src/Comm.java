import core.Utils;
import protocol.CommandEnum;
import protocol.DialogProtocol;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import util.Observable;
import util.Observer;

public class Comm extends Utils implements Runnable, Observer {

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

    public Comm(InetAddress ip, int port, DatagramSocket ds, Serveur parent, boolean samePort) throws Exception {
        this(ip, port, ds, parent);
        samePortAsParent = samePort;
    }

    public boolean traitement(DatagramPacket p) {
        DialogProtocol text = new DialogProtocol(p);

        System.out.println(text);
        envoyer("Message reçu : '"+text.toString()+"'", ipClient, portClient);

        if(text.isAbortingConnection()) {
            return false;
        }

        return true;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Updated !");
        if(arg instanceof DatagramPacket) {
            verifications((DatagramPacket) arg);
        }
    }

    public void verifications(DatagramPacket p) {
        System.out.println("Un client a envoyé un message");
        System.out.println("IP paq : " + p.getAddress().getHostAddress());
        System.out.println("");
        if(samePortAsParent || p.getAddress().getHostAddress().equalsIgnoreCase(ipClient.getHostAddress())
                && p.getPort() == portClient) {
            traitement(p);
            System.out.println("Message traité");
        }
    }

    @Override
    public void init() {
        if(!samePortAsParent) {
            DialogProtocol response = new DialogProtocol();
            response.setCommand(CommandEnum.CHANGINGPORT);
            response.setContent("");
            envoyer(response.toString(), ipClient, portClient);
        }
    }

    @Override
    public void preprocess() {

    }

    @Override
    public void postprocess() {

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
        System.out.print("Message reçu : '");
        String msg = (new String(p.getData())).trim();
        System.out.print(msg);
        System.out.println("'");

        update(null, p);
    }

    // ============ STATIC ===================

    private static List<Comm> clients;
}
