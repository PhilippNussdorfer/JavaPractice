package bbrz.textadventure.actions;

import java.util.ArrayList;
import java.util.List;

public abstract class AbAction implements Action {
    List<String> possibleCommands = new ArrayList<>();

    @Override
    public boolean canHandle(String command) {
        for (String possibleCommand : possibleCommands) {
            if (possibleCommand.equals(command))
                return true;
        }
        return false;
    }
}
