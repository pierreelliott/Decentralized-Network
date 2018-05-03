package TCPConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {

    public static void main(String[] arg) {
        try {
            ServerManager manager = new ServerManager(4000, 5);
            (new Thread(manager)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        (new Thread(() -> {

        })).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        (new Thread(() -> {
            Client client = new Client();
        })).start();
    }
}
