package Text_Adventure.Action;

import Text_Adventure.Item;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

import java.util.Arrays;
import java.util.List;

public class HelpAction extends Action{

    private final CommandInterpreter commandInterpreter;

    public HelpAction(CommandInterpreter commandInterpreter, String ... commandsArr) {
        super(null, null, "Help is the command to show all action commands the player can use.", commandsArr);
        this.commandInterpreter = commandInterpreter;
    }

    @Override
    public void execute() {

        /*old version of the command output
        List<String> commands = commandInterpreter.getKnownCommands();
        System.out.println("Those are all the possible commands: ");

        for (String commandStr : commands) {
            System.out.println(commandStr);
        }
        System.out.println();
        */
        List<List<String>> commands = commandInterpreter.getKnownCommands();
        System.out.println("Those are all the possible commands: ");

        System.out.println(AsciiTable.getTable(commands, Arrays.asList(new Column().header("Commands").dataAlign(HorizontalAlign.LEFT).with(nameDesc -> nameDesc.get(0)),
                new Column().header("Descriptions").with(nameDesc -> nameDesc.get(1)))));
    }
}
