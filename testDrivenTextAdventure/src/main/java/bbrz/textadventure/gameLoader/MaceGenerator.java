package bbrz.textadventure.gameLoader;

import bbrz.textadventure.locatins.Location;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MaceGenerator {
    private final Stack<MaceNode> stack = new Stack<>();
    private Random rand;
    private Location[][] mace;
    private Location replaceable;
    private int dimensions;

    public MaceGenerator(int dimensions, Location replaceable, Random rand) {
        mace = new Location[dimensions][dimensions];

        this.rand = rand;
        this.dimensions = dimensions;
        this.replaceable = replaceable;
    }

    public void generateMace() {
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

    public String getRawMace() {
        StringBuilder builder = new StringBuilder();
        for (Location[] row : mace) {
            for (Location loc : row) {
                if (loc == null) {
                    builder.append("*");
                } else {
                    builder.append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
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
