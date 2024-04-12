package bbrz.textadventure.rules;

public class EdgeOfTheForestLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.EDGE_OF_THE_FOREST;
    }

    @Override
    public boolean isInRules(MapRuleMark randomLocation) {
        return randomLocation == MapRuleMark.MEADOW ||
                randomLocation == MapRuleMark.LAKE ||
                randomLocation == MapRuleMark.EDGE_OF_THE_SWAMP ||
                randomLocation == MapRuleMark.WOODS ||
                randomLocation == MapRuleMark.WELL ||
                randomLocation == MapRuleMark.EDGE_OF_THE_FOREST;
    }
}
