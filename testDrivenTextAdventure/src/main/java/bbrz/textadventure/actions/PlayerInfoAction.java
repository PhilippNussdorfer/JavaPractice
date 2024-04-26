package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.tools.StringFormatting;

public class PlayerInfoAction extends AbAction {

    private final StringFormatting formatter = new StringFormatting();

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
                "\n\nYour equipment that you have equipped is:\n" + formatter.getPrintableStringFromItemList(player.getEquipped().getEquippedList()) +
                "\nYou have " + (player.getEquipped().getEQUIPPED_SPACE() - player.getEquipped().getEquippedList().size()) + " equipment slots free.", TextColor.MAGENTA);
        game.getWrapper().outPrintlnColored("=".repeat(210), TextColor.DARK_BROWN);
    }

    @Override
    public String helpMessage() {
        return formatter.formatStringLength(15, getName()) + " => " + formatter.formatStringLength(100, getDescription())
                + " | Commands => " + formatter.formatStringLength(30, formatter.getPrintableCollection(getPossibleCommands()));
    }
}
