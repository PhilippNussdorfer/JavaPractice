package org.example;

public class Dreieck implements Polygon {
    private final double a, b, c;

    public Dreieck(double a, double b, double c) {
        this.a = a;
        this.c = c;
        this.b = b;
    }

    @Override
    public double umfang() {
        return this.a + this.c + this.b;
    }

    @Override
    public String getName() {
        return "Dreieck";
    }
}
