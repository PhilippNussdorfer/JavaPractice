package bbrz.textadventure.gameLoader;

import bbrz.textadventure.Game;
import bbrz.textadventure.actions.*;
import bbrz.textadventure.tools.Interpreter;
import bbrz.textadventure.tools.OutputWrapper;

public class InterpreterInit {

    public static Interpreter init(Game game, OutputWrapper wrapper) {
        Interpreter interpreter = new Interpreter();

        interpreter.addActions(new HelpAction(game, "h", "help"));
        interpreter.addActions(new MoveAction(game, "west", "north", "east", "south", "n", "s", "w", "e"));
        interpreter.addActions(new ExitAction(game, "ex", "x", "exit"));
        interpreter.addActions(new DescriptionAction(game, wrapper, "d", "desc", "describe"));
        interpreter.addActions(new PickUpAction(game, "pickup", "pick"));
        interpreter.addActions(new DropAction(game, "drop"));
        interpreter.addActions(new BackpackAction(game, "bp", "backpack"));
        interpreter.addActions(new PlayerInfoAction(game, "player-info", "p-info", "pi"));
        interpreter.addActions(new EquipAction(game, "eq", "equip"));
        interpreter.addActions(new DropEquipmentAction(game, "de", "drop-equipment", "drop-eq"));
        interpreter.addActions(new SwapEquipmentAction(game, "swap", "swap-eq", "swap-equipment"));

        return interpreter;
    }
}
