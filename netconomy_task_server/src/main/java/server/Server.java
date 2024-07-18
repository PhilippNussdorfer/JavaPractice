package server;

import server.encryption.suite.Encryptor;
import server.filewriter.FileWriter;
import server.filewriter.TxtWriter;
import server.filewriter.XlsxWriter;
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
    private final String pathToOutputDir;
    private final int EXCEL_COLUMNS = 3;
    private final SqlConnection sqlite;

    public Server(Socket socket, DataInputStream in, DataOutputStream out, String pathToOutputDir, SqlConnection sqlite) {
        this.SOCKET = socket;
        this.IN = in;
        this.OUT = out;
        this.pathToOutputDir = pathToOutputDir;
        this.sqlite = sqlite;

        try {
            serverOperation();
            closeConnection();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void serverOperation() {
        try {
            StringBuilder builder = new StringBuilder();
            String inputFile = IN.readUTF();
            String[] splitString = inputFile.split(";");        // The file should always be the first

            Path path = Paths.get(splitString[0]);
            ResultSet result = sqlite.getKeyFromFile(path.getFileName().toString());
            String key = result.getString("KEY");

            OUT.writeUTF("Starting Decrypting ...");
            decryptAndAppendOnStringBuilder(builder, splitString, key);
            OUT.writeUTF("Finished Decrypting!");

            handleFileSavingAndZipping(splitString, builder.substring(0, builder.length() - 1));
            sqlite.writeLog("Decrypted " + path.getFileName() + " Saved the decrypted and zipped file in " + pathToOutputDir + " .");

            OUT.writeUTF("exit");

        } catch (IOException | SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private void decryptAndAppendOnStringBuilder(StringBuilder builder, String[] splitString, String key) {
        for (String str : splitString) {
            if (!str.equals(splitString[0])) {
                builder.append(Encryptor.decrypt(key, str)).append(";");
            }
        }
    }

    private void handleFileSavingAndZipping(String[] splitString, String content) throws IOException {
        if (splitString[0].toLowerCase().endsWith(".xlsx")) {
            FileWriter fileWriter = new XlsxWriter(EXCEL_COLUMNS);
            writeFileAndReturnResponseToClient(splitString, content, fileWriter);
        }

        else if (splitString[0].toLowerCase().endsWith(".txt")) {
            FileWriter fileWriter = new TxtWriter();
            writeFileAndReturnResponseToClient(splitString, content, fileWriter);
        }

        else {
            OUT.writeUTF("Saving and zipping failed!");
        }
    }

    private void writeFileAndReturnResponseToClient(String[] splitString, String content, FileWriter fileWriter) throws IOException {
        if (fileWriter.writeInFile(pathToOutputDir, splitString[0], content)) {
            FileWriter.zipFile(pathToOutputDir, splitString[0]);
            OUT.writeUTF("Saved and zipped file!");
        }
        else
            OUT.writeUTF("An error occurred while trying to save and zip the file!");
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
