package bbrz.at;

import bbrz.at.WeatherClasses.WeatherData;
import bbrz.at.WeatherClasses.WeatherLocation;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public class mariaDBConn {

    private final String protocol = "jdbc:mysql://";
    private final String hostName = "localhost";
    private final String password = "";
    private final String port = "3306";
    private final String user = "root";
    private final String dbName = "weatherapp";

    private Connection conn() {
        try {
            return DriverManager.getConnection(protocol + hostName + ":" + port + "/" + dbName + "?user=" + user + "&password=" + password);
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return null;
    }

    private void notifier(int cnt) {
        if (cnt > 0) {
            System.out.println("Insert Success");
        } else {
            System.out.println("Insert failed");
        }
    }

    public void insertStringArrayIntoDB(String query, String ... inserts) {
        try {
            PreparedStatement pStmt = Objects.requireNonNull(conn()).prepareStatement(query);
            for (int i = 0; i < inserts.length; i++) {
                pStmt.setString(i + 1, inserts[i]);
            }
            int cnt = pStmt.executeUpdate();
            notifier(cnt);

            pStmt.close();
            Objects.requireNonNull(conn()).close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertIntoWeatherData(String query, int id, String date, Time time, BigDecimal temperature) {
        try {
            PreparedStatement pStmt = Objects.requireNonNull(conn()).prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setString(2, date);
            pStmt.setTime(3, time);
            pStmt.setBigDecimal(4, temperature);

            int cnt = pStmt.executeUpdate();
            notifier(cnt);

            pStmt.close();
            Objects.requireNonNull(conn()).close();
        } catch (SQLException s) {
            throw  new RuntimeException(s);
        }
    }

    public List<WeatherData> SelectWeatherData() {
        List<WeatherData> data = new ArrayList<>();
        String query = "SELECT * FROM weather_data;";
        try {
            PreparedStatement pStmt = Objects.requireNonNull(conn()).prepareStatement(query);
            ResultSet res = pStmt.executeQuery();
            while (res.next()) {
                data.add(new WeatherData(res.getInt("location_id"), res.getString("date"), res.getString("time"), res.getDouble("temperature")));
            }

            pStmt.close();
            Objects.requireNonNull(conn()).close();
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
        return data;
    }

    public List<WeatherLocation> SelectLocations() {
        List<WeatherLocation> data = new ArrayList<>();
        String query = "SELECT * FROM locations;";
        try {
            PreparedStatement pStmt = Objects.requireNonNull(conn()).prepareStatement(query);
            ResultSet res = pStmt.executeQuery();
            while (res.next()) {
                data.add(new WeatherLocation(res.getInt("id"), res.getString("location"), res.getString("measurement_device_in_location")));
            }

            pStmt.close();
            Objects.requireNonNull(conn()).close();
        } catch (SQLException s) {
            throw new RuntimeException(s);
        }
        return data;
    }
}
