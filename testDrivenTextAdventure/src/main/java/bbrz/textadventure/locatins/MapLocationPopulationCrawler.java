package bbrz.textadventure.locatins;

import bbrz.textadventure.rules.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MapLocationPopulationCrawler {
    private static final RuleInterpreter ruleInterpreter = new RuleInterpreter();

    public MapLocationPopulationCrawler() {
        ruleInterpreter.addList(List.of(
                new BeachLocRule(),
                new ClearingLocRule(),
                new CliffLocRule(),
                new EdgeOfTheForestLocRule(),
                new EdgeOfTheSwampLocRule(),
                new LakeLocRule(),
                new MeadowLocRule(),
                new SeaLocRule(),
                new StartingLocRule(),
                new SwampLocRule(),
                new WellLocRule(),
                new WoodsLocRule()
        ));
    }

    private static boolean isLocationSettable(Location prevLoc, Location randomPickedLoc) {
        return ruleInterpreter.interpretRule(prevLoc.getMark(), randomPickedLoc.getMark());
    }

    public static List<List<Location>> populateMaze(List<List<Location>> maze, int x, int y, List<Location> possibleLoc, Location prevLoc) {
        if (prevLoc == null) {
            maze.get(y).set(x, possibleLoc.get(0));
        } else {
            maze.get(y).set(x, getPossibleLoc(possibleLoc, prevLoc));
        }

        int countLocationOccurrence = 0, currentX = x, currentY = y;
        boolean hasLocations = true;

        while (hasLocations) {
            Location prev = maze.get(currentY).get(currentX);
            List<Position> posList = possibleDirections(currentX, currentY, maze);
            Iterator<Position> posDirIterator = posList.iterator();

            while (posDirIterator.hasNext()) {
                Position pos = posDirIterator.next();
                int xDir = pos.getX(), yDir = pos.getY();

                if (maze.get(yDir).get(xDir).getMark() == MapRuleMark.REPLACEABLE) {
                    countLocationOccurrence ++;
                } else {
                    posDirIterator.remove();
                }
            }

            if (countLocationOccurrence > 1) {
                for (int i = 0; i < countLocationOccurrence; i++) {
                    maze = populateMaze(maze, posList.get(i).getX(), posList.get(i).getY(), possibleLoc, maze.get(currentY).get(currentX));
                }
            }

            if (countLocationOccurrence == 1) {
                currentX = posList.get(0).getX();
                currentY = posList.get(0).getY();

                Location tmpSave = getPossibleLoc(possibleLoc, prev);
                maze.get(currentY).set(currentX, tmpSave);
            }

            if (countLocationOccurrence == 0) {
                hasLocations = false;
            } else {
                countLocationOccurrence = 0;
            }
        }

        return maze;
    }

    private static List<Position> possibleDirections(int x, int y, List<List<Location>> maze) {
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

    private static Location getPossibleLoc(List<Location> possibleLoc, Location prevLoc) {
        Location tmpSave = null;
        Random random = new Random();
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
