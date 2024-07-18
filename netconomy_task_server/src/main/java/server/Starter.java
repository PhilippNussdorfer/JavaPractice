package server;

import server.tool.InputHandler;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Starter {
    private static InputHandler inputHandler = new InputHandler();

    public static void main(String[] args) throws SQLException {
        /*String key = "";
        SqlConnection sqlConnection = new SqlConnection(SqlConnector.connect("jdbc:sqlite:", "src/main/java/server/sqlite/database.sqlite3"));
        ResultSet res = sqlConnection.getKeyFromFile("ExternalInput.txt");
        while (res.next()) {
            System.out.println(key = res.getString("KEY"));
        }
        sqlConnection.closeConnection();*/

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

            new Server(socket, in, out, pathToDatabase, pathToOutputDir);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        /*FileReader<Map<Integer, List<String>>> reader = new XlsxReader();
        Map<Integer, List<String>> data = reader.readFile("src/main/resources/Input/ExternalInput.xlsx");

        for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
            for (String encrypted : entry.getValue()) {
                System.out.println(Encryptor.decrypt(key, encrypted));
            }
        }*/

        /*FileReader<String> reader = new TxtReader();
        String data = reader.readFile("src/main/resources/Input/ExternalInput.txt");

        System.out.println(Encryptor.decrypt(key, data));*/
    }
}
