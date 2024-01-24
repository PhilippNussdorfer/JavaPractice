package bbrz.polygon;

public class Rectangle extends Polygon {

    private double a;
    private double b;

    public Rectangle(String name, double a, double b) {
        super(name);
        this.a = a;
        this.b = b;
    }

    @Override
    public double outline() {
        return (this.a * 2) + (this.b * 2);
    }
}
