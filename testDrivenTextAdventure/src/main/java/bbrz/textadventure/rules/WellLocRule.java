package bbrz.textadventure.rules;

public class WellLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.WELL;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation != randomLocation && (
                    randomLocation == MapRuleMark.MEADOW ||
                        randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                            randomLocation == MapRuleMark.EDGE_OF_THE_SWAMP
                );
    }
}
