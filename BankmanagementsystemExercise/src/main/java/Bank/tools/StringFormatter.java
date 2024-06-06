package Bank.tools;

public class StringFormatter {
    public String concatStringArray(String ... arr) {
        return String.join(", ", arr);
    }

    public String formatStringLength(int length, String str) {
        return String.format("%1$-" + length + "s", str);
    }
}
