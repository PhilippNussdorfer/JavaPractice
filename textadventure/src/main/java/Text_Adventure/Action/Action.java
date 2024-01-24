package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Action implements Serializable {

    protected Player player;
    protected Game game;
    private List<String> commands;
    protected String[] words;
    protected String description;

    public Action(Player player, Game game, String description, String ... commandsArr) {

        this.player = player;
        this.game = game;
        this.description = description;
        this.commands = new ArrayList<>();

        this.commands.addAll(Arrays.asList(commandsArr));
    }

    public List<String> getCommands() {return commands;}

    public boolean shouldExecute(String input) {
        this.words = input.split(" ");
        for (String command : this.commands) {
            if (words[0].equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }

    public abstract void execute();
}
