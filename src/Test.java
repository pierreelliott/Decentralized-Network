import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Test {

    public static void main(String[] zero) {
        (new Thread(() -> {
            ServerSocket socketserver  ;
            Socket socketduserveur ;
            try {
                socketserver = new ServerSocket(4000);
                socketduserveur = socketserver.accept();
                System.out.println("Un zéro s'est connecté !");
                PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());
                out.println("Hi fellow !");
                socketserver.close();
                socketduserveur.close();

            }catch (IOException e) {
                e.printStackTrace();
            }
        })).start();


        (new Thread(() -> {
            Socket socket;
            try {
                socket = new Socket(InetAddress.getLocalHost(),4000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String text = "!";
//                text = in.readLine();
                System.out.println("Texte : " + text);
                while(text.equalsIgnoreCase("!")) {
                    text = in.readLine();
                }
                System.out.println(text);
                socket.close();
            }catch (UnknownHostException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        })).start();
    }
}
