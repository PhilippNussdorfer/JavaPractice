package bbrz.textadventure.tools;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringFormatting {

    public String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }
}
