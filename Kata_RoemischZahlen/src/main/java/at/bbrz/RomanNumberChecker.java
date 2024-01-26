package at.bbrz;

import at.bbrz.RomanNumbers.RomanNumbers;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RomanNumberChecker {
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

    public boolean isRomanNumber(String romanNumber) {
        for (char latter : romanNumber.toUpperCase().toCharArray()) {
            if (getRomanNumberWithLatter(latter) == null)
                return false;
        }
        return true;
    }

    public RomanNumbers getRomanNumberWithLatter(char letter) {
        for (RomanNumbers number : currentRomanNumbers) {
            if (letter == number.getLatter())
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
}
