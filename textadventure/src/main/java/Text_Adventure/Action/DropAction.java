package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;

public class DropAction extends Action {
    public DropAction(Player player, Game game, String... commandsArr) {
        super(player, game, "You drop the item you write after the drop item.", commandsArr);
    }

    @Override
    public void execute() {
        for (int i = 1; i < words.length; i++) {
            Item item = player.getItemWithStrInv(words[i]);
            if (item != null) {
                player.dropItem(item);
                game.getActiveRoom().addItem(item);
            } else {
                System.out.println("you couldn't find the item you where searching for: " + item);
            }
        }
    }
}
