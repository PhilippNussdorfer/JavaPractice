package org.example;

public class Rechteck implements Polygon{
    private final double a, b;

    public Rechteck(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double umfang() {
        return (a * 2) + (b * 2);
    }

    @Override
    public String getName() {
        return "Rechteck";
    }
}
