package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbAction implements Action {
    private String[] possibleCommands;
    protected Game game;

    @Override
    public boolean canHandle(String command) {
        for (String possibleCommand : possibleCommands) {
            if (possibleCommand.equals(command))
                return true;
        }
        return false;
    }
}
