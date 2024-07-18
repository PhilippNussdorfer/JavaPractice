package server;

import server.encryption.suite.Encryptor;
import server.sqlite.SqlConnection;
import server.sqlite.SqlConnector;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    private final Socket SOCKET;
    private final DataInputStream IN;
    private final DataOutputStream OUT;
    private final String pathToDatabase;
    private String pathToOutputDir;

    public Server(Socket socket, DataInputStream in, DataOutputStream out, String pathToDatabase, String pathToOutputDir) {
        this.SOCKET = socket;
        this.IN = in;
        this.OUT = out;
        this.pathToDatabase = pathToDatabase;
        this.pathToOutputDir = pathToOutputDir;

        try {
            serverOperation();
            closeConnection();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void serverOperation() {
        SqlConnection sqlite = new SqlConnection(SqlConnector.connect("jdbc:sqlite:", pathToDatabase));

        try {
            StringBuilder builder = new StringBuilder();
            String inputFile = IN.readUTF();
            String[] splitString = inputFile.split(";");        // first should always be the file

            Path path = Paths.get(splitString[0]);
            ResultSet result = sqlite.getKeyFromFile(path.getFileName().toString());
            String key = result.getString("KEY");

            OUT.writeUTF("Starting Decrypting ...");

            for (String str : splitString) {
                if (!str.equals(splitString[0])) {
                    builder.append(Encryptor.decrypt(key, str)).append(";");
                }
            }

            sqlite.writeLog("Decrypted " + path.getFileName() + ".");
            OUT.writeUTF("Finished Decrypting!");
            OUT.writeUTF(builder.substring(0, builder.length() - 1));
            OUT.writeUTF("exit");

        } catch (IOException | SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private void closeConnection() throws IOException {
        System.out.println("Closing connection");

        this.SOCKET.close();
        this.IN.close();
        this.OUT.close();
    }

    public static Socket serverStart(int portNumber) {
        try {
            ServerSocket server = new ServerSocket(portNumber);

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
