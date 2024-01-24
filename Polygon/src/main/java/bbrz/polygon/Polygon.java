package bbrz.polygon;

public abstract class Polygon {

    private final String name;

    public Polygon(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public abstract double outline();
}
