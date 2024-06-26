package bbrz.textadventure.rules;

public class ClearingLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.CLEARING;
    }

    @Override
    public boolean isInRules(MapRuleMark randomLocation) {
        return randomLocation == MapRuleMark.WOODS ||
                randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                randomLocation == MapRuleMark.WELL;
    }
}
