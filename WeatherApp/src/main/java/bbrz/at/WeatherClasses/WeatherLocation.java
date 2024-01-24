package bbrz.at.WeatherClasses;

import lombok.Getter;

import java.util.List;

@Getter
public class WeatherLocation {
    private int id;
    private String location;
    private String deviceLocation;

    public WeatherLocation(int id, String location, String deviceLocation) {
        this.id = id;
        this.location = location;
        this.deviceLocation = deviceLocation;
    }

    public WeatherLocation(String ... data) {
        try {
            this.id = Integer.parseInt(data[0]);
            this.location = data[1];
            this.deviceLocation = data[2];
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.location + " - " + this.deviceLocation;
    }
}
