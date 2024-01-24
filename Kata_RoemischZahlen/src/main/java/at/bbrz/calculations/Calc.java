package at.bbrz.calculations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Calc {
    private char operator;

    public abstract int getResult(int firstNum, int secNum);
}
