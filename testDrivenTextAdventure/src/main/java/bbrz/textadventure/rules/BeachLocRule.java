package bbrz.textadventure.rules;

public class BeachLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.BEACH;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation != randomLocation && (
                    randomLocation == MapRuleMark.CLIFF ||
                        randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                            randomLocation == MapRuleMark.MEADOW
                );
    }
}
