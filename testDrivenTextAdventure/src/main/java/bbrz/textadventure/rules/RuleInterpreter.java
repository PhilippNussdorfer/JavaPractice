package bbrz.textadventure.rules;

import java.util.ArrayList;
import java.util.List;

public class RuleInterpreter {
    private final List<Rule> rules = new ArrayList<>();

    public void AddList(List<Rule> rules) {
        this.rules.addAll(rules);
    }

    public boolean interpretRule(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        for (Rule rule : rules) {
            if (rule.canHandle(prevLocation)) {
                return rule.isInRules(randomLocation);
            }
        }
        return false;
    }
}
