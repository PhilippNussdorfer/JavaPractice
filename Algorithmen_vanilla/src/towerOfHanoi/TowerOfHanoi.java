package towerOfHanoi;

public class TowerOfHanoi {
    private final static Stack<TowerPiece> tower_1 = new Stack<>();
    private final static Stack<TowerPiece> tower_2 = new Stack<>();
    private final static Stack<TowerPiece> tower_3 = new Stack<>();

    private static void initTower() {
        tower_1.push(new TowerPiece(3));
        tower_1.push(new TowerPiece(2));
        tower_1.push(new TowerPiece(1));
    }

    private static void showTowers() {
        System.out.println("Tower 1 => " + String.join(", ", tower_1.getStackInList()) + "\n");
        System.out.println("Tower 2 => " + String.join(", ", tower_2.getStackInList()) + "\n");
        System.out.println("Tower 3 => " + String.join(", ", tower_3.getStackInList()) + "\n");
        System.out.println("=".repeat(30) + "\n");
    }

    private static void movePiece(Stack<TowerPiece> fromTower, Stack<TowerPiece> toTower) {
        var tmpTop = fromTower.getTop();

        toTower.push(tmpTop);
        fromTower.pop();
        showTowers();
    }

    public static void main(String[] args) {
        initTower();
        showTowers();

        movePiece(tower_1, tower_3);
        movePiece(tower_1, tower_2);
        movePiece(tower_3, tower_2);
        movePiece(tower_1, tower_3);
        movePiece(tower_2, tower_1);
        movePiece(tower_2, tower_3);
        movePiece(tower_1, tower_3);
    }
}
