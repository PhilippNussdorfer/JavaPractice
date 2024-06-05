import lombok.Getter;

//https://www.geeksforgeeks.org/a-search-algorithm/
public class Cell {
    public int parent_i, parent_j;
    public double f, g, h;

    public Cell() {
        this.parent_i = 0;
        this.parent_j = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }
}
