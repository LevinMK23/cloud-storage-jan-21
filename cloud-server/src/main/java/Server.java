import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class Server {
        public static final int PORT = 8082;
        private Set<ClientHandler> clientHandlers;

        public Server() {
            this(PORT);
        }

        public Server(int port) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                clientHandlers = new HashSet<>();
                while (true) {
                    System.out.println("Waiting for a connection...");
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket);
                    new ClientHandler(this, socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public synchronized void subscribe(ClientHandler ch) {
            clientHandlers.add(ch);
        }

        public synchronized void unsubscribe(ClientHandler ch) {
            clientHandlers.remove(ch);
        }
    }
