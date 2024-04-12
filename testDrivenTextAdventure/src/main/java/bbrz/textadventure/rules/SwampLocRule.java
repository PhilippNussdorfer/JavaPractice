package bbrz.textadventure.rules;

public class SwampLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.SWAMP;
    }

    @Override
    public boolean isInRules(MapRuleMark randomLocation) {
        return randomLocation == MapRuleMark.SWAMP ||
                randomLocation == MapRuleMark.EDGE_OF_THE_SWAMP;
    }
}
