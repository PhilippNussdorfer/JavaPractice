import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//https://www.geeksforgeeks.org/a-search-algorithm/
@Getter
@Setter
@AllArgsConstructor
public class Cell {
    private int parent_i, parent_j;
    private double f, g, h;

    public Cell() {
        this.parent_i = 0;
        this.parent_j = 0;
        this.f = 0;
        this.g = 0;
        this.h = 0;
    }
}
