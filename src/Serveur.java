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

public class Serveur extends Utils implements Runnable, Observable {

    public Serveur(int port) throws Exception {
        super(port);
    }

    public Serveur(InetAddress adr, int port) throws Exception {
        super(port, adr);
    }

    private List<Observer> observers = new ArrayList<>();
    private boolean stateChanged = false;

    public void establishConnection(DatagramPacket p, DatagramSocket sock) {
        try {
            Comm l;
            if(getSocket() == sock) {
                l = new Comm(p.getAddress(), p.getPort(), sock, true);
                addObserver(l);
            } else {
                l = new Comm(p.getAddress(), p.getPort(), sock);
            }
            (new Thread(l)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void establishConnection(DatagramPacket p) {
        try {
            int port = Util.scan(SERV_PORT, SERV_PORT+5000).get(1);
            DatagramSocket sock = new DatagramSocket();
            establishConnection(p, sock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void deleteObservers() {
        observers.clear();
    }

    @Override
    public void notifyObservers() {
        if(hasChanged()) {
            for(Observer o : observers) {
                o.update(this, null);
            }
            clearChanged();
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        if(hasChanged()) {
            for(Observer o : observers) {
                o.update(this, arg);
            }
            clearChanged();
        }
    }

    @Override
    public int countObservers() {
        return observers.size();
    }

    @Override
    public void setChanged() {
        stateChanged = true;
    }

    @Override
    public void clearChanged() {
        stateChanged = false;
    }

    @Override
    public boolean hasChanged() {
        return stateChanged;
    }

    @Override
    public void init() {
        System.out.println("Serveur ready");
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
        System.out.print("(Serveur) Message reçu : '");
        String msg = (new String(p.getData())).trim();
        System.out.print(msg);
        System.out.println("'");
        System.out.println("IP : " + p.getAddress().getHostAddress());
        DialogProtocol message = new DialogProtocol(p);
        if(message.isAskingConnection()) {
            System.out.println("Client connecté");
//            establishConnection(p, getSocket());
            establishConnection(p);
        } else {
            setChanged();
            notifyObservers(p);
        }
    }

    // ===================== STATIC ========================

    private static int SERV_PORT = 4000;
    private static String SERV_IP = "127.0.0.1";
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
