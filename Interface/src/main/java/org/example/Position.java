package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Position {
    private final int x,y;

    @Override
    public String toString() {
        return "X = " + x + "\nY = " + y + "\n";
    }
}
