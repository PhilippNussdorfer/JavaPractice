package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.sqlite.SqlConnection;
import server.sqlite.SqlConnector;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    Server server;

    String path = "";
    String databasePath = "C:\\Users\\BBRZ\\Desktop\\Philipp_Nußdorfer\\TaskNetconomy\\DB\\database.sqlite3";

    @BeforeEach
    void setUp() {
        server  = new Server(new SocketWrapper(), new DataInputStream(System.in), new DataOutputStream(System.out), path,
                             new SqlConnection(SqlConnector.connect("jdbc:sqlite:", databasePath)));
    }

    @Test
    void test() {

    }

    private static class SocketWrapper extends Socket implements Closeable {

        @Override
        public void close() throws IOException {

        }
    }
}