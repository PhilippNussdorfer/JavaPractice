package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.ItemRecipes.RecipeInterpreter;
import Text_Adventure.Player;

import java.util.ArrayList;
import java.util.List;

public class CombineAction extends Action {

    public CombineAction(Player player, Game game, String... commandsArr) {
        super(player, game, "Combines tow ore more items.", commandsArr);
    }

    @Override
    public void execute() {

        List<Item> combineItems = new ArrayList<>();
        RecipeInterpreter recipeInter = new RecipeInterpreter(game, player);
        for (String strItem : words) {
            Item temp = player.getItemWithStrInv(strItem);
            if (temp == null) {
                temp = game.getActiveRoom().getItemWithStrRoom(strItem);
            }
            combineItems.add(temp);
        }
        recipeInter.interpret(combineItems);
    }
}
