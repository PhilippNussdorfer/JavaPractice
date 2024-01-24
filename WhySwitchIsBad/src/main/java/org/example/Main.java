package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Polygon> polygonList = new ArrayList<>();
        polygonList.add(new Dreieck(1, 5, 7));
        polygonList.add(new Rechteck(4, 6));

        for (Polygon p : polygonList) {
            System.out.println(p.getName() + " : " + p.umfang());
        }
    }
}