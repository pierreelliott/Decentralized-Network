package core;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public abstract class Utils extends CommInterface implements Runnable {

    public Utils(DatagramSocket o) throws Exception {
        super(o);
    }

    public Utils(int port) throws Exception {
        super(port);
    }

    public Utils(int port, InetAddress adr) throws Exception {
        super(port, adr);
    }

    @Override
    public void run() {
        try {
            init();
            while(true) {
                preprocess();
                process();
                postprocess();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /* ================== STATIC ======================== */

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
}
