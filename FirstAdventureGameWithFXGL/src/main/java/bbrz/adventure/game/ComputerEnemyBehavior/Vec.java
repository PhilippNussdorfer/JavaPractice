package bbrz.adventure.game.ComputerEnemyBehavior;

import lombok.Getter;

@Getter
public class Vec {
    private double x, y;

    public Vec() {

    }

    public Vec(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vec vec) {
        this.x += vec.getX();
        this.y += vec.getY();
    }

    public void sub(Vec vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
    }

    public void div(double v) {
        this.x /= v;
        this.y /= v;
    }

    public void multiply(double v) {
        this.x *= v;
        this.y *= v;
    }

    public double mag() {
        return Math.sqrt(
                Math.pow(this.x, 2)
                + Math.pow(this.y, 2));
    }

    public double dot(Vec vec) {
        return this.x * vec.getX() + this.y * vec.getY();
    }

    public void normalize() {
        double mag = this.mag();
        if (mag != 0) {
            this.x /= mag;
            this.y /= mag;
        }
    }

    public void limit(double limit) {
        double mag = this.mag();
        if (mag != 0 && mag > limit) {
            this.x *= limit / mag;
            this.y *= limit / mag;
        }
    }

    public double heading() {
        return Math.atan2(this.y, this.x);
    }

    public static Vec sub(Vec vec, Vec vec2) {
        return new Vec(vec.getX() - vec2.getX(), vec.getY() - vec2.getY());
    }

    public static double dist(Vec vec, Vec vec2) {
        return Math.sqrt(
                Math.pow(vec.getX() - vec2.getX(), 2)
                + Math.pow(vec.getY() - vec2.getY(), 2));
    }

    public static double angleBetween(Vec vec, Vec vec2) {
        return Math.acos(vec.dot(vec2) / (vec.mag() * vec2.mag()));
    }
}