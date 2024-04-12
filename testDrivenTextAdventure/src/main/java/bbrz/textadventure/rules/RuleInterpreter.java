package bbrz.textadventure.rules;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RuleInterpreter {
    @Getter
    private final List<Rule> rules = new ArrayList<>();

    public void addList(List<Rule> rules) {
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
