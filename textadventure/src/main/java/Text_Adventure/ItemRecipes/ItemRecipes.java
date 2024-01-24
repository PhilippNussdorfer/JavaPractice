package Text_Adventure.ItemRecipes;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;

import java.util.List;

public abstract class ItemRecipes {

    protected Player player;
    protected Game game;
    protected Item[] itemArr;
    protected Item item;
    protected String uniqueText;
    private Item[] recipeItemArr;


    public ItemRecipes(Player player, Item[] itemArr, Game game, String uniqueText, Item ... recipeItemArr) {
        this.player = player;
        this.itemArr = itemArr;
        this.game = game;
        this.recipeItemArr = recipeItemArr;
        this.uniqueText = uniqueText;
    }

    public ItemRecipes(Player player, Item item, Game game, String uniqueText, Item ... recipeItemArr) {
        this.player = player;
        this.item = item;
        this.game = game;
        this.recipeItemArr = recipeItemArr;
        this.uniqueText = uniqueText;
    }

    public Item[] getRecipeItemArr() {return this.recipeItemArr;}

    public abstract void combine(List<Item> items);
}
