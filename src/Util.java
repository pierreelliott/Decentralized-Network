import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class Util {

    protected DatagramSocket ds;

    public Util() throws SocketException {
        ds = new DatagramSocket();
    }

    public Util(int port) throws SocketException {
        ds = new DatagramSocket(port);
    }

    public Util(DatagramSocket d) {
        ds = d;
    }

    public static List<Integer> scan(int dep, int fin) {
        List<Integer> list = new ArrayList<>();
        DatagramSocket ds = null;
        for(int i = dep; i <= fin; i++) {
            try {
                ds = new DatagramSocket(i);
            } catch (Exception ex) {
                list.add(i);
            } finally {
                if(ds != null) {
                    ds.close();
                }
                ds = null;
            }
        }
        return list;
    }

    public static boolean isOccuped(int port) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(port);
        } catch (Exception ex) {
            return false;
        } finally {
            if(ds != null) {
                ds.close();
            }
        }
        return true;
    }

    public boolean envoyer(String data, InetAddress ip, int port) {

        DatagramPacket dp = new DatagramPacket(data.getBytes(), data.length(), ip, port);

        try {
            ds.send(dp);
        } catch (IOException e) {
            System.err.println("Erreur envoi :\n" + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean envoyer(String data, byte[] ip, int port) {
        InetAddress ad = null;
        try {
            ad = InetAddress.getByAddress(ip);
        } catch (UnknownHostException e) {
            System.err.println("Erreur résolution adresse IP");
            return false;
        }

        return envoyer(data, ad, port);
    }

}
