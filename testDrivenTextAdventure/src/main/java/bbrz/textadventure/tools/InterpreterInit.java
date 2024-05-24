package bbrz.textadventure.tools;

import bbrz.textadventure.Game;
import bbrz.textadventure.actions.*;
import bbrz.textadventure.locations.Directions;
import bbrz.textadventure.rules.*;

import java.util.ArrayList;
import java.util.List;

public class InterpreterInit {

    public static CommandInterpreter initActionInterpreter(Game game, OutputWrapper wrapper) {

        CommandInterpreter commandInterpreter = new CommandInterpreter();

        commandInterpreter.addActions(new HelpAction(game, "h", "help"));
        commandInterpreter.addActions(new MoveAction(game, combineListsAndGetStringArray()));
        commandInterpreter.addActions(new ExitAction(game, "ex", "x", "exit"));
        commandInterpreter.addActions(new DescriptionAction(game, wrapper, "d", "desc", "describe"));
        commandInterpreter.addActions(new PickUpAction(game, "pickup", "pick"));
        commandInterpreter.addActions(new DropAction(game, "drop"));
        commandInterpreter.addActions(new BackpackAction(game, "bp", "backpack"));
        commandInterpreter.addActions(new PlayerInfoAction(game, "player-info", "p-info", "pi"));
        commandInterpreter.addActions(new EquipAction(game, "eq", "equip"));
        commandInterpreter.addActions(new DropEquipmentAction(game, "de", "drop-equipment", "drop-eq"));
        commandInterpreter.addActions(new SwapEquipmentAction(game, "swap", "swap-eq", "swap-equipment"));

        return commandInterpreter;
    }

    private static String[] combineListsAndGetStringArray() {
        List<String> directionsList = new ArrayList<>();

        directionsList.addAll(Directions.NORTH.getValues());
        directionsList.addAll(Directions.EAST.getValues());
        directionsList.addAll(Directions.WEST.getValues());
        directionsList.addAll(Directions.SOUTH.getValues());

        String[] directions = new String[directionsList.size()];

        for (int i = 0; i < directionsList.size(); i++) {
            directions[i] = directionsList.get(i);
        }

        return directions;
    }

    public static RuleInterpreter initRuleInterpreter() {
        var ruleInterpreter = new RuleInterpreter();

        ruleInterpreter.addList(List.of(
                new BeachLocRule(),
                new ClearingLocRule(),
                new CliffLocRule(),
                new EdgeOfTheForestLocRule(),
                new EdgeOfTheSwampLocRule(),
                new LakeLocRule(),
                new MeadowLocRule(),
                new SeaLocRule(),
                new StartingLocRule(),
                new SwampLocRule(),
                new WellLocRule(),
                new WoodsLocRule()
        ));

        return ruleInterpreter;
    }
}
