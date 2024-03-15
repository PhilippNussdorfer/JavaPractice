package bbrz.textadventure.tools;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class StringFormatting {

    public String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }

    private String getPrintableCollection(String[] strArr) {
        return String.join(", ", strArr);
    }

    private String getPrintableCollection(List<String> strArr) {
        return String.join(", ", strArr);
    }
}
