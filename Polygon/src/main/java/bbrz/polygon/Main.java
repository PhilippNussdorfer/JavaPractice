package bbrz.polygon;

public class Main {
    public static void main(String[] args) {
        Polygon tr = new Triangle("triangle", 3, 2, 1);
        Polygon re = new Rectangle("rectangle", 4, 6);
        Polygon qu = new Quadrat("quadrat", 5);

        Triangle tri = new Triangle("triangle", 1, 5, 4);

        System.out.println(tri.outline() + " " + tri.getName());
        System.out.println(tr.getName() + " " + tr.outline());
        System.out.println(re.getName() + " " + re.outline());
        System.out.println(qu.getName() + " " + qu.outline());

        re = new Quadrat("tr", 2);
        System.out.println(re.getName() + " " + re.outline());
    }
}