public class TearThePaper {
    int x, y;

    public TearThePaper(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getTears() {
        return (x * y) - 1;
    }

    public static void main(String[] args) {
        var paper = new TearThePaper(1250, 50);

        System.out.println(paper.getTears());
    }
}
