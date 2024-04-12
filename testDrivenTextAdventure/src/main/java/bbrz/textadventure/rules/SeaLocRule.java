package bbrz.textadventure.rules;

public class SeaLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.SEA;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation == randomLocation ||
                randomLocation == MapRuleMark.BEACH ||
                randomLocation == MapRuleMark.CLIFF;
    }
}
