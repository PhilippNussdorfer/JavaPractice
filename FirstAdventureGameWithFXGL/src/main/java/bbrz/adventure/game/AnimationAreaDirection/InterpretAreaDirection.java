package bbrz.adventure.game.AnimationAreaDirection;

import bbrz.adventure.game.InputRegister.InputStatus;

import java.util.List;

public class InterpretAreaDirection {

    private final InputStatus inputStatus;
    private final List<AreaDirection> aresDirectionsCollection;

    public InterpretAreaDirection(InputStatus inputStatus, List<AreaDirection> aresDirectionsCollection) {//List<Integer> directions, int direction) {
        this.inputStatus = inputStatus;
        this.aresDirectionsCollection = aresDirectionsCollection;
    }

    public void interpret() {
        for (AreaDirection areaDirection : aresDirectionsCollection) {
            if (inputStatus.isIdle()) {
                if (areaDirection.getIndicator() == inputStatus.getLastKnownDirection()) {
                    areaDirection.setAttackArea();
                }
            } else {
                if (areaDirection.getIndicator() == inputStatus.getDirectionList()
                        .get(inputStatus.getDirectionList().size() - 1)) {
                    areaDirection.setAttackArea();
                }
            }
        }
    }
}
