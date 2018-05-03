package TCPConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends ServerManager {

    PrintWriter out;
    BufferedReader in;

    public Server(Socket sock) {
        super(sock);
    }

    @Override
    public void preprocess() {
        try {
            out = new PrintWriter(getSocket().getOutputStream());
            in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process() {
        String text;
        try {
            text = in.readLine();
            System.out.println("Texte reçu : " + text);
            out.println("Bien reçu : '" + text + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
