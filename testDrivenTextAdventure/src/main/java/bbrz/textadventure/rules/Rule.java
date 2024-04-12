package bbrz.textadventure.rules;

public interface Rule {
    boolean canHandle(MapRuleMark prevLocation);
    boolean isInRules(MapRuleMark prevLocation, MapRuleMark randomLocation);
}
