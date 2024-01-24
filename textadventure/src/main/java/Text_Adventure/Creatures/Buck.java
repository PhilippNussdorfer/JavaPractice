package Text_Adventure.Creatures;

import Text_Adventure.Room;

import java.util.ArrayList;
import java.util.List;

public class Buck extends Creatures {

    public Buck(Room creatureActiveRoom) {
        super("Strange Buck", "You can see a strange creature, it looks a bit like a roebuck but it has strange strips on its body that, glows a light turquoise color.", creatureActiveRoom, false);
    }
}
