package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * used the website below as reference and help for this part of te application
 * https://www.geeksforgeeks.org/socket-programming-in-java/
 */
public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Server(int port) {
        try {
            serverStart(port);

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            serverLoop();

            closeConnection();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void serverStart(int port) throws IOException {
        server = new ServerSocket(port);

        System.out.println("Server started");
        System.out.println("Waiting for a client");

        socket = server.accept();
        System.out.println("Client accepted");
    }

    private void serverLoop() {
        String line = "";
        while (!line.equals("Over")) {
            try {
                line = in.readUTF();
                System.out.println(line);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void closeConnection() throws IOException {
        System.out.println("Closing connection");

        this.socket.close();
        this.in.close();
        this.out.close();
    }
}
