package bbrz.textadventure.actions;

import bbrz.textadventure.Game;

public class ExitAction extends AbAction {
    public ExitAction(Game game, String ... possibleCommands) {
        super(game, "Exit", "Exits the game <Command>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) {
        game.setLoopGame(false);
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
