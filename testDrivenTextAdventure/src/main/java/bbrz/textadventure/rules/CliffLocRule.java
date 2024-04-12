package bbrz.textadventure.rules;

public class CliffLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.CLIFF;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation != randomLocation && (
                    randomLocation == MapRuleMark.BEACH ||
                        randomLocation == MapRuleMark.WOODS ||
                            randomLocation == MapRuleMark.MEADOW ||
                                randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                                    randomLocation == MapRuleMark.EDGE_OF_THE_SWAMP
                );
    }
}
