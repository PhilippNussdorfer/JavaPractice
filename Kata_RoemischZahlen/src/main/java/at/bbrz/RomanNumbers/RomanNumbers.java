package at.bbrz.RomanNumbers;

import lombok.Getter;

public enum RomanNumbers {
    I(1, 'I'),
    V(5, 'V'),
    X(10, 'X'),
    L(50, 'L'),
    C(100, 'C'),
    D(500, 'D'),
    M(1000, 'M'),
    N(0, 'N');

    @Getter
    private final int value;
    @Getter
    private final char latter;

    RomanNumbers(int value, char latter) { this.value = value; this.latter = latter; }

    @Override
    public String toString() {
        return "" + latter;
    }
}
