package bbrz.textadventure.rules;

public class EdgeOfTheSwampLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.EDGE_OF_THE_SWAMP;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation != randomLocation && (
                    randomLocation == MapRuleMark.SWAMP ||
                        randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                            randomLocation == MapRuleMark.MEADOW ||
                                randomLocation == MapRuleMark.LAKE ||
                                    randomLocation == MapRuleMark.CLIFF
                );
    }
}
