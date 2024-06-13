package Bank.tools;

import Bank.customExceptions.InvalidInputException;

public class StringFormatter {
    public String concatStringArray(String ... arr) {
        return String.join(", ", arr);
    }

    public String formatStringLength(int length, String str) {
        return String.format("%1$-" + length + "s", str);
    }

    public String CheckIfElementExists(int index, String[] arr) throws InvalidInputException {
        String last;

        try {
            last = arr[index];
        } catch (IndexOutOfBoundsException exception) {
            throw new InvalidInputException("Input to short please check help and make sure the input is correct!");
        }

        return last;
    }
}
