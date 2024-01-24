package Text_Adventure.Action;

import Text_Adventure.Creatures.Creatures;
import Text_Adventure.Game;
import Text_Adventure.Player;

import java.util.List;

public class WaitAction extends Action {
    public WaitAction(Player player, Game game, String description, String... commandsArr) {
        super(player, game, description, commandsArr);
    }

    @Override
    public void execute() {
        List<Creatures> creaturesList = game.getAllCreaturesList();
        for (Creatures creature : creaturesList) {
            creature.moveRoom();
        }
        System.out.println("You have waited for the creatures to Move.\n");
    }
}
