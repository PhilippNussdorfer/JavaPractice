package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.tools.StringFormatter;

public class PlayerInfoAction extends AbAction {

    public PlayerInfoAction(Game game, String ... possibleCommands) {
        super(game, "Player info", "Shows information about the player and his equipment <Command>", possibleCommands);
    }

    @Override
    public void execute(String... commandAndParams) {
        Player player = game.getPlayer();
        var playerStats = player.getBoostedStats();

        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
        game.getWrapper().outPrintlnColored("You are: " + player.getName() + "\n\n" +
                "HP: " + playerStats.get(0) + "/" + player.getActualHp() + "\nArmor: " + playerStats.get(1) + "\nDmg: " + playerStats.get(2) +
                "\n\nYour equipment that you have equipped is:\n" + game.getFormatter().getPrintableStringFromItemList(player.getStats().getEq().getEquippedList()) +
                "\nYou have " + (player.getStats().getEq().getEQUIPPED_SPACE() - player.getStats().getEq().getEquippedList().size()) + " equipment slots free.", TextColor.MAGENTA);
        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
    }

    @Override
    public String helpMessage() {
        return game.getFormatter().formatStringLength(15, getName()) + " => " + game.getFormatter().formatStringLength(100, getDescription())
                + " | Commands => " + game.getFormatter().formatStringLength(30, game.getFormatter().getPrintableCollection(getPossibleCommands()));
    }
}
