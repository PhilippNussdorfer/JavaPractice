// MazeGenerator from https://github.com/oppenheimj
package bbrz.textadventure.gameLoader;

import bbrz.textadventure.locations.Location;

import java.util.*;

public class MazeGenerator {
    private final Stack<MazeNode> stack = new Stack<>();
    private final Random rand = new Random();
    private final Location[][] maze;
    private final Location replaceable;
    private final int dimensions;

    public MazeGenerator(int dimensions, Location replaceable) {
        maze = new Location[dimensions][dimensions];

        this.dimensions = dimensions;
        this.replaceable = replaceable;
    }

    public void generateMaze() {
        stack.push(new MazeNode(0, 0));

        while (!stack.empty()) {
            MazeNode next = stack.pop();

            if (validNextNode(next)) {
                maze[next.getY()][next.getX()] = replaceable;
                ArrayList<MazeNode> neighbours = findNeighbours(next);
                randomlyAddNodesToStack(neighbours);
            }
        }
    }

    public String getRawMaze() {
        StringBuilder builder = new StringBuilder();
        for (Location[] row : maze) {
            for (Location loc : row) {
                if (loc == null) {
                    builder.append("  ");
                } else {
                    builder.append("* ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public List<List<Location>> getMazeAsList() {
        List<List<Location>> result = new ArrayList<>();
        for (Location[] row : maze) {
            result.add(Arrays.asList(row));
        }
        return result;
    }

    private void randomlyAddNodesToStack(ArrayList<MazeNode> neighbours) {
        int targetIndex;

        while (!neighbours.isEmpty()) {
            targetIndex = rand.nextInt(neighbours.size());
            stack.push(neighbours.remove(targetIndex));
        }
    }

    private ArrayList<MazeNode> findNeighbours(MazeNode next) {
        ArrayList<MazeNode> neighbours = new ArrayList<>();

        for (int y = next.getY() - 1; y < next.getY() + 2; y++) {
            for (int x = next.getX() - 1; x < next.getX() + 2; x++) {
                if (pointOnGrid(x, y) && pointNotCorner(next, x, y) && pointNotNode(next, x, y)) {
                    neighbours.add(new MazeNode(x, y));
                }
            }
        }

        return neighbours;
    }

    private boolean pointNotCorner(MazeNode next, int x, int y) {
        return (x == next.getX() || y == next.getY());
    }

    private boolean validNextNode(MazeNode next) {
        int numNeighbouringOnes = 0;

        for (int y = next.getY() - 1; y < next.getY() + 2; y++) {
            for (int x = next.getX() - 1; x < next.getX() + 2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(next, x, y) && maze[y][x] == replaceable) {
                    numNeighbouringOnes++;
                }
            }
        }
        return (numNeighbouringOnes < 3) && maze[next.getY()][next.getX()] != replaceable;
    }

    private boolean pointNotNode(MazeNode next, int x, int y) {
        return !(x == next.getX() && y == next.getY());
    }

    private boolean pointOnGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < dimensions && y < dimensions;
    }
}