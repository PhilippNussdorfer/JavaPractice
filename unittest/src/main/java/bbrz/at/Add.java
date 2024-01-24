package bbrz.at;

public class Add {
    private double number_A;
    private double number_B;

    public Add(double a, double b) {
        this.number_A = a;
        this.number_B = b;
    }

    public double add() {
        return this.number_A + this.number_B;
    }
}
