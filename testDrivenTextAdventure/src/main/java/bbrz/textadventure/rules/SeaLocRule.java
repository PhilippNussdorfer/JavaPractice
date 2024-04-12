package bbrz.textadventure.rules;

public class SeaLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.SEA;
    }

    @Override
    public boolean isInRules(MapRuleMark randomLocation) {
        return randomLocation == MapRuleMark.SEA ||
                randomLocation == MapRuleMark.BEACH ||
                randomLocation == MapRuleMark.CLIFF;
    }
}
