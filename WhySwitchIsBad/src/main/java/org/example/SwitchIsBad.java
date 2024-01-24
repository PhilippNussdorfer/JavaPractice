package org.example;

import java.util.ArrayList;
import java.util.List;

public class SwitchIsBad {
    public static void main(String[] args) {
        Rechteck rechteck = new Rechteck(3, 6);
        Dreieck dreieck = new Dreieck(2, 5, 3);

        List<Object> objList = new ArrayList<>();
        objList.add(rechteck);
        objList.add(dreieck);

        for (Object o : objList) {
            switch (o.getClass().getSimpleName()) {
                case "Rechteck" -> System.out.println(((Rechteck) o).getName() + ((Rechteck) o).umfang());
                case "Dreieck" -> System.out.println(((Dreieck) o).getName() + ((Dreieck) o).umfang());
                default -> throw new IllegalStateException("Unexpected value: " + o);
            }
        }
    }
}
