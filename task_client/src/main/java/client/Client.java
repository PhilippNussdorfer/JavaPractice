package client;

import client.filereader.FileReader;
import client.filereader.TxtReader;
import client.filereader.XlsxReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client {
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private final String serverAddress;
    private final int port;
    private final String pathToInputDir;

    public Client(int port, String serverAddress, String pathToInputDir) {
        this.port = port;
        this.serverAddress = serverAddress;
        this.pathToInputDir = pathToInputDir;

        connectToServer();
        clientLoop();
        closeConnection();
    }

    private void clientLoop() throws MissingFormatArgumentException {
        List<String> encryptedList = readEncryptedFile();
        if (encryptedList.isEmpty())
            throw new MissingFormatArgumentException("List is empty, check if the given input file was empty");

        try {
            String line = "";
            out.writeUTF(pathToInputDir + ";" + String.join(";", encryptedList));

            while (!line.equals("exit")) {

                line = in.readUTF();
                System.out.println(line);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private List<String> readEncryptedFile() {
        List<String> encryptedList = new ArrayList<>();

        if (pathToInputDir.toLowerCase().endsWith(".xlsx")) {
            FileReader<Map<Integer, List<String>>> fileReader = new XlsxReader();
            Map<Integer, List<String>> result = fileReader.readFile(pathToInputDir);

            for (Map.Entry<Integer, List<String>> entry : result.entrySet()) {
                encryptedList.addAll(entry.getValue());
            }
        }

        if (pathToInputDir.toLowerCase().endsWith(".txt")) {
            FileReader<String> fileReader = new TxtReader();

            encryptedList.add(fileReader.readFile(pathToInputDir));
        }

        return encryptedList;
    }

    private void connectToServer() {
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected");

            initOutAndInput();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void initOutAndInput() throws IOException {
        in = new DataInputStream(socket.getInputStream());
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
