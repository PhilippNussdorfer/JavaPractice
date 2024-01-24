package bbrz.at;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Weather_App app = new Weather_App();
        app.run();

        // to fill the Database with test Values ->
        // fillDatabase();
    }

    private static void fillDatabase() {
        mariaDBConn db = new mariaDBConn();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Time time;

        List<String[]> locations = new ArrayList<>();
        locations.add(new String[] {"Linz", "south"});
        locations.add(new String[] {"Linz", "middle"});
        locations.add(new String[] {"Linz", "east"});
        locations.add(new String[] {"Stratberg", "north"});
        locations.add(new String[] {"Stratberg", "south"});
        locations.add(new String[] {"Kapfenberg", "middle"});
        locations.add(new String[] {"Kapfenberg", "east"});
        locations.add(new String[] {"Kapfenberg", "west"});
        locations.add(new String[] {"Kapfenberg", "north"});
        locations.add(new String[] {"Wien", "Middle"});
        locations.add(new String[] {"Wien", "north"});
        locations.add(new String[] {"Wien", "west"});

        BigDecimal bdc;
        Random rnd = new Random();

        for (int j = 0; j < 31; j++) {
            int insertCnt = 3;
            for (String[] strArr : locations) {
                // only used for Adding new Locations->
                // db.insertStringArrayIntoDB("INSERT INTO locations (location, measurement_device_in_location) VALUES (?, ?);", strArr);
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        time = new Time(6, 0, 0);
                    } else {
                        time = new Time(14, 0, 0);
                    }
                    bdc = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(15, 40));
                    String strDate = format.format(date);
                    db.insertIntoWeatherData("INSERT INTO weather_data (location_id, date, time, temperature) VALUES (?, ?, ?, ?);", insertCnt, strDate, time, bdc);
                }
                insertCnt++;
            }
            date = new Date(date.getTime() - 24 * 60 * 60 * 1000);
        }
    }
}