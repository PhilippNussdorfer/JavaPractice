package bbrz.adventure.game.pathfinding;

import lombok.AllArgsConstructor;

// https://www.geeksforgeeks.org/a-search-algorithm/
@AllArgsConstructor
public class Cell {
    public double g, h, f;
    public int parentRow, parentCol;
}
