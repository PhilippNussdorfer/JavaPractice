package server;

import server.sqlite.SqlConnection;
import server.sqlite.SqlConnector;
import server.tool.InputHandler;

import java.io.*;
import java.net.Socket;

public class Starter {
    private static final InputHandler inputHandler = new InputHandler();

    public static void main(String[] args) {
        try {
            int port = inputHandler.askForPort();
            String pathToDatabase = inputHandler.fileOrDirExists("Please enter the Path to the Database", "Could not find the File \n");
            String pathToOutputDir = inputHandler.fileOrDirExists("Please enter the directory path of the output", "Could not access the given path\n");

            Socket socket = Server.serverStart(port);

            if (socket == null) {
                System.out.println("The socket is null from the server start!");
                return;
            }

            DataOutputStream out = new DataOutputStream((socket.getOutputStream()));
            DataInputStream in = new DataInputStream(socket.getInputStream());

            new Server(socket, in, out, pathToOutputDir, new SqlConnection(SqlConnector.connect("jdbc:sqlite:", pathToDatabase)));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
