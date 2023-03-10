import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private ArrayList<String> resp = new ArrayList<>();;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<String> sendMessage(String msg) {

        out.println(msg);
        resp.clear();
        String inputLine;
        try {
            while (!(inputLine = in.readLine()).isEmpty()) {
                resp.add(inputLine);
                System.out.println(inputLine);
                if (!in.ready()) break;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resp;
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}