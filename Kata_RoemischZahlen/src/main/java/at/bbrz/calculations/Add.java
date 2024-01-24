package at.bbrz.calculations;

public class Add extends Calc {

    public Add() {
        super('+');
    }

    @Override
    public int getResult(int firstNum, int secNum) {
        return firstNum + secNum;
    }
}
