package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandInterpreter implements Serializable {

    private Player player;
    private Game game;
    private List<Action> actions;

    public CommandInterpreter(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.actions = new ArrayList<>();

        init();
    }

    private void init() {

        HelpAction helpAction = new HelpAction(this, "help", "h");
        ExitAction exitAction = new ExitAction(player, "exit", "stop", "ex");
        PickupAction pickupAction = new PickupAction(player, game, "pickup");
        DropAction dropAction = new DropAction(player, game, "drop");
        ShowInvAction showInvAction = new ShowInvAction(player, "inv", "i", "inventory");
        InvestigateAction investigateAction = new InvestigateAction(player, game,
                "Shows the description to" +
                        " every item.", "in", "investigate", "invest");
        CombineAction combineAction = new CombineAction(player, game, "combine", "comb", "c");
        WaitAction waitAction = new WaitAction(player, game, "With this Action you can wait for a creature until it moves.", "wait");
        SaveAction saveAction = new SaveAction(player, game, "Saves the game.", "save");
        LoadAction loadAction = new LoadAction(player, game, "Loads the last saved game.", "load");

        addAction(helpAction);
        addAction(exitAction);
        addAction(pickupAction);
        addAction(dropAction);
        addAction(showInvAction);
        addAction(investigateAction);
        addAction(combineAction);
        addAction(waitAction);
        addAction(saveAction);
        addAction(loadAction);
    }

    public void interpret(String input) {

        for (Action action : actions) {

            if (action.shouldExecute(input)) {
                action.execute();
            }
        }
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public List<List<String>> getKnownCommands() {

        List<List<String>> lines = new ArrayList<>();
        for (Action action : this.actions) {
            String commandStr = "";
            List<String> nameDesc = new ArrayList<>();
            for (String command : action.getCommands()) {
                commandStr += command + ", ";
            }
            commandStr = commandStr.substring(0, commandStr.length() - 2);
            nameDesc.add(commandStr);
            nameDesc.add(action.description);
            lines.add(nameDesc);
        }

        return lines;
    }

    /* old version of getKnownCommands

    public List<String> getKnownCommands() {

        List<String> commands = new ArrayList<>();
        for (Action action : this.actions) {
            String commandsStr = "";
            for (String command : action.getCommands()) {
                commandsStr = commandsStr.concat(command + ", ");
            }
            commands.add(commandsStr.substring(0, commandsStr.length() - 2) + " :" + " ".repeat(30 -
                    commandsStr.length()) + action.description);
        }
        return commands;
    }*/
}
