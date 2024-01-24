package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Player;

import java.io.*;

public class LoadAction extends Action {

    public LoadAction(Player player, Game game, String description, String... commandsArr) {
        super(player, game, description, commandsArr);
    }

    @Override
    public void execute() {
        try {
            FileInputStream fileInputStream = new FileInputStream("Save.sv");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Game savedGame = (Game) objectInputStream.readObject();
            game.initGame(savedGame);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
