package org.example.Entitys;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.Interfaces.BoardPosition;
import org.example.Interfaces.SaveAble;
import org.example.Position;

@RequiredArgsConstructor
public class Ghost implements SaveAble, BoardPosition {
    @NonNull
    private final String name;
    private Position pos;

    @Override
    public boolean save() {
        System.out.println("Saved Ghost\n" + name + "\n");

        return false;
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
