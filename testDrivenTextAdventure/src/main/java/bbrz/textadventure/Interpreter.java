package bbrz.textadventure;

import bbrz.textadventure.actions.Action;
import bbrz.textadventure.customException.CommandNotFoundException;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Interpreter {
    private List<Action> actionList = new ArrayList<>();

    public void addActions(Action ... actions) {
        for (Action action : actions) {
            actionList.add(action);
        }
    }

    public void interpret(String commandWithParams) throws CommandNotFoundException {
        String[] words = commandWithParams.toLowerCase().split(" ");

        for (Action action : actionList) {
            if (action.canHandle(words[0])) {
                action.execute(words);
                return;
            }
        }

        throw new CommandNotFoundException("No such Command found.");
    }
}