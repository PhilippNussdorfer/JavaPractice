import java.util.ArrayList;
import java.util.List;

public class gridPuzzle {
    private int x, y;

    public gridPuzzle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getNum() {
        if (x == y) {
            if (x%2 != 0) {
                x += 1;
                y += 1;
            }
        } else {
            if (x%2 != 0) {
                x += 1;
            }

            if (y%2 != 0) {
                y += 1;
            }
        }

        return (x * y) / 4;
    }

    public int getNumWithCrawler() {
        int result = 0;

        List<List<Integer>> grid = new ArrayList<>();

        for (int Y = 0; Y < y; ++Y) {
            List<Integer> spaces = new ArrayList<>();
            for (int X = 0; X < x; X++) {
                spaces.add(0);
            }
            grid.add(spaces);
        }

        boolean run = true;
        while (run) {
            if (crawler(grid)) {
                result ++;
            } else {
                run = false;
            }
        }

        return result;
    }

    private boolean crawler(List<List<Integer>> grid) {
        int x = 0, y = 0, count = 0;

        boolean searching = true;
        while (searching) {
            if (grid.get(y).get(x) == 0) {
                searching = false;
            } else {
                if (x + 1 < this.x) {
                    x += 1;
                } else {
                    x = 0;
                    if (y + 1 < this.y) {
                        y += 1;
                    } else {
                        return false;
                    }
                }
            }
        }

        while (count < 4) {
            if (grid.get(y).get(x) == 0) {
                grid.get(y).set(x, 1);
            }

            if (count < 2 && x + 1 < this.x) {
                x += 1;
            }

            if (count < 4 && count >=2 && y + 1 < this.y) {
                y += 1;
            }
            count ++;
        }

        return true;
    }

    public static void main(String[] args) {
        gridPuzzle puzzle = new gridPuzzle(5, 3);

        System.out.println(puzzle.getNum());
        System.out.println(puzzle.getNumWithCrawler());
    }
}
