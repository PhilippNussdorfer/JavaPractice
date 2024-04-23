package bbrz.textadventure.locatins;

import bbrz.textadventure.rules.*;
import bbrz.textadventure.tools.LocationPointerTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MapLocationPopulationCrawler {
    private final RuleInterpreter ruleInterpreter;
    private final Random random;
    private final LocationPointerTool tool;

    public MapLocationPopulationCrawler(RuleInterpreter ruleInterpreter, Random random, LocationPointerTool tool) {
        this.ruleInterpreter = ruleInterpreter;
        this.random = random;
        this.tool = tool;
    }

    private boolean isLocationSettable(Location prevLoc, Location randomPickedLoc) {
        return ruleInterpreter.interpretRule(prevLoc.getMark(), randomPickedLoc.getMark());
    }

    public List<List<Location>> populateMaze(List<List<Location>> maze, int x, int y, List<Location> possibleLocations, Location prevLoc) {
        if (prevLoc == null) {
            maze.get(y).set(x, possibleLocations.get(0));
            maze.get(y).get(x).addPosition(x, y);
        } else {
            maze.get(y).set(x, getPossibleLoc(possibleLocations, prevLoc));
            maze.get(y).get(x).addPosition(x, y);

            tool.addPointerToLocation(prevLoc, maze.get(y).get(x));
        }

        Position currentPos = new Position(x, y);
        boolean hasLocations = true;

        while (hasLocations) {
            Location prev = maze.get(currentPos.getY()).get(currentPos.getX());
            List<Position> posList = possibleDirections(currentPos.getX(), currentPos.getY(), maze);
            Iterator<Position> posDirIterator = posList.iterator();

            int countLocationOccurrence = filterInvalidDirections(maze, posDirIterator);

            if (countLocationOccurrence == 1)
                currentPos = new Position(posList.get(0).getX(), posList.get(0).getY());

            maze = populateValidDirections(maze, possibleLocations, countLocationOccurrence, currentPos, prev, posList);

            if (countLocationOccurrence == 0) {
                hasLocations = false;
            } else {
                countLocationOccurrence = 0;
            }
        }

        return maze;
    }

    private List<List<Location>> populateValidDirections(List<List<Location>> maze, List<Location> possibleLoc, int countLocationOccurrence, Position pos, Location prev, List<Position> posList) {
        if (countLocationOccurrence > 1) {
            for (int i = 0; i < countLocationOccurrence; i++) {
                maze = populateMaze(maze, posList.get(i).getX(), posList.get(i).getY(), possibleLoc, maze.get(pos.getY()).get(pos.getX()));
            }
        }

        if (countLocationOccurrence == 1) {
            Location tmpSave = getPossibleLoc(possibleLoc, prev);
            maze.get(pos.getY()).set(pos.getX(), tmpSave);
            maze.get(pos.getY()).get(pos.getX()).addPosition(pos);

            tool.addPointerToLocation(prev, maze.get(pos.getY()).get(pos.getX()));
        }

        return maze;
    }

    private int filterInvalidDirections(List<List<Location>> maze, Iterator<Position> posDirIterator) {
        int countLocationOccurrence = 0;

        while (posDirIterator.hasNext()) {
            Position pos = posDirIterator.next();
            int x = pos.getX(), y = pos.getY();

            if (maze.get(y).get(x) != null && maze.get(y).get(x).getMark() == MapRuleMark.REPLACEABLE) {
                countLocationOccurrence++;
            } else {
                posDirIterator.remove();
            }
        }
        return countLocationOccurrence;
    }

    private List<Position> possibleDirections(int x, int y, List<List<Location>> maze) {
        List<Position> posList = new ArrayList<>();

        if (y - 1 >= 0) {
            posList.add(new Position(x, y - 1));
        }
        if (y + 1 < maze.size()) {
            posList.add(new Position(x, y + 1));
        }

        if (x - 1 >= 0) {
            posList.add(new Position(x - 1, y));
        }
        if (x + 1 < maze.get(0).size()) {
            posList.add(new Position(x + 1, y));
        }

        return posList;
    }

    private Location getPossibleLoc(List<Location> possibleLoc, Location prevLoc) {
        Location tmpSave = null;
        int count = 0;

        while (count < 500) {
            tmpSave = possibleLoc.get(random.nextInt(possibleLoc.size()));

            if (isLocationSettable(prevLoc, tmpSave))
                break;

            count ++;
        }

        return tmpSave;
    }
}
