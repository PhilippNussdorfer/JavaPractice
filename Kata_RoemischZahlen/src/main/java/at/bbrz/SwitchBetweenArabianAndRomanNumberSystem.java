package at.bbrz;

import at.bbrz.RomanNumbers.RomanNumbers;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            char[] myRomanNumber = romanNumber.toUpperCase().toCharArray();
            List<List<RomanNumbers>> sets = new ArrayList<>();

            splitRomanNumberInSets(myRomanNumber, sets);
            return calculateArabianNumber(sets);
        }
        throw new IllegalArgumentException("This is not an roman number!");
    }

    private boolean isRomanNumber(String romanNumber) {
        boolean result = false;
        for (char romanNum : romanNumber.toUpperCase().toCharArray()) {
            for (RomanNumbers numbers : currentRomanNumbers) {
                if (romanNum == numbers.getLatter()) {
                    result = true;
                    break;
                } else {
                    result = false;
                }
            }
            if (!result) {
                return result;
            }
        }
        return result;
    }

    private int calculateArabianNumber(List<List<RomanNumbers>> sets) {
        int result = 0;
        boolean skip = false;
        List<RomanNumbers> currentPointer = null, prevPointer = null;

        for (int i = 0; i < sets.size(); i++) {
            if (i - 1 >= 0) {
                currentPointer = sets.get(i);
                prevPointer = sets.get(i - 1);

                int currentValue = currentPointer.get(0).getValue();
                int prevValue = prevPointer.get(0).getValue();

                if (prevValue < currentValue) {
                    result += getValueFromSet(currentPointer) - getValueFromSet(prevPointer);
                    skip = true;
                } else {
                    if (skip) {
                        skip = false;
                    } else {
                        result += getValueFromSet(prevPointer);
                    }
                }
            }
        }

        if (prevPointer != null && prevPointer.get(0).getValue() > currentPointer.get(0).getValue()) {
            result += getValueFromSet(currentPointer);
        } else if (prevPointer == null && currentPointer == null) {
            result += getValueFromSet(sets.get(0));
        }
        return result;
    }

    private void splitRomanNumberInSets(char[] myRomanNumber, List<List<RomanNumbers>> sets) {
        List<RomanNumbers> tmpRomanNumbersList = new ArrayList<>();

        for (char number : myRomanNumber) {
            var num = getRomanNumberWithLatter(number);

            if (!tmpRomanNumbersList.isEmpty()) {

                if (!tmpRomanNumbersList.contains(num)) {
                    sets.add(new ArrayList<>(tmpRomanNumbersList));
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
            sets.add(new ArrayList<>(tmpRomanNumbersList));
            tmpRomanNumbersList.clear();
        }
    }

    private int getValueFromSet(List<RomanNumbers> numbers) {
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

    private int intDiv(RomanNumbers romanNumber, int num) {
        int tmp = num % romanNumber.getValue();
        return num - tmp;
    }

    private RomanNumbers getBiggestPossibleRomanNumber(int num) {
        RomanNumbers result = null;
        int tmp = 0;

        for (RomanNumbers number : currentRomanNumbers) {
            if (num >= number.getValue() && number.getValue() > tmp) {
                result = number;
                tmp = number.getValue();
            }
        }
        if (result == null)
            return RomanNumbers.M;
        return result;
    }

    private RomanNumbers getNextSmallestGroundRomanNumber(int num) {
        RomanNumbers result = null;
        int tmp = 0;

        for (RomanNumbers number : currentRomanNumbers) {
            if (!listOfBetwixtNumbers.contains(number.getLatter()) && num > number.getValue() && number.getValue() > tmp) {
                result = number;
                tmp = number.getValue();
            }
        }
        if (result == null)
            return RomanNumbers.I;
        return result;
    }

    private RomanNumbers getRomanNumberWithLatter(char letter) {
        for (RomanNumbers number : currentRomanNumbers) {
            if (letter == number.getLatter())
                return number;
        }
        return null;
    }

    private RomanNumbers getNextSmallestRomanNumber(int current) {
        sortRomanNumbersDesc();
        for (RomanNumbers num : currentRomanNumbers) {
            if (current > num.getValue()) {
                sortRomanNumbersAsc();
                return num;
            }
        }
        sortRomanNumbersAsc();
        return null;
    }

    private int getMultiplier(RomanNumbers biggestPossible, int roundedNum) {
        return roundedNum / biggestPossible.getValue();
    }

    private int getBiggestRomanNum() {
        int res = 0;
        for (RomanNumbers number : currentRomanNumbers) {
            if (number.getValue() > res) {
                res = number.getValue();
            }
        }
        return res;
    }

    private RomanNumbers getNextBiggestGroundRomanNumber(int num) {
        for (RomanNumbers number : currentRomanNumbers) {
            if (!listOfBetwixtNumbers.contains(number.getLatter()) && number.getValue() > num) {
                return number;
            }
        }
        return RomanNumbers.M;
    }

    private void sortRomanNumbersAsc() {
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

    private void sortRomanNumbersDesc() {
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

    private String getRomanNumWithSubtraction(int roundedNum) {
        var possibleGroundNumberToSubtractWith = getNextSmallestGroundRomanNumber(roundedNum);
        var nextBiggestGroundNumber = getNextBiggestGroundRomanNumber(roundedNum);
        int multiplier = getMultiplier(possibleGroundNumberToSubtractWith, nextBiggestGroundNumber.getValue() - roundedNum);

        while (multiplier > 3) {
            nextBiggestGroundNumber = getNextSmallestRomanNumber(nextBiggestGroundNumber.getValue());
            if (nextBiggestGroundNumber == null)
                return "Error with this number:" + roundedNum;
            multiplier = getMultiplier(possibleGroundNumberToSubtractWith, nextBiggestGroundNumber.getValue() - roundedNum);
        }

        return String.valueOf(possibleGroundNumberToSubtractWith.getLatter()).repeat(Math.max(0, multiplier)) +
                nextBiggestGroundNumber.getLatter();
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
            newNumber = arabianNumber - getArabianNumber(result.toString());
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
