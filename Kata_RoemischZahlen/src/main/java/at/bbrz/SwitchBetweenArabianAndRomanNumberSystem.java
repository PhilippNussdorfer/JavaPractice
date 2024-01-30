package at.bbrz;

import at.bbrz.RomanNumbers.RomanNumbers;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SwitchBetweenArabianAndRomanNumberSystem {

    private final List<RomanNumbers> currentRomanNumbers = new ArrayList<>(
            List.of(RomanNumbers.I,
                    RomanNumbers.V,
                    RomanNumbers.X,
                    RomanNumbers.L,
                    RomanNumbers.C,
                    RomanNumbers.D,
                    RomanNumbers.M,
                    RomanNumbers.N)
    );
    private final List<Character> listOfBetwixtNumbers = new ArrayList<>(List.of('V', 'L', 'D'));

    public SwitchBetweenArabianAndRomanNumberSystem() {
        sortRomanNumbersAsc();
    }

    public int getArabianNumber(String romanNumber) {
        if (isRomanNumber(romanNumber)) {
            char[] arrayOfRomanDigits = romanNumber.toUpperCase().toCharArray();
            List<List<RomanNumbers>> listOfRomanDigits;

            listOfRomanDigits = splitRomanNumberInLists(arrayOfRomanDigits);
            return calculateArabianNumber(listOfRomanDigits);
        }
        throw new IllegalArgumentException("This is not an roman number!");
    }

    private int calculateArabianNumber(List<List<RomanNumbers>> listOfRomanDigits) {
        int result = 0;
        boolean skip = false;
        List<RomanNumbers> currentPointer = null, prevPointer = null;

        for (int i = 0; i < listOfRomanDigits.size(); i++) {
            if (i - 1 >= 0) {
                currentPointer = listOfRomanDigits.get(i);
                prevPointer = listOfRomanDigits.get(i - 1);

                int currentValue = currentPointer.get(0).getValue();
                int prevValue = prevPointer.get(0).getValue();

                if (prevValue < currentValue) {
                    result += getValueFromList(currentPointer) - getValueFromList(prevPointer);
                    skip = true;
                } else {
                    if (skip) {
                        skip = false;
                    } else {
                        result += getValueFromList(prevPointer);
                    }
                }
            }
        }

        if (prevPointer != null && prevPointer.get(0).getValue() > currentPointer.get(0).getValue()) {
            result += getValueFromList(currentPointer);
        } else if (prevPointer == null && currentPointer == null) {
            result += getValueFromList(listOfRomanDigits.get(0));
        }
        return result;
    }

    private List<List<RomanNumbers>> splitRomanNumberInLists(char[] myRomanNumber) {
        List<List<RomanNumbers>> listOfRomanDigits = new ArrayList<>();
        List<RomanNumbers> tmpRomanNumbersList = new ArrayList<>();

        for (char number : myRomanNumber) {
            var num = getRomanNumberWithLatter(number);

            if (!tmpRomanNumbersList.isEmpty()) {

                if (!tmpRomanNumbersList.contains(num)) {
                    listOfRomanDigits.add(new ArrayList<>(tmpRomanNumbersList));
                    tmpRomanNumbersList.clear();
                    tmpRomanNumbersList.add(num);

                } else {
                    tmpRomanNumbersList.add(num);
                }
            } else {
                tmpRomanNumbersList.add(num);
            }
        }

        if (!tmpRomanNumbersList.isEmpty()) {
            listOfRomanDigits.add(new ArrayList<>(tmpRomanNumbersList));
            tmpRomanNumbersList.clear();
        }
        return listOfRomanDigits;
    }

    private int getValueFromList(List<RomanNumbers> numbers) {
        int result = 0;
        for (RomanNumbers number : numbers) {
            result += number.getValue();
        }
        return result;
    }

    private int roundDown(int num) {

        if (num > RomanNumbers.M.getValue()) {
            return intDiv(RomanNumbers.M, num);

        } else if (num > RomanNumbers.C.getValue()) {
            return intDiv(RomanNumbers.C, num);

        } else if (num > RomanNumbers.X.getValue()) {
            return intDiv(RomanNumbers.X, num);

        } else {
            return num;
        }
    }

    public boolean isRomanNumber(String romanNumber) {
        for (char latter : romanNumber.toUpperCase().toCharArray()) {
            if (getRomanNumberWithLatter(latter) == null)
                return false;
        }
        return true;
    }

    public RomanNumbers getRomanNumberWithLatter(char letter) {
        for (RomanNumbers number : currentRomanNumbers) {
            if (letter == number.getLetter())
                return number;
        }
        return null;
    }

    public void sortRomanNumbersAsc() {
        for (int i = 0; i < currentRomanNumbers.size(); i++) {
            for (int j = 0; j < currentRomanNumbers.size(); j++) {
                if (j + 1 != currentRomanNumbers.size() && currentRomanNumbers.get(j).getValue() > currentRomanNumbers.get(j + 1).getValue()) {
                    var tmp = currentRomanNumbers.get(j);
                    currentRomanNumbers.set(j, currentRomanNumbers.get(j + 1));
                    currentRomanNumbers.set(j + 1, tmp);
                }
            }
        }
    }

    public void sortRomanNumbersDesc() {
        for (int i = 0; i < currentRomanNumbers.size(); i++) {
            for (int j = 0; j < currentRomanNumbers.size(); j++) {
                if (j + 1 != currentRomanNumbers.size() && currentRomanNumbers.get(j).getValue() < currentRomanNumbers.get(j + 1).getValue()) {
                    var tmp = currentRomanNumbers.get(j);
                    currentRomanNumbers.set(j, currentRomanNumbers.get(j + 1));
                    currentRomanNumbers.set(j + 1, tmp);
                }
            }
        }
    }

    private int intDiv(RomanNumbers romanNumber, int num) {
        int tmp = num % romanNumber.getValue();
        return num - tmp;
    }

    private RomanNumbers getBiggestPossibleRomanNumber(int num) {
        RomanNumbers result = null;
        int tmp = 0;

        for (RomanNumbers number : getCurrentRomanNumbers()) {
            if (num >= number.getValue() && number.getValue() > tmp) {
                result = number;
                tmp = number.getValue();
            }
        }
        return result;
    }

    private RomanNumbers getNextSmallestGroundRomanNumber(int num) {
        RomanNumbers result = null;
        int tmp = 0;

        for (RomanNumbers number : getCurrentRomanNumbers()) {
            if (!getListOfBetwixtNumbers().contains(number.getLetter()) && num > number.getValue() && number.getValue() > tmp) {
                result = number;
                tmp = number.getValue();
            }
        }
        return result;
    }

    private RomanNumbers getNextSmallestRomanNumber(int current) {
        RomanNumbers nextSmallest = null;
        sortRomanNumbersDesc();
        for (RomanNumbers num : getCurrentRomanNumbers()) {
            if (current > num.getValue()) {
                sortRomanNumbersAsc();
                nextSmallest = num;
            }
        }
        return nextSmallest;
    }

    private int getMultiplier(RomanNumbers biggestPossible, int roundedNum) {
        return roundedNum / biggestPossible.getValue();
    }

    private int getBiggestRomanNum() {
        int res = 0;
        for (RomanNumbers number : getCurrentRomanNumbers()) {
            if (number.getValue() > res) {
                res = number.getValue();
            }
        }
        return res;
    }

    private RomanNumbers getNextBiggestGroundRomanNumber(int num) {
        RomanNumbers nextGroundNumber = null;
        for (RomanNumbers number : getCurrentRomanNumbers()) {
            if (!getListOfBetwixtNumbers().contains(number.getLetter()) && number.getValue() > num) {
                nextGroundNumber = number;
            }
        }
        return nextGroundNumber;
    }

    private String getRomanNumWithSubtraction(int roundedNum) {
        var possibleGroundNumberToSubtractWith = getNextSmallestGroundRomanNumber(roundedNum);
        var nextBiggestGroundNumber = getNextBiggestGroundRomanNumber(roundedNum);
        int multiplier = getMultiplier(possibleGroundNumberToSubtractWith, nextBiggestGroundNumber.getValue() - roundedNum);

        while (multiplier > 1) {
            nextBiggestGroundNumber = getNextSmallestRomanNumber(nextBiggestGroundNumber.getValue());
            multiplier = getMultiplier(possibleGroundNumberToSubtractWith, nextBiggestGroundNumber.getValue() - roundedNum);
        }

        return String.valueOf(possibleGroundNumberToSubtractWith.getLetter()).repeat(Math.max(0, multiplier)) +
                nextBiggestGroundNumber.getLetter();
    }

    public String getRomanNumber(int arabianNumber) {
        if (arabianNumber == 0) {
            return RomanNumbers.N.toString();
        }

        var biggestPossible = getBiggestPossibleRomanNumber(arabianNumber);
        int newNumber = 0, roundedNumber = roundDown(arabianNumber), multiplier = getMultiplier(biggestPossible, roundedNumber);
        StringBuilder result = new StringBuilder();

        if (multiplier > 3 && biggestPossible.getValue() == getBiggestRomanNum()) {
            System.out.println("The Arabian Number is too Big for the current array of Numbers please extend the roman numbers!");
            return RomanNumbers.N.toString();
        }
        else if (multiplier > 3 || (multiplier * biggestPossible.getValue() != roundedNumber)) {
            result.append(getRomanNumWithSubtraction(roundedNumber));
            var provisionalResult = getArabianNumber(result.toString());
            newNumber = arabianNumber - provisionalResult;
        }
        else {
            for (int i = 0; i < multiplier; i++) {
                result.append(biggestPossible.name());
                newNumber = arabianNumber - (biggestPossible.getValue() * multiplier);
            }
        }

        if (newNumber != 0) {
            result.append(getRomanNumber(newNumber));
        }

        return result.toString();
    }
}
