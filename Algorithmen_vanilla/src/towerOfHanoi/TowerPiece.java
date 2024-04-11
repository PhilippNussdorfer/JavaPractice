package towerOfHanoi;

public class TowerPiece {
    private final int size;

    public TowerPiece(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return "" + size;
    }
}
