package bbrz.textadventure.rules;

public class SwampLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.SWAMP;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation == randomLocation ||
                randomLocation == MapRuleMark.EDGE_OF_THE_SWAMP;
    }
}
