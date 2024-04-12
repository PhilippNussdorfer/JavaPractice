package bbrz.textadventure.rules;

public class StartingLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return MapRuleMark.STARTING_LOCATION == prevLocation;
    }

    @Override
    public boolean isInRules(MapRuleMark randomLocation) {
        return randomLocation != MapRuleMark.STARTING_LOCATION;
    }
}
