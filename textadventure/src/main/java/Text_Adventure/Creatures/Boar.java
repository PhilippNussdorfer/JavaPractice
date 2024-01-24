package Text_Adventure.Creatures;

import Text_Adventure.Room;

import java.util.ArrayList;
import java.util.List;

public class Boar extends Creatures {

    public Boar(Room creatureActiveRoom) {
        super("Giant Boar", "You can see a giant boar, it is at least as high as you your self it seems to be friendly at the moment but be careful around it.", creatureActiveRoom, true);
    }
}
