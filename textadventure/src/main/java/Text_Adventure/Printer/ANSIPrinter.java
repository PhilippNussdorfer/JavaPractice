package Text_Adventure.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

public class ANSIPrinter {

    private final String picName, pictureFormat;
    private final String reset = "\033[0m";

    public ANSIPrinter(String picName, String pictureFormat) {
        this.picName = picName;
        this.pictureFormat = pictureFormat;
    }

    public String getPicName() {return picName;}

    public void print() {
        char[] hexArr = "0123456789ABCDEF".toCharArray();

        List<String> picFormatList = new ArrayList<>(List.of(this.pictureFormat.split("(?<=\\G.{4})")));
        int width = Integer.parseInt(picFormatList.get(0));

        List<String> colorsList = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            colorsList.add(picFormatList.get(i));
        }

        List<String> hexList = new ArrayList<>();
        for (int i = 17; i < picFormatList.size(); i++) {
            hexList.add(picFormatList.get(i).substring(3));
        }

        Map<String, String> colorShem = new HashMap<>();
        for (int i = 0; i < hexArr.length; i++) {
            int colorInt = Integer.parseInt(colorsList.get(i));
            colorShem.put(String.valueOf(hexArr[i]), "\033[48;5;" + (colorInt) + "m");
        }


        int counter = 0;
        for (String hex : hexList) {
            System.out.print(colorShem.get(hex) + "   " + this.reset);
            counter ++;
            if (counter == width) {
                counter = 0;
                System.out.println();
            }
        }
        System.out.println();
    }

    public void printImport() {

        char[] hexArr = "0123456789ABCDEF".toCharArray();
        char[] charHex = this.pictureFormat.substring(52).toCharArray();
        int width = Integer.parseInt(this.pictureFormat.substring(2, 4), 16);
        List<String> colorsList = List.of(this.pictureFormat.substring(4, 52).split("(?<=\\G.{3})"));

        Map<Character, String> colorShem = new HashMap<>();
        for (int i = 0; i < hexArr.length; i++) {
            int colorInt = Integer.parseInt(colorsList.get(i));
            colorShem.put((hexArr[i]), "\033[48;5;" + (colorInt) + "m");
        }

        int counter = 0;
        for (char hex : charHex) {
            System.out.print(colorShem.get(hex) + "   " + this.reset);
            counter ++;
            if (counter == width) {
                counter = 0;
                System.out.println();
            }
        }
        System.out.println();
    }
}
