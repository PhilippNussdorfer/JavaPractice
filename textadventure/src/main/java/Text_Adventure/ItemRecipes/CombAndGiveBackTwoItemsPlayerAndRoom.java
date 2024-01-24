package Text_Adventure.ItemRecipes;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;

import java.util.List;

public class CombAndGiveBackTwoItemsPlayerAndRoom extends ItemRecipes {   // Used for combine actions where te action
    // causes to change the unpick able item and gives 2 items back, one for the player and the changed item for the room.

    public CombAndGiveBackTwoItemsPlayerAndRoom(Player player, Item[] itemArr, Game game, String uniqueText, Item... recipeItemArr) {
        super(player, itemArr, game, uniqueText, recipeItemArr);
    }

    @Override
    public void combine(List<Item> items) {

        for(Item listItem : items) {
            for (Item recipeItem : getRecipeItemArr()) {
                if (listItem == recipeItem) {
                    if (player.getScrip().remove(listItem)) {
                        System.out.println("Used " + listItem.getName() + " Item to combine\n");
                    }
                    if (game.getActiveRoom().getItemsInTheRoom().remove(listItem)) {
                        System.out.println("Used " + listItem.getName() + " Item to combine\n");
                    }
                }
            }
        }
        player.addItemToScrip(itemArr[0]);
        game.getActiveRoom().addItem(itemArr[1]);
        System.out.println(uniqueText);
    }
}
