package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.tools.StringFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DropEquipmentActionTest {

    DropEquipmentAction action;

    @Mock
    Game game;
    @Mock
    Player player;
    @Mock
    Equipped equipped;
    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    EntityStats stats;
    @Mock
    StringFormatter formatter;

    @BeforeEach
    void setUp() {
        action = new DropEquipmentAction(game, "de", "drop-eq");
    }

    @Test
    void execute() throws NoFreeSpaceException, NoItemFoundException {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getEq()).thenReturn(equipped);
        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item, secItem));
        Mockito.when(item.getName()).thenReturn("Wooden Round Shield");
        Mockito.when(secItem.getName()).thenReturn("Ring");

        action.execute("de", "ring");
        Mockito.verify(player, Mockito.times(1)).dropEquipment(game, secItem);

        action.execute("de", "none");
        Mockito.verify(player, Mockito.times(0)).dropEquipment(game, item);

        action.execute("drop-eq", "Wooden Round Shield");
        Mockito.verify(player, Mockito.times(1)).dropEquipment(game, item);
    }

    @Test
    void throwException() {
        assertThrows(NoItemFoundException.class, ()->action.execute("eq"));
    }

    @Test
    void canHandle() {
        assertTrue(action.canHandle("drop-EQ"));
        assertTrue(action.canHandle("DE"));
        assertFalse(action.canHandle("exit"));
    }

    @Test
    void helpMessage() {
        Mockito.when(game.getFormatter()).thenReturn(formatter);
        Mockito.when(formatter.getPrintableCollection(Mockito.any(String[].class))).thenReturn("de, drop-eq                   ");
        Mockito.when(formatter.formatStringLength(Mockito.anyInt(), Mockito.anyString())).thenReturn("Drop Equipment ", "drops the item in the backpack or to the floor when the backpack is full <Command> <Item Name>      ", "de, drop-eq                   ");

        assertEquals("Drop Equipment  => drops the item in the backpack or to the floor when the backpack is full <Command> <Item Name>       | Commands => de, drop-eq                   ", action.helpMessage());
    }
}
