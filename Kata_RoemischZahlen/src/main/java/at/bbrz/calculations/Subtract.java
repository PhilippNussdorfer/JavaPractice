package at.bbrz.calculations;

public class Subtract extends Calc {

    public Subtract() {
        super('-');
    }

    @Override
    public int getResult(int firstNum, int secNum) {
        return firstNum - secNum;
    }
}
