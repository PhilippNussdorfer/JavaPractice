package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class MoveAction extends AbAction {

    public MoveAction(Game game, String ... possibleCommands) {
        super(game, "Move", "To move to an other location <Command>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) {
        game.move(commandAndParams[0]);
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
