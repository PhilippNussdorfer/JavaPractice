package bbrz.textadventure.gameLoader;

import bbrz.textadventure.locatins.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MazeGeneratorTest {

    MazeGenerator gen;

    @Mock
    Location loc;
    @Mock
    Location marker;

    @BeforeEach
    void setUp() {
        gen = new MazeGenerator(10, loc);
        gen.generateMaze();
    }

    @Test
    void getRawMace() {
        var res = gen.getRawMaze();
        var i = res.length();

        assertEquals(210, i);
        assertEquals(10, countNewLines(res));
    }

    @Test
    void getMaceAsList() {
        var res = gen.getMazeAsList().size();
        assertEquals(10, res);

        res = gen.getMazeAsList().get(0).size();
        assertEquals(10, res);

        int countLocations = 0;
        int countNulls = 0;

        for (List<Location> row : gen.getMazeAsList()) {
            for (Location location : row) {
                if (location == null) {
                    countNulls++;
                } else {
                    countLocations++;
                }
            }
        }

        assertTrue(countLocations >= 40);
        assertTrue(countNulls >= 40);
    }

    @Test
    void WalkThroughMaze() {
        int walkableTiles = walker(gen.getMazeAsList(), 0, 0);
        int LocationCount = 0;

        for (List<Location> row : gen.getMazeAsList()) {
            for (Location location : row) {
                if (location != null) {
                    LocationCount++;
                }
            }
        }

        assertEquals(LocationCount, walkableTiles);
    }

    private int walker(List<List<Location>> maze, int x, int y) {
        maze.get(y).set(x, marker);

        List<Integer> listOf_XDir = new ArrayList<>();
        List<Integer> listOf_YDir = new ArrayList<>();

        int count = 1, countLocationOccurrence = 0, currentX = x, currentY = y;
        boolean hasLocations = true;

        while (hasLocations) {
            if (currentY - 1 >= 0) {
                listOf_YDir.add(currentY - 1);
                listOf_XDir.add(currentX);
            }
            if (currentY + 1 < maze.size()) {
                listOf_YDir.add(currentY + 1);
                listOf_XDir.add(currentX);
            }

            if (currentX - 1 >= 0) {
                listOf_XDir.add(currentX - 1);
                listOf_YDir.add(currentY);
            }
            if (currentX + 1 < maze.size()) {
                listOf_XDir.add(currentX + 1);
                listOf_YDir.add(currentY);
            }

            Iterator<Integer> xDirIterator = listOf_XDir.iterator();
            Iterator<Integer> yDirIterator = listOf_YDir.iterator();

            while (xDirIterator.hasNext() && yDirIterator.hasNext()) {
                int xDir = xDirIterator.next(), yDir = yDirIterator.next();

                if (maze.get(yDir).get(xDir) == loc) {
                    countLocationOccurrence ++;
                } else {
                    xDirIterator.remove();
                    yDirIterator.remove();
                }
            }

            if (countLocationOccurrence > 1) {
                for (int i = 0; i < countLocationOccurrence; i++) {
                    count += walker(maze, listOf_XDir.get(i), listOf_YDir.get(i));
                }
            }

            if (countLocationOccurrence == 1) {
                currentX = listOf_XDir.get(0);
                currentY = listOf_YDir.get(0);

                maze.get(currentY).set(currentX, marker);
                count ++;
            }

            if (countLocationOccurrence == 0) {
                hasLocations = false;
            } else {
                countLocationOccurrence = 0;
            }
        }

        return count;
    }

    private int countNewLines(String str) {
        var i = str.split("\n");
        return i.length;
    }
}