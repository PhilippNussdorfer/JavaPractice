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
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
