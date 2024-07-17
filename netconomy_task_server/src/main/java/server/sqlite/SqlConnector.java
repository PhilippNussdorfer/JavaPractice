package server.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface SqlConnector {
    static Connection connect(String db, String url) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(db + url);
            System.out.println("Connected to the database");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return conn;
    }

    void closeConnection();
}
