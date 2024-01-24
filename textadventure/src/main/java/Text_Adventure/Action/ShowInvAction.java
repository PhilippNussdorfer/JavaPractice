package Text_Adventure.Action;

import Text_Adventure.Player;

public class ShowInvAction extends Action{

    public ShowInvAction(Player player, String... commandsArr) {
        super(player, null, "Shows your inventory.", commandsArr);
    }

    @Override
    public void execute() {
        player.showItemsInScrip();
    }
}
