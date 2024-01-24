package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Player;

public class UseAction extends Action {

    public UseAction(Player player, Game game, String description, String... commandsArr) {
        super(player, game, description, commandsArr);
    }

    @Override
    public void execute() {

    }
}
