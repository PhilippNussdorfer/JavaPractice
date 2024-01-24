package at.bbrz.calculations;

public class Multiplei extends Calc {

    public Multiplei() {
        super('*');
    }

    @Override
    public int getResult(int firstNum, int secNum) {
        return firstNum * secNum;
    }
}
