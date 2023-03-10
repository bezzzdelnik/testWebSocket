import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private GreetClient client;

    public void start(int port) {

        client = new GreetClient();
        client.startConnection("127.0.0.1", 5250);

        String greeting = null;
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                ArrayList<String> response = client.sendMessage(inputLine.replaceAll("\\\"NM", "\\\""));

                for (String s: response) {
                    out.println(s);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}