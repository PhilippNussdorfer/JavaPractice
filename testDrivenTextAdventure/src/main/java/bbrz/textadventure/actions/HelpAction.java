package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.colors.TextColor;
import bbrz.textadventure.tools.StringFormatting;

import java.util.List;

public class HelpAction extends AbAction {

    private final StringFormatting formatter = new StringFormatting();

    public HelpAction(Game game, String ... possibleCommands) {
        super(game, "Help", "Shows the help for all possible Commands <Command>", possibleCommands);
    }

    @Override
    public void execute(String... params) {
        var actions = game.getInterpreter().getActionList();

        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
        for (Action action : actions) {
            if (action instanceof DescriptionAction descAction) {
                game.getWrapper().outPrintlnColored(formatter.formatStringLength(15, action.getName()) + " => " + formatter.formatStringLength(100 ,action.getDescription())
                        + " | Commands => " + formatter.formatStringLength(30, getPrintableCollection(action.getPossibleCommands()))
                        + " | Additions => " + formatter.formatStringLength(30, getPrintableCollection(descAction.getAdditionList())), TextColor.CYAN);
            } else {
                game.getWrapper().outPrintlnColored(formatter.formatStringLength(15, action.getName()) + " => " + formatter.formatStringLength(100, action.getDescription())
                        + " | Commands => " + formatter.formatStringLength(30, getPrintableCollection(action.getPossibleCommands())), TextColor.CYAN);
            }
        }
        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
    }
}
