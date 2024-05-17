package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PickUpActionTest {

    PickUpAction action;

    @Mock
    Game game;
    @Mock
    Player player;
    @Mock
    Location location;
    @Mock
    Item item;

    @BeforeEach
    void setUp() {
        action = new PickUpAction(game, "pickup", "p");
    }

    @Test
    void execute() throws NoFreeSpaceException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getItems()).thenReturn(List.of(item));
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(item.getName()).thenReturn("name");

        action.execute("p", "name");
        Mockito.verify(player, Mockito.times(1)).bpAdd(item);
        Mockito.verify(location, Mockito.times(1)).removeItems(item);
    }

    @Test
    void canHandle() {
        assertTrue(action.canHandle("picKup"));
        assertFalse(action.canHandle("exit"));
    }

    @Test
    void throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> action.execute("p"));
    }

    @Test
    void helpMessage() {
        assertEquals("Pick up         => To pick up an item in an location <Command> <Item name>                                              | Commands => pickup, p                     ", action.helpMessage());
    }
}