package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;

public class InvestigateAction extends Action {

    public InvestigateAction(Player player, Game game, String description, String... commandsArr) {
        super(player, game, description, commandsArr);
    }

    @Override
    public void execute() {

        for (int i = 1; i < words.length; i++) {
            Item item = player.getItemWithStrInv(words[i]);
            Item item_ = game.getActiveRoom().getItemWithStrRoom(words[i]);

            if (item != null) {
                System.out.println(words[i] + ":" + " ".repeat(20 - words[i].length()) + item.getDescription());
                item.printItemIMG();
            } else if (item_ != null) {
                System.out.println(words[i] + ":" + " ".repeat(20 - words[i].length()) + item_.getDescription());
                item_.printItemIMG();
            }
        }
        System.out.println();
    }
}
