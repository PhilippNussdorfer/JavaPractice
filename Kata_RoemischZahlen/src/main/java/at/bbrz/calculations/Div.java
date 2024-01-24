package at.bbrz.calculations;

public class Div extends Calc {

    public Div() {
        super('/');
    }

    @Override
    public int getResult(int firstNum, int secNum) {
        return firstNum / secNum;
    }
}
