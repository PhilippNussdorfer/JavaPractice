package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Client(String address, int port) {
        connectToServer(address, port);

        clientLoop();

        closeConnection();
    }

    private void clientLoop() {
        String line = "";
        while (!line.equals("Over")) {
            try {
                line = in.readLine();
                out.writeUTF(line);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void connectToServer(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            initOutAndInput();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void initOutAndInput() throws IOException {
        in = new DataInputStream(System.in);
        out = new DataOutputStream(socket.getOutputStream());
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
