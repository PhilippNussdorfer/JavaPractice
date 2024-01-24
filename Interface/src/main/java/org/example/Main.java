package org.example;

import org.example.Entitys.Ghost;
import org.example.Entitys.PacMan;
import org.example.Interfaces.BoardPosition;
import org.example.Interfaces.SaveAble;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<SaveAble> saveAbles = new ArrayList<>();
        saveAbles.add(new Ghost("Pinky"));
        saveAbles.add(new Ghost("Inky"));
        saveAbles.add(new PacMan("Philipp", 99991));

        List<BoardPosition> boardPositions = saveAbles.stream().filter(e -> e instanceof BoardPosition).map(e -> (BoardPosition) e).toList();

        for (BoardPosition pos : boardPositions) {
            pos.setPosition(1,4);
        }

        var res = Saver.saveAll(saveAbles);
        System.out.println(res + "\n");

        for (BoardPosition pos : boardPositions) {
            System.out.println(pos.getPosition().toString());
        }
    }
}