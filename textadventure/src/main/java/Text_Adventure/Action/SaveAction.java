package Text_Adventure.Action;

import Text_Adventure.Game;
import Text_Adventure.Player;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveAction extends Action {
    public SaveAction(Player player, Game game, String description, String... commandsArr) {
        super(player, game, description, commandsArr);
    }

    @Override
    public void execute() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Save.sv");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // https://www.youtube.com/watch?v=uGYZn6xk-hA
    /*@Override
    public void execute() {

    }*/
}
