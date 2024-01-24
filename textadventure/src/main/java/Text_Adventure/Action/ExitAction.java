package Text_Adventure.Action;

import Text_Adventure.Player;

public class ExitAction extends Action{
    public ExitAction(Player player, String... commandsArr) {
        super(player, null, "Exits the game.", commandsArr);
    }

    @Override
    public void execute() {
        System.out.println("Goodbye " + player.getName() + " have an wonderful day.");
        System.exit(0);
    }
}
