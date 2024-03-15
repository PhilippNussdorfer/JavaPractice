package bbrz.textadventure.tools;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class StringFormatting {

    public String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }

    public String getPrintableCollection(String[] strArr) {
        return String.join(", ", strArr);
    }

    public String getPrintableCollection(List<String> strArr) {
        return String.join(", ", strArr);
    }
}
