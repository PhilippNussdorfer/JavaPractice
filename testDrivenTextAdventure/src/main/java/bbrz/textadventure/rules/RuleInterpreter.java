package bbrz.textadventure.rules;

import java.util.ArrayList;
import java.util.List;

public class RuleInterpreter {
    private final List<Rule> rules = new ArrayList<>();

    public RuleInterpreter() {
        init();
    }

    private void init() {
        rules.addAll(List.of(
            new StartingLocRule()
        ));
    }

    public boolean interpretRule(MapRuleMark prevLocation, MapRuleMark randomLocation) {
        for (Rule rule : rules) {
            if (rule.canHandle(prevLocation)) {
                return rule.isInRules(prevLocation, randomLocation);
            }
        }
        return false;
    }
}
