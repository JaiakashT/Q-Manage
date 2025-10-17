import java.io.*;
import java.net.*;
import java.util.*;

public class QueueServer {
    private static int currentTicket = -1; // start before 0
    private static final List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000); // port
        System.out.println(" Queue Server started on port 5000...\n");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(" New client connected: " + clientSocket);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            clients.add(out);

            out.println("TICKET:" + (currentTicket == -1 ? "---" : currentTicket));

            new Thread(() -> handleClient(clientSocket, out)).start();
        }
    }

    private static void handleClient(Socket socket, PrintWriter out) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String input;
            while ((input = in.readLine()) != null) {
                if (input.equals("NEXT")) {
                    currentTicket++;
                    broadcast("TICKET:" + currentTicket);
                } else if (input.equals("RESET")) {
                    currentTicket = -1;
                    broadcast("TICKET:---");
                }
            }
        } catch (IOException e) {
            System.out.println("⚠ Client disconnected.");
            clients.remove(out);
        }
    }

    private static void broadcast(String message) {
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }
}