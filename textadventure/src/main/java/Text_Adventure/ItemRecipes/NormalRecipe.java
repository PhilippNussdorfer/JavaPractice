package Text_Adventure.ItemRecipes;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;
import java.util.List;

public class NormalRecipe extends ItemRecipes {     // should be used if the recipe only give back one item.

    public NormalRecipe(Player player, Item item, Game game, String uniqueText, Item ... recipeItemArr) {
        super(player, item, game, uniqueText, recipeItemArr);
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
        player.addItemToScrip(item);
        System.out.println(uniqueText);
    }
}
