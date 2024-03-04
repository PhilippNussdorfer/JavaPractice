package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.rooms.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DropActionTest {

    DropAction action;

    @Mock
    Game game;
    @Mock
    Location location;
    @Mock
    Player player;
    @Mock
    Item item;

    @BeforeEach
    void setUp() {
        action = new DropAction(game, "drop");
    }

    @Test
    void execute() {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(item.getName()).thenReturn("name");
        Mockito.when(player.getBackpack()).thenReturn(List.of(item));

        action.execute("Drop", "name");
        Mockito.verify(location, Mockito.times(1)).addItems(item);
        Mockito.verify(player, Mockito.times(1)).BPRemoveItems(item);
    }

    @Test
    void canHandle() {
        assertTrue(action.canHandle("DrOp"));
        assertFalse(action.canHandle("x"));
    }

    @Test
    void throwsNewIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> action.execute("drop"));
    }
}