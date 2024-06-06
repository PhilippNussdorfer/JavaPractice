package bbrz.adventure.game.pathfinding;

import java.util.*;

// https://www.geeksforgeeks.org/a-search-algorithm/
public class AStar {
    private final List<List<Integer>> map;

    public AStar(List<List<Integer>> map) {
        this.map = map;
    }

    private boolean isValid(int row, int col) {
        int mapRow = map.size();
        int mapCol = map.get(0).size();

        return     (row >= 0)
                && (row < mapRow)
                && (col >= 0)
                && (col < mapCol);
    }

    private boolean isWalkable(int row, int col) {
        return map.get(row).get(col) == 1;
    }

    private boolean isDestination(int row, int col, int[] dest) {
        return     row == dest[0]
                && col == dest[1];
    }

    private double calculateHValue(int row, int col, int[] dest) {
        return Math.sqrt(
                (row - dest[0])
                        * (row - dest[0])
                        + (col - dest[1])
                        * (col - dest[1])
        );
    }

    private List<int[]> tracePath(Cell[][] cellDetails, int[] dest) {
        System.out.println("The Path is :");
        int row = dest[0];
        int col = dest[1];

        Map<int[], Boolean> path = new LinkedHashMap<>();

        while (!(
                cellDetails[row][col].parentRow == row
                        && cellDetails[row][col].parentCol == col
        )) {

            path.put(new int[] {row, col}, true);

            int tmp_row = cellDetails[row][col].parentRow;
            int tmp_col = cellDetails[row][col].parentCol;
            row = tmp_row;
            col = tmp_col;
        }

        path.put(new int[]{row, col}, true);
        List<int[]> pathList = new ArrayList<>(path.keySet());
        Collections.reverse(pathList);

        return pathList;
    }

    public List<int[]> aStarSearch(int[] src, int[] dest) {
        int row = this.map.size();
        int col = this.map.get(0).size();

        if (   !isValid(src[0], src[1])
                || !isValid(dest[0], dest[1])) {
            System.out.println("Source or destination is invalid");
            return null;
        }

        if (!isWalkable(src[0], src[1])
                || !isWalkable(dest[0], dest[1])) {
            System.out.println("Source or the destination is blocked");
            return null;
        }

        if (isDestination(src[0], src[1], dest)) {
            System.out.println("We are already at the destination");
            return null;
        }

        boolean[][] closedList = new boolean[row][col];
        Cell[][] cellDetails = new Cell[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cellDetails[i][j] = new Cell(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, -1, -1);
            }
        }

        int i = src[0], j = src[1];
        cellDetails[i][j].f = 0;
        cellDetails[i][j].g = 0;
        cellDetails[i][j].h = 0;
        cellDetails[i][j].parentRow = i;
        cellDetails[i][j].parentCol = j;

        Map<Double, int[]> openList = new HashMap<>();
        openList.put(0.0, new int[] {i, j});

        List<int[]> result = null;

        while (!openList.isEmpty()) {
            Map.Entry<Double, int[]> tmp = openList.entrySet().iterator().next();
            openList.remove(tmp.getKey());

            i = tmp.getValue()[0];
            j = tmp.getValue()[1];
            closedList[i][j] = true;

            if ((result = direction(i - 1, j, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
            if ((result = direction(i + 1, j, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
            if ((result = direction(i, j + 1, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
            if ((result = direction(i, j - 1, i, j, dest, cellDetails, closedList, openList)) != null)
                break;

            if ((result = direction(i - 1, j + 1, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
            if ((result = direction(i - 1, j - 1, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
            if ((result = direction(i + 1, j + 1, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
            if ((result = direction(i + 1, j - 1, i, j, dest, cellDetails, closedList, openList)) != null)
                break;
        }
        System.out.println("Failed to find the destination cell");
        return result;
    }

    private List<int[]> direction(int i, int j, int parentRow, int parentCol, int[] dest, Cell[][] cellDetails, boolean[][] closedList, Map<Double, int[]> openList) {
        double gNew, hNew, fNew;

        if (isValid(i, j)) {
            if (isDestination(i, j, dest)) {

                cellDetails[i][j].parentRow = parentRow;
                cellDetails[i][j].parentCol = parentCol;

                System.out.println("The destination cell is found");

                return tracePath(cellDetails, dest);
            } else if (    !closedList[i][j]
                    && isWalkable(i, j)) {
                gNew = cellDetails[parentRow][parentCol].g + 1;
                hNew = calculateHValue(i, j, dest);
                fNew = gNew + hNew;

                if (   cellDetails[i][j].f == Double.POSITIVE_INFINITY
                        || cellDetails[i][j].f > fNew) {
                    openList.put(fNew, new int[]{i, j});

                    cellDetails[i][j].f = fNew;
                    cellDetails[i][j].g = gNew;
                    cellDetails[i][j].h = hNew;
                    cellDetails[i][j].parentRow = parentRow;
                    cellDetails[i][j].parentCol = parentCol;
                }
            }
        }

        return null;
    }
}
