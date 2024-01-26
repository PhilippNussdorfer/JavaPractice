package at.bbrz;

import at.bbrz.RomanNumbers.RomanNumbers;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SwitchBetweenArabianAndRomanNumberSystem {
    RomanNumberChecker checker;

    public SwitchBetweenArabianAndRomanNumberSystem(RomanNumberChecker checker) {
        this.checker = checker;
        checker.sortRomanNumbersAsc();
    }

    public int getArabianNumber(String romanNumber) {
        if (checker.isRomanNumber(romanNumber)) {
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
            var num = checker.getRomanNumberWithLatter(number);

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

    private int intDiv(RomanNumbers romanNumber, int num) {
        int tmp = num % romanNumber.getValue();
        return num - tmp;
    }

    private RomanNumbers getBiggestPossibleRomanNumber(int num) {
        RomanNumbers result = null;
        int tmp = 0;

        for (RomanNumbers number : checker.getCurrentRomanNumbers()) {
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

        for (RomanNumbers number : checker.getCurrentRomanNumbers()) {
            if (!checker.getListOfBetwixtNumbers().contains(number.getLatter()) && num > number.getValue() && number.getValue() > tmp) {
                result = number;
                tmp = number.getValue();
            }
        }
        if (result == null)
            return RomanNumbers.I;
        return result;
    }

    private RomanNumbers getNextSmallestRomanNumber(int current) {
        checker.sortRomanNumbersDesc();
        for (RomanNumbers num : checker.getCurrentRomanNumbers()) {
            if (current > num.getValue()) {
                checker.sortRomanNumbersAsc();
                return num;
            }
        }
        return null;
    }

    private int getMultiplier(RomanNumbers biggestPossible, int roundedNum) {
        return roundedNum / biggestPossible.getValue();
    }

    private int getBiggestRomanNum() {
        int res = 0;
        for (RomanNumbers number : checker.getCurrentRomanNumbers()) {
            if (number.getValue() > res) {
                res = number.getValue();
            }
        }
        return res;
    }

    private RomanNumbers getNextBiggestGroundRomanNumber(int num) {
        for (RomanNumbers number : checker.getCurrentRomanNumbers()) {
            if (!checker.getListOfBetwixtNumbers().contains(number.getLatter()) && number.getValue() > num) {
                return number;
            }
        }
        return RomanNumbers.M;
    }

    private String getRomanNumWithSubtraction(int roundedNum) {
        var possibleGroundNumberToSubtractWith = getNextSmallestGroundRomanNumber(roundedNum);
        var nextBiggestGroundNumber = getNextBiggestGroundRomanNumber(roundedNum);
        int multiplier = getMultiplier(possibleGroundNumberToSubtractWith, nextBiggestGroundNumber.getValue() - roundedNum);

        while (multiplier > 1) {
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
