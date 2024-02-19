package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class descriptionAction extends AbAction {

    public descriptionAction(Game game, String ... possibleCommands) {
        super(possibleCommands, game);
    }

    @Override
    public void execute(String... params) {
        if (params.length > 1) {
            if (true) {

            }
        }
    }
}
