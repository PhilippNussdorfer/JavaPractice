package bbrz.textadventure.locations;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Directions {
    NORTH("north", "n"),
    WEST("west", "w"),
    SOUTH("south", "s"),
    EAST("east", "e");

    @Getter
    private final List<String> values = new ArrayList<>();

    Directions(String ... values) {
        this.values.addAll(Arrays.asList(values));
    }
}
