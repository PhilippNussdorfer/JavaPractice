package bbrz.textadventure.rules;

public class WoodsLocRule implements Rule {

    @Override
    public boolean canHandle(MapRuleMark prevLocation) {
        return prevLocation == MapRuleMark.WOODS;
    }

    @Override
    public boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        return prevLocation == randomLocation ||
                randomLocation == MapRuleMark.EDGE_OF_THE_FOREST ||
                randomLocation == MapRuleMark.CLEARING ||
                randomLocation == MapRuleMark.LAKE;
    }
}
