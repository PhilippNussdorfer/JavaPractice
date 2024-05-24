package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.StringFormatter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbAction implements Action {
    protected Game game;
    private String name;
    private String description;
    private String[] possibleCommands;

    @Override
    public boolean canHandle(String command) {
        for (String possibleCommand : possibleCommands) {
            if (possibleCommand.equalsIgnoreCase(command))
                return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String[] getPossibleCommands() {
        return this.possibleCommands;
    }
}
