import core.Utils;
import protocol.DialogProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
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

    /*@Override
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
                String msg = (new String(p.getData())).trim();
                System.out.print(msg);
                System.out.println("'");
                DialogProtocol message = new DialogProtocol(p);
                if(message.isAskingConnection()) {
                    System.out.println("Client connecté");
                    establishConnection(p);
                }
                setChanged();
                notifyObservers(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public void establishConnection(DatagramPacket p) {
        Comm l = new Comm(p.getAddress(), p.getPort(), getSocket());
        addObserver(l);
        (new Thread(l)).start();
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

    // ===================== STATIC ========================

    public static void main(String[] args) {
        int PORT = 4000;
        try {
            InetAddress adr = InetAddress.getByName("127.0.0.2");
            Serveur serv = new Serveur(adr, PORT);
            (new Thread(serv)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        System.out.print("Message reçu : '");
        String msg = (new String(p.getData())).trim();
        System.out.print(msg);
        System.out.println("'");
        DialogProtocol message = new DialogProtocol(p);
        if(message.isAskingConnection()) {
            System.out.println("Client connecté");
            establishConnection(p);
        }
        setChanged();
        notifyObservers(p);
    }
}
