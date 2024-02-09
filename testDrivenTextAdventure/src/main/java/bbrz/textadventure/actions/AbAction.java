package bbrz.textadventure.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbAction implements Action {
    String[] possibleCommands;

    @Override
    public boolean canHandle(String command) {
        for (String possibleCommand : possibleCommands) {
            if (possibleCommand.equals(command))
                return true;
        }
        return false;
    }
}
