package Text_Adventure.ItemRecipes;

import Text_Adventure.Game;
import Text_Adventure.Item;
import Text_Adventure.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeInterpreter {

    private Game game;
    private Player player;
    private List<ItemRecipes> recipesList;

    public RecipeInterpreter(Game game, Player player) {

        this.game = game;
        this.player = player;
        this.recipesList = new ArrayList<>();

        init();
    }

    private void init() {

        NormalRecipe bucketWithRope = new NormalRecipe(this.player, this.game.getItemFromItemMap("ropeB"),
                this.game, "A bucket with a rope", this.game.getItemFromItemMap("rope"), this.game.getItemFromItemMap("bucket"));
        NormalRecipe litOilLampMa = new NormalRecipe(this.player, this.game.getItemFromItemMap("litL"),
                this.game, "Now I have a light", this.game.getItemFromItemMap("mat1"), this.game.getItemFromItemMap("cleanL"));

        RecipeWhereOnlyOneItemIsUsed waterBucket = new RecipeWhereOnlyOneItemIsUsed(this.player, this.game.getItemFromItemMap("waterB"),
                this.game, "Fells a bit wet in my scrip, I'm not sure way", this.game.getItemFromItemMap("well"), this.game.getItemFromItemMap("ropeB"));
        RecipeWhereOnlyOneItemIsUsed litOilLampFP = new RecipeWhereOnlyOneItemIsUsed(this.player, this.game.getItemFromItemMap("litL"),
                this.game, "Was a bit hot to light the lamp, but it was worth it", this.game.getItemFromItemMap("fireP"), this.game.getItemFromItemMap("cleanL"));
        RecipeWhereOnlyOneItemIsUsed bucketWithRopeDWH = new RecipeWhereOnlyOneItemIsUsed(this.player, this.game.getItemFromItemMap("ropeB"),
                this.game, "The hole looks like to not fill up", this.game.getItemFromItemMap("dWaterH"), this.game.getItemFromItemMap("dWaterB"));

        CombAndGiveBackTwoItemsPlayerAndRoom bucketWithRopeAndDirtyWaterHole = new CombAndGiveBackTwoItemsPlayerAndRoom(this.player, new Item[]{this.game.getItemFromItemMap("ropeB"),
                this.game.getItemFromItemMap("dWaterH")}, this.game, "The bucket is still wet but not dirty anymore", this.game.getItemFromItemMap("dWaterB"),
                this.game.getItemFromItemMap("holeG"));

        CombAndGivesBackTwoItemsForThePlayer wetSponge = new CombAndGivesBackTwoItemsForThePlayer(this.player, new Item[] {this.game.getItemFromItemMap("wetSpo"),
                this.game.getItemFromItemMap("waterB")}, this.game, "Nice a wet sponge, its not enough that my scrip is wet from the water bucket", this.game.getItemFromItemMap("spo"),
                this.game.getItemFromItemMap("waterB"));
        CombAndGivesBackTwoItemsForThePlayer cleanLampRecipe = new CombAndGivesBackTwoItemsForThePlayer(this.player, new Item[] {this.game.getItemFromItemMap("dirtySpo"),
                this.game.getItemFromItemMap("cleanL")}, this.game, "Now the dirty lamp is clean but my scrip is now dirty from the dirty sponge", this.game.getItemFromItemMap("wetSpo"),
                this.game.getItemFromItemMap("dirtyL"));
        CombAndGivesBackTwoItemsForThePlayer cleanWetSponge = new CombAndGivesBackTwoItemsForThePlayer(this.player, new Item[] {this.game.getItemFromItemMap("wetSpo"),
                this.game.getItemFromItemMap("dWaterB")}, this.game, "The sponge is clean but for how long", this.game.getItemFromItemMap("dirtySpo"),
                this.game.getItemFromItemMap("waterB"));

        addRecipe(cleanLampRecipe);
        addRecipe(wetSponge);
        addRecipe(waterBucket);
        addRecipe(bucketWithRope);
        addRecipe(litOilLampFP);
        addRecipe(litOilLampMa);
        addRecipe(bucketWithRopeAndDirtyWaterHole);
        addRecipe(cleanWetSponge);
        addRecipe(bucketWithRopeDWH);
    }

    public void interpret(List<Item> itemList) {

        int containsItems = 0;
        for (ItemRecipes recipe : recipesList) {
            for (Item item : itemList) {
                if (Arrays.stream(recipe.getRecipeItemArr()).toList().contains(item)) {
                    containsItems++;
                }
                if (containsItems == recipe.getRecipeItemArr().length) {
                    recipe.combine(itemList);
                }
            }
            containsItems = 0;
        }
    }

    public void addRecipe(ItemRecipes recipe) {
        this.recipesList.add(recipe);
    }
}
