// MazeGenerator from https://github.com/oppenheimj
package bbrz.textadventure.gameLoader;

import bbrz.textadventure.locatins.Location;

import java.util.*;

public class MazeGenerator {
    private final Stack<MaceNode> stack = new Stack<>();
    private final Random rand = new Random();
    private final Location[][] mace;
    private final Location replaceable;
    private final int dimensions;

    public MazeGenerator(int dimensions, Location replaceable) {
        mace = new Location[dimensions][dimensions];

        this.dimensions = dimensions;
        this.replaceable = replaceable;
    }

    public void generateMaze() {
        stack.push(new MaceNode(0, 0));

        while (!stack.empty()) {
            MaceNode next = stack.pop();

            if (validNextNode(next)) {
                mace[next.getY()][next.getX()] = replaceable;
                ArrayList<MaceNode> neighbours = findNeighbours(next);
                randomlyAddNodesToStack(neighbours);
            }
        }
    }

    public String getRawMaze() {
        StringBuilder builder = new StringBuilder();
        for (Location[] row : mace) {
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
        for (Location[] row : mace) {
            result.add(Arrays.asList(row));
        }
        return result;
    }

    private void randomlyAddNodesToStack(ArrayList<MaceNode> neighbours) {
        int targetIndex;

        while (!neighbours.isEmpty()) {
            targetIndex = rand.nextInt(neighbours.size());
            stack.push(neighbours.remove(targetIndex));
        }
    }

    private ArrayList<MaceNode> findNeighbours(MaceNode next) {
        ArrayList<MaceNode> neighbours = new ArrayList<>();

        for (int y = next.getY() - 1; y < next.getY() + 2; y++) {
            for (int x = next.getX() - 1; x < next.getX() + 2; x++) {
                if (pointOnGrid(x, y) && pointNotCorner(next, x, y) && pointNotNode(next, x, y)) {
                    neighbours.add(new MaceNode(x, y));
                }
            }
        }

        return neighbours;
    }

    private boolean pointNotCorner(MaceNode next, int x, int y) {
        return (x == next.getX() || y == next.getY());
    }

    private boolean validNextNode(MaceNode next) {
        int numNeighbouringOnes = 0;

        for (int y = next.getY() - 1; y < next.getY() + 2; y++) {
            for (int x = next.getX() - 1; x < next.getX() + 2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(next, x, y) && mace[y][x] == replaceable) {
                    numNeighbouringOnes++;
                }
            }
        }
        return (numNeighbouringOnes < 3) && mace[next.getY()][next.getX()] != replaceable;
    }

    private boolean pointNotNode(MaceNode next, int x, int y) {
        return !(x == next.getX() && y == next.getY());
    }

    private boolean pointOnGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < dimensions && y < dimensions;
    }
}