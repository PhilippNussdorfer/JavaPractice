package bbrz.polygon;

public class Triangle extends Polygon {

    private double a;
    private double b;
    private double c;

    public Triangle(String name, double a, double b, double c) {
        super(name);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double outline() {
        return this.a + this.b + this.c;
    }
}
