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

    @BeforeEach
    void setUp() {
        gen = new MazeGenerator(10, loc);
        gen.generateMaze();
    }

    @Test
    void getRawMace() {
        var res = gen.getRawMaze();
        var i = res.length();

        assertEquals(110, i);
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

    private int walker(List<List<Location>> maze, int x, int y) {
        var currentLoc = maze.get(y).get(x);
        List<Location> listOfDirections = new ArrayList();
        int currentX = x, currentY = y, count = 0;

        if (currentY - 1 > 0) {
            var up = maze.get(currentY - 1).get(currentX);
            listOfDirections.add(up);
        }
        if (currentY + 1 < maze.size()) {
            var down = maze.get(currentY + 1).get(currentX);
            listOfDirections.add(down);
        }
        if (currentX - 1 > 0) {
            var left = maze.get(currentY).get(currentX - 1);
            listOfDirections.add(left);
        }
        if (currentX + 1 < maze.size()) {
            var right = maze.get(currentY).get(currentX + 1);
            listOfDirections.add(right);
        }

        Iterator<Location> iterator = listOfDirections.iterator();

        while (iterator.hasNext()) {
            Location next = iterator.next();
            if (next == null) {
                iterator.remove();
            }
        }

        if (listOfDirections.size() >= 2) {
            for (Location loc : listOfDirections) {
                walker(maze, x, y);
            }
        }

        return 0;
    }

    private int countNewLines(String str) {
        var i = str.split("\n");
        return i.length;
    }
}