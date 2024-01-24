package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;

public class PickupAction extends Action{

    public PickupAction(Player player, Game game, String... commandsArr) {
        super(player, game, "Picks up the item the player writes after the command.", commandsArr);
    }

    @Override
    public void execute() {
        for (int i = 1; i < words.length; i++) {
            Item item = game.getActiveRoom().getItemWithStrRoom(words[i]);
            if (item != null) {
                if (item.isPickAble()) {
                    player.addItemToScrip(item);
                    game.getActiveRoom().removeItem(item);
                } else {
                    System.out.println("The item you want to pickup is not take able. \n");
                }
            } else {
                System.out.println("you couldn't find the item you where searching for: " + item);
            }
        }
    }
}
