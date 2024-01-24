package org.example.Entitys;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.Interfaces.BoardPosition;
import org.example.Interfaces.SaveAble;
import org.example.Position;

@RequiredArgsConstructor
public class PacMan implements SaveAble, BoardPosition {
    @NonNull
    private final String name;
    @NonNull
    private final int points;
    private Position pos;

    @Override
    public boolean save() {
        System.out.println("Saved PacMan\n" + name + "\n" + points + "\n");

        return true;
    }

    @Override
    public void setPosition(int x, int y) {
        pos = new Position(x, y);
    }

    @Override
    public Position getPosition() {
        return pos;
    }
}
