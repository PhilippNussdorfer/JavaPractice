package at.bbrz;

import at.bbrz.calculations.Interpretor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Calculator {

    private final SwitchBetweenArabianAndRomanNumberSystem switcher;
    private final Interpretor interpretor;

    public String Calculate(String romanNumFirst, char operator, String romanNumSec) {
        return switcher.getRomanNumber(interpretor.interpret(switcher.getArabianNumber(romanNumFirst), operator, switcher.getArabianNumber(romanNumSec)));
    }
}
