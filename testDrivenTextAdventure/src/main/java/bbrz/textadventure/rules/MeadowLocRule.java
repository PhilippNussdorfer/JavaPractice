package bbrz.textadventure.rules;

public class MeadowLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.MEADOW;
    }

    @Override
    public boolean isInRules(MapRuleMark randomLocation) {
        return randomLocation == MapRuleMark.MEADOW ||
                randomLocation == MapRuleMark.CLIFF ||
                randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                randomLocation == MapRuleMark.EDGE_OF_THE_SWAMP ||
                randomLocation == MapRuleMark.LAKE ||
                randomLocation == MapRuleMark.WELL;
    }
}
