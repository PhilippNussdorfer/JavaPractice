import java.util.*;

public class AStarSearch {
    private int[][] grid;

    public static void main(String[] args) {
        int[] src = {8, 9};
        int[] dest = {0, 0};

        int[][] grid  = {
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
                {1, 1, 1, 0, 1, 1, 0, 1, 0, 1},
                {0, 0, 1, 0, 1, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 1, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 1, 0, 0, 1}
        };

        AStarSearch search = new AStarSearch();
        search.aStarSearch(src, dest, grid);
    }

    private boolean isValid(int row, int col) {
        int mapRow = grid.length;
        int mapCol = grid[0].length;

        return     (row >= 0)
                && (row < mapRow)
                && (col >= 0)
                && (col < mapCol);
    }

    private boolean isUnBlocked(int row, int col) {
        return grid[row][col] == 1;
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

    private void tracePath(Cell[][] cellDetails, int[] dest) {
        System.out.println("The Path is :");
        int row = dest[0];
        int col = dest[1];

        Map<int[], Boolean> path = new LinkedHashMap<>();

        while (!(
                   cellDetails[row][col].getParent_i() == row
                && cellDetails[row][col].getParent_j() == col
                )) {

            path.put(new int[] {row, col}, true);

            int tmp_row = cellDetails[row][col].getParent_i();
            int tmp_col = cellDetails[row][col].getParent_j();
            row = tmp_row;
            col = tmp_col;
        }

        path.put(new int[]{row, col}, true);
        List<int[]> pathList = new ArrayList<>(path.keySet());
        Collections.reverse(pathList);

        pathList.forEach(arr -> {
            if (       arr[0] == 2
                    || arr[0] == 1) {
                System.out.println("-> (" + arr[0] + ", " + (arr[1]) + ") " + grid[arr[0]][arr[1]]);
            } else  {
                System.out.println("-> (" + arr[0] + ", " + arr[1] + ") " + grid[arr[0]][arr[1]]);
            }
        });
        System.out.println();
    }

    public void aStarSearch(int[] src, int[] dest, int[][] grid) {
        this.grid = grid;
        int row = this.grid.length;
        int col = this.grid[0].length;

        if (   !isValid(src[0], src[1])
            || !isValid(dest[0], dest[1])) {
            System.out.println("Source or destination is invalid");
            return;
        }

        if (!isUnBlocked(src[0], src[1])
            || !isUnBlocked(dest[0], dest[1])) {
            System.out.println("Source or the destination is blocked");
            return;
        }

        if (isDestination(src[0], src[1], dest)) {
            System.out.println("We are already at the destination");
            return;
        }

        boolean[][] closedList = new boolean[row][col];
        Cell[][] cellDetails = new Cell[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cellDetails[i][j] = new Cell(-1, -1, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
        }

        int i = src[0], j = src[1];
        cellDetails[i][j].setF(0);
        cellDetails[i][j].setG(0);
        cellDetails[i][j].setH(0);
        cellDetails[i][j].setParent_i(i);
        cellDetails[i][j].setParent_j(j);

        Map<Double, int[]> openList = new HashMap<>();
        openList.put(0.0, new int[] {i, j});

        while (!openList.isEmpty()) {
            Map.Entry<Double, int[]> tmp = openList.entrySet().iterator().next();
            openList.remove(tmp.getKey());

            i = tmp.getValue()[0];
            j = tmp.getValue()[1];
            closedList[i][j] = true;

            if (direction(i - 1, j, i, j, dest, cellDetails, closedList, openList))
                return;
            if (direction(i + 1, j, i, j, dest, cellDetails, closedList, openList))
                return;
            if (direction(i, j + 1, i, j, dest, cellDetails, closedList, openList))
                return;
            if (direction(i, j - 1, i, j, dest, cellDetails, closedList, openList))
                return;

            if (direction(i - 1, j + 1, i, j, dest, cellDetails, closedList, openList))
                return;
            if (direction(i - 1, j - 1, i, j, dest, cellDetails, closedList, openList))
                return;
            if (direction(i + 1, j + 1, i, j, dest, cellDetails, closedList, openList))
                return;
            if (direction(i + 1, j - 1, i, j, dest, cellDetails, closedList, openList))
                return;
        }
        System.out.println("Failed to find the destination cell");
    }

    /**
     * @param closedList will fill this list.
     * @param openList will fill and remove from this list
     * @return returns an boolean
     */
    private boolean direction(int i, int j, int parent_i, int parent_j, int[] dest, Cell[][] cellDetails, boolean[][] closedList, Map<Double, int[]> openList) {
        double gNew, hNew, fNew;

        if (isValid(i, j)) {
            if (isDestination(i, j, dest)) {

                cellDetails[i][j].setParent_i(parent_i);
                cellDetails[i][j].setParent_j(parent_j);

                System.out.println("The destination cell is found");

                tracePath(cellDetails, dest);
                return true;

            } else if (    !closedList[i][j]
                    && isUnBlocked(i, j)) {
                gNew = cellDetails[parent_i][parent_j].getG() + 1;
                hNew = calculateHValue(i, j, dest);
                fNew = gNew + hNew;

                if (   cellDetails[i][j].getF() == Double.POSITIVE_INFINITY
                        || cellDetails[i][j].getF() > fNew) {
                    openList.put(fNew, new int[]{i, j});

                    cellDetails[i][j].setF(fNew);
                    cellDetails[i][j].setG(gNew);
                    cellDetails[i][j].setH(hNew);
                    cellDetails[i][j].setParent_i(parent_i);
                    cellDetails[i][j].setParent_j(parent_j);
                }
            }
        }

        return false;
    }
}
