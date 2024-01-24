package bbrz.at.WeatherClasses;

import lombok.Getter;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Getter
public class WeatherData {
    private int locationId;
    private String date;
    private String time;
    private double temperature;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public WeatherData(int locationId, String date, String time, double temperature) {
        this.locationId = locationId;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
    }

    public WeatherData(String ... data) {
        try {
            this.locationId = Integer.parseInt(data[0]);
            this.date = data[1];
            this.time = data[2];
            this.temperature = Double.parseDouble(data[3]);
        } catch (NumberFormatException n) {
            n.printStackTrace();
        }
    }

    public static String getDataFromDataList(int locationId, List<WeatherData> dataList, String selectedDate, boolean exel) {
        StringBuilder strBuilder = new StringBuilder();
        for (WeatherData data : dataList) {
            if (selectedDate.equalsIgnoreCase("Show All")) {
                if (data.getLocationId() == locationId) {
                    buildString(strBuilder, data, exel);
                }
            } else if (selectedDate.equalsIgnoreCase("Last 7 Days")) {
                Date today = new Date();
                Date sevenDaysAgo = new Date(today.getTime() - 24 * 60 * 60 * 1000 * 7);
                try {
                    if (data.getLocationId() == locationId && (
                            (today.equals(format.parse(data.getDate())) || today.after(format.parse(data.getDate())) ) &&
                            (sevenDaysAgo.equals(format.parse(data.getDate())) || sevenDaysAgo.before(format.parse(data.getDate())) ))) {
                        buildString(strBuilder, data, exel);
                    }
                } catch (ParseException p) {
                    p.printStackTrace();
                }
            } else {
                if (data.getLocationId() == locationId && data.getDate().equals(selectedDate)) {
                    buildString(strBuilder, data, exel);
                }
            }
        }
        if (exel) {
            if (String.valueOf(strBuilder).length() > 0) {
                return String.valueOf(strBuilder).substring(0, strBuilder.length() - 1);
            }
        }
        return String.valueOf(strBuilder);
    }

    private static void buildString(StringBuilder strBuilder, WeatherData data, boolean exel) {
        if (exel) {
            strBuilder.append(data.date).append(",")
                    .append(data.getTemperature()).append(",")
                    .append(data.getTime()).append("|");
        } else {
            strBuilder.append("Date: ").append(data.date)
                    .append("\n").append("Temperature: ")
                    .append(data.temperature).append("\n")
                    .append("Time ").append(data.getTime().substring(0, 8)).append("\n\n");
        }
    }

    public static String getAvgMinMax(String selectedOption, List<WeatherData> dataList, int selectedLocation, String selectedDate) {

        if (selectedOption.equalsIgnoreCase("Average")) {
            return getAvgToString(filterList(dataList, selectedLocation, selectedDate));
        } else if (selectedOption.equalsIgnoreCase("Max")) {
            return getMaxMinToString(filterList(dataList, selectedLocation, selectedDate), true);
        } else if (selectedOption.equalsIgnoreCase("Min")) {
            return getMaxMinToString(filterList(dataList, selectedLocation, selectedDate), false);
        }
        return "";
    }
    private static List<WeatherData> filterList(List<WeatherData> dataList, int selectedLocation, String selectedDate) {
        List<WeatherData> filteredList = new ArrayList<>();
        Date today = new Date();
        Date sevenDaysAgo = new Date(today.getTime() - 24 * 60 * 60 * 1000 * 7);

        for (WeatherData data : dataList) {
            if (data.getDate().equalsIgnoreCase(selectedDate) && data.locationId == selectedLocation) {
                filteredList.add(data);
            }
            if (selectedDate.equalsIgnoreCase("show all") && data.locationId == selectedLocation) {
                filteredList.add(data);
            }
            try {
                if (selectedDate.equalsIgnoreCase("last 7 days") && data.locationId == selectedLocation &&
                        (today.equals(format.parse(data.getDate())) || today.after(format.parse(data.getDate())) ) &&
                        (sevenDaysAgo.equals(format.parse(data.getDate())) || sevenDaysAgo.before(format.parse(data.getDate())) )) {
                    filteredList.add(data);
                }
            } catch (ParseException p) {
                p.printStackTrace();
            }

        }
        return filteredList;
    }
    private static String getAvgToString(List<WeatherData> filteredList) {
        DecimalFormat formatDecimal = new DecimalFormat("##.##");
        double sum = 0;
        if (filteredList.size() > 0) {
            for (WeatherData data : filteredList) {
                sum += data.getTemperature();
            }
            return "Average of selected Values: \n" + formatDecimal.format(sum / filteredList.size());
        }
        return "No Values selected to Filter";
    }

    private static String getMaxMinToString(List<WeatherData> filteredList, boolean max) {
        List<Double> tmp = new ArrayList<>();
        Double minMaxValue;
        if (filteredList.size() > 0) {
            for (WeatherData data : filteredList) {
                tmp.add(data.getTemperature());
            }
            if (max) {
                minMaxValue = Collections.max(tmp);
                return "Max of the Selected Values: \n" + minMaxValue;
            } else {
                minMaxValue = Collections.min(tmp);
                return "Min of the Selected Values: \n" + minMaxValue;
            }
        }
        return "No Values selected to Filter";
    }

    public static double getAVGPerDate(List<Double> each) {
        double sum = 0;
        if (each.size() > 0) {
            for (double num : each) {
                sum += num;
            }
            return sum / each.size();
        }
        return sum;
    }
}
