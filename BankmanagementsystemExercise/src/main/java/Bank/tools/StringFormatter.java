package Bank.tools;

public class StringFormatter {
    public String concatStringArray(String ... arr) {
        return String.join(", ", arr);
    }

    public String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }
}
