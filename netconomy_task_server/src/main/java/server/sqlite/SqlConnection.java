package server.sqlite;

import lombok.AllArgsConstructor;

import java.sql.*;

@AllArgsConstructor
public class SqlConnection implements SqlConnector {
    private final Connection conn;

    public ResultSet getKeyFromFile(String file) throws SQLException {
        throwExceptionWhenConnectionIsNull();

        PreparedStatement prepStmt = conn.prepareStatement("SELECT KEY FROM AES_KEYS WHERE FILE = ?;");
        prepStmt.setString(1, file);
        return prepStmt.executeQuery();
    }

    private void throwExceptionWhenConnectionIsNull() throws SQLException {
        if (conn == null)
            throw new SQLException("There is no connection to an Database");
    }

    public void writeLog(String log) throws SQLException {
        throwExceptionWhenConnectionIsNull();

        PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO LOG (log) VALUES (?);");
        prepStmt.setString(1, log);
        if (prepStmt.execute())
            System.out.println("Created log");
    }

    @Override
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Closed connection");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
