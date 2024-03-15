package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.colors.TextColor;

import java.util.List;

public class HelpAction extends AbAction {

    public HelpAction(Game game, String ... possibleCommands) {
        super(game, "Help", "Shows the help for all possible Commands <Command>", possibleCommands);
    }

    private String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }

    @Override
    public void execute(String... params) {
        var actions = game.getInterpreter().getActionList();

        System.out.println("=".repeat(210));
        for (Action action : actions) {
            if (action instanceof DescriptionAction descAction) {
                game.getWrapper().outPrintlnColored(formatStringLength(15, action.getName()) + " => " + formatStringLength(100 ,action.getDescription())
                        + " | Commands => " + formatStringLength(30, getPrintableCollection(action.getPossibleCommands()))
                        + " | Additions => " + formatStringLength(30, getPrintableCollection(descAction.getAdditionList())), TextColor.CYAN);
            } else {
                game.getWrapper().outPrintlnColored(formatStringLength(15, action.getName()) + " => " + formatStringLength(100, action.getDescription())
                        + " | Commands => " + formatStringLength(30, getPrintableCollection(action.getPossibleCommands())), TextColor.CYAN);
            }
        }
        System.out.println("=".repeat(210));
    }

    private String getPrintableCollection(String[] strArr) {
        return String.join(", ", strArr);
    }

    private String getPrintableCollection(List<String> strArr) {
        return String.join(", ", strArr);
    }
}
