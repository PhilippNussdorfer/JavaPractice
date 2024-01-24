package bbrz.at;

import bbrz.at.WeatherClasses.WeatherData;
import bbrz.at.WeatherClasses.WeatherLocation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Weather_App {
    private JPanel mainView;
    private JComboBox selectLocation;
    private JTextPane txtOutput;
    private JScrollPane scrollBar;
    private JComboBox selectDate;
    private JButton btnShowLineChart;
    private JComboBox selectFromBox;
    private JComboBox selectToBox;
    private JButton btnExportToExel;
    private JButton btnPrint;
    private JButton btnImport;
    private JTextPane txtOutputForAvgMinMax;
    private JComboBox selectAvgMinMax;
    private int selectedLocationId;
    private String selectedDate;
    private final mariaDBConn db = new mariaDBConn();
    private List<WeatherData> weatherDataList = db.SelectWeatherData();
    private List<WeatherLocation> weatherLocationList = db.SelectLocations();
    private final List<WeatherData> newWeatherDataList = new ArrayList<>();
    private final List<WeatherLocation> newWeatherLocationList = new ArrayList<>();
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final int oneDay = 24 * 60 * 60 * 1000;

    public Weather_App() {
        init();
        selectLocation.addActionListener(e -> {
            var item = selectLocation.getSelectedItem();
            selectedLocationId = ((WeatherLocation)item).getId();
            txtOutput.setText(WeatherData.getDataFromDataList(selectedLocationId, weatherDataList, selectedDate, false));
            txtOutputForAvgMinMax.setText("");
            selectAvgMinMax.setSelectedIndex(0);
        });

        selectDate.addActionListener(e -> {
            var item = selectDate.getSelectedItem();
            selectedDate = String.valueOf(item);
            txtOutput.setText(WeatherData.getDataFromDataList(selectedLocationId, weatherDataList, selectedDate, false));
            txtOutputForAvgMinMax.setText("");
            selectAvgMinMax.setSelectedIndex(0);
        });

        btnShowLineChart.addActionListener(e -> {
            try {
                showLineChartWindow();
            } catch (ParseException p) {
                p.printStackTrace();
            }
        });

        btnExportToExel.addActionListener(e -> {
            fileSaveDialog();
        });

        btnPrint.addActionListener(e -> {
            printData(WeatherData.getDataFromDataList(selectedLocationId, weatherDataList, selectedDate, false));
        });

        btnImport.addActionListener(e -> {
            importWeatherData();
        });

        selectAvgMinMax.addActionListener(e -> {
            String selectedOption = Objects.requireNonNull(selectAvgMinMax.getSelectedItem()).toString();
            String returnValue = WeatherData.getAvgMinMax(selectedOption, weatherDataList, selectedLocationId, selectedDate);
            txtOutputForAvgMinMax.setText(returnValue);
        });
    }

    private void importWeatherData() {
        JFrame frame = new JFrame();
        List<String> newData = new ArrayList<>();
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("*.csv", "csv");
        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Choose the CSV File");
        chooser.addChoosableFileFilter(csvFilter);
        chooser.setFileFilter(csvFilter);
        int approved = chooser.showOpenDialog(frame);

        if (approved == JFileChooser.APPROVE_OPTION) {
            newData = FileWriterReader.readTextFile(chooser.getSelectedFile());
        }

        for (String dataStr : Objects.requireNonNull(newData)) {
            System.out.println(dataStr);
            String[] dataArr = dataStr.split("");
            //id, location, deviceLocation
            this.newWeatherLocationList.add(new WeatherLocation(dataArr[0], dataArr[1], dataArr[2]));
            //locationID, date, time, temperature
            this.newWeatherDataList.add(new WeatherData(dataArr[3], dataArr[4], dataArr[5], dataArr[6]));
        }

        boolean answer = checkIfDataExists();
        System.out.println(answer);
        for (WeatherData data : this.newWeatherDataList) {
            System.out.println(data.getDate() + " - " + data.getTemperature());
        }
        this.weatherDataList = db.SelectWeatherData();
        this.weatherLocationList = db.SelectLocations();
    }

    private boolean confirmDialog() {
        boolean retValue = false;
        //JOptionPane: 0 == OK, 1 == NO, 2 == cancel
        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to import this will override some of the old data");
        if (confirm == 0) {
            retValue = true;
        }
        return retValue;
    }

    private boolean checkIfDataExists() {
        for (WeatherData existingData : this.weatherDataList) {
            for (WeatherData newData : this.newWeatherDataList) {
                if (existingData.getDate().equalsIgnoreCase(newData.getDate()) && existingData.getTime().equalsIgnoreCase(newData.getTime())) {
                    return confirmDialog();
                }
            }
        }
        return true;
    }

    private void showLineChartWindow() throws ParseException {
        List<Double> temperatureList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        Map<String, List<Double>> tmpForAVG = new HashMap<>();
        Date from, to;

        if (!String.valueOf(selectFromBox.getSelectedItem()).equalsIgnoreCase("None")) {
            from = format.parse(String.valueOf(selectFromBox.getSelectedItem()));
        } else {
            from = new Date();
        }
        if (!String.valueOf(selectToBox.getSelectedItem()).equalsIgnoreCase("None")) {
            to = format.parse(String.valueOf(selectToBox.getSelectedItem()));
        } else {
            to = new Date(from.getTime() - this.oneDay * 7);
        }

        for (WeatherData data : weatherDataList) {
            if (!dateList.contains(data.getDate())) {
                if ( (from.equals(format.parse(data.getDate())) || from.after(format.parse(data.getDate())) ) &&
                        (to.equals(format.parse(data.getDate())) || to.before(format.parse(data.getDate())) ) ) {
                    dateList.add(data.getDate());
                }
            }
        }
        for (WeatherData tmpData : weatherDataList) {
            if (tmpData.getLocationId() == selectedLocationId && dateList.contains(tmpData.getDate())) {
                if (tmpForAVG.containsKey(tmpData.getDate())) {
                    tmpForAVG.get(tmpData.getDate()).add(tmpData.getTemperature());
                } else {
                    tmpForAVG.put(tmpData.getDate(), new ArrayList<>(List.of(tmpData.getTemperature())));
                }
            }
        }
        tmpForAVG.forEach((key, value) -> {
            temperatureList.add(WeatherData.getAVGPerDate(value));
        });

        LineChart panel = new LineChart(temperatureList, dateList);
        panel.setLineColor(Color.CYAN);
        panel.setPointColor(Color.magenta);
        panel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawnGraph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void init() {
        fillSelectionBox();

        var item = selectDate.getSelectedItem();
        selectedDate = String.valueOf(item);

        item = selectLocation.getSelectedItem();
        selectedLocationId = ((WeatherLocation)item).getId();

        txtOutput.setText(WeatherData.getDataFromDataList(selectedLocationId, weatherDataList, selectedDate, false));
    }

    public void run() {
        JFrame frame = new JFrame("Weather App");
        frame.setContentPane(mainView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setVisible(true);
    }

    private void fillSelectionBox() {
        for (WeatherLocation location : weatherLocationList) {
            selectLocation.addItem(location);
        }
        fillDateBox(selectDate, true, "Last 7 Days", "Show All");
        fillDateBox(selectFromBox, true, "None");
        fillDateBox(selectToBox, true, "None");
        fillDateBox(selectAvgMinMax, false, "None", "Average", "Min", "Max");
    }
    private void fillDateBox(JComboBox dateBox, boolean addDates, String ... defaultSetting) {
        List<String> tmp = new ArrayList<>();
        for (String defSetting : defaultSetting) {
            dateBox.addItem(defSetting);
        }
        if (addDates) {
            for (WeatherData data : weatherDataList) {
                if (!tmp.contains(data.getDate())) {
                    dateBox.addItem(data.getDate());
                    tmp.add(data.getDate());
                }
            }
        }
    }

    private void fileSaveDialog() {
        JFrame frame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("*.csv", "csv");

        fileChooser.addChoosableFileFilter(csvFilter);
        fileChooser.setFileFilter(csvFilter);
        fileChooser.setSelectedFile(new File("myFile.csv"));
        fileChooser.setDialogTitle("Choose where to save");

        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File path = fileChooser.getSelectedFile();

            if (!path.toString().endsWith(".csv")) {
                path = new File(path + ".csv");
            }

            if (!path.exists()) {
                FileWriterReader.createFile(path);
            }
            FileWriterReader.writeFile(path, getListFromString(WeatherData.getDataFromDataList(selectedLocationId, weatherDataList, selectedDate, true)));
            try {
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void printData(String dataString) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new Printer(dataString));
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException p) {
                p.printStackTrace();
            }
        }
    }

    /**
     * Needs a String with "|" to Split the string;
     * @return a String List;
     */
    private List<String> getListFromString (String toList) {
        List<String> list = new ArrayList<>();
        list.add(((WeatherLocation)selectLocation.getSelectedItem()).getLocation() + ",-," + (((WeatherLocation) selectLocation.getSelectedItem()).getDeviceLocation()));
        list.add("Date,Temperature,Time");
        list.addAll(Arrays.stream(toList.split("\\|")).toList());
        return list;
    }
}
