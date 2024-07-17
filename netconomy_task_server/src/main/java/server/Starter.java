package server;

import server.encryption.suite.Encryptor;
import server.filereader.FileReader;
import server.filereader.TxtReader;
import server.sqlite.SqlConnection;
import server.sqlite.SqlConnector;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Starter {
    public static void main(String[] args) throws SQLException {
        /*String key = "";
        SqlConnection sqlConnection = new SqlConnection(SqlConnector.connect("jdbc:sqlite:", "src/main/java/server/sqlite/database.sqlite3"));
        ResultSet res = sqlConnection.getKeyFromFile("ExternalInput.txt");
        while (res.next()) {
            System.out.println(key = res.getString("KEY"));
        }
        sqlConnection.closeConnection();*/

        try {
            Socket socket = Server.serverStart(5000);
            if (socket == null) {
                System.out.println("The socket is null from the server start!");
                return;
            }

            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            new Server(socket, in, out);
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
