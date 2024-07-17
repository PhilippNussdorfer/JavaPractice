package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final Socket SOCKET;
    private final DataInputStream IN;
    private final DataOutputStream OUT;

    public Server(Socket socket, DataInputStream in, DataOutputStream out) {
        this.SOCKET = socket;
        this.IN = in;
        this.OUT = out;

        try {
            serverLoop();
            closeConnection();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void serverLoop() {
        String line = "";
        while (!line.equals("Over")) {
            try {
                line = IN.readUTF();
                System.out.println(line);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Closing connection");

        this.SOCKET.close();
        this.IN.close();
        this.OUT.close();
    }

    public static Socket serverStart(int port) {
        try {
            ServerSocket server = new ServerSocket(port);

            System.out.println("Server started");
            System.out.println("Waiting for a client");

            Socket socket = server.accept();
            System.out.println("Client accepted");

            return socket;
        } catch (IOException | SecurityException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
