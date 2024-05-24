
package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.item.ItemStats;
import bbrz.textadventure.item.ItemType;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.tools.colors.TextColor;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoItemFoundException;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DescriptionActionTest {

    DescriptionAction action;

    @Mock
    Game game;
    @Mock
    OutputWrapper wrapper;
    @Mock
    Location location;
    @Mock
    Item item;
    @Mock
    Player player;
    @Mock
    Backpack bp;
    @Mock
    ItemStats itemStats;
    @Mock
    EntityStats stats;

    @BeforeEach
    void beforeEach() {
        action = new DescriptionAction(game, wrapper, "desc", "describe", "d");
    }

    @Test
    void simpleInputs() throws CommandNotFoundException, NoItemFoundException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getDescription()).thenReturn("Hello world");
        action.execute("d");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("Hello world\n", TextColor.DARK_BROWN);

        Mockito.when(location.getDescription()).thenReturn("this is a location");
        action.execute("desc");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("this is a location\n", TextColor.DARK_BROWN);

        Mockito.when(location.getDescription()).thenReturn("location description");
        action.execute("describe");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("location description\n", TextColor.DARK_BROWN);
    }

    @Test
    void inputsWithAdditions() throws CommandNotFoundException, NoItemFoundException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getDescription()).thenReturn("Hello world");
        action.execute("D", "Location");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("Hello world\n", TextColor.DARK_BROWN);

        Mockito.when(location.getName()).thenReturn("Your location");
        action.execute("d", "LocationName");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("Your location\n", TextColor.DARK_BROWN);
    }

    @Test
    void describeItem() throws NoItemFoundException, CommandNotFoundException {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(item.getName()).thenReturn("candle");
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(List.of(item));
        Mockito.when(location.getItems()).thenReturn(new ArrayList<>());
        Mockito.when(item.getDescription()).thenReturn("Hello World");
        Mockito.when(item.getStats()).thenReturn(itemStats);
        Mockito.when(itemStats.getItemStats()).thenReturn(List.of(0, 0, 0));
        Mockito.when(item.getType()).thenReturn(ItemType.CONSUMABLE);

        action.execute("d", "ItemDesc", "Candle");
        Mockito.verify(wrapper, Mockito.times(1)).outPrintlnColored("Hello World, it is a: CONSUMABLE, bonus stats Hp: 0, Armor: 0, Dmg: 0", TextColor.DARK_BROWN);
    }

    @Test
    void describeItemsThrowException() {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(item.getName()).thenReturn("candle");
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(List.of(item));
        Mockito.when(location.getItems()).thenReturn(new ArrayList<>());

        assertThrows(NoItemFoundException.class, () -> action.execute("d", "itemDesc", "name"));
    }

    @Test
    void describeItemFailed() {
        assertThrows(CommandNotFoundException.class, () -> action.execute("d", "item"));
    }

    @Test
    void invalidAndValidCommands() {
        var res = action.canHandle("w");
        assertFalse(res);

        res = action.canHandle("d");
        assertTrue(res);
    }

    @Test
    void helpMessage() {
        assertEquals("Describe        => To describe an location, item or get the location name <Command> <Addition> <Item name>              | Commands => desc, describe, d              | Additions => location, locationName, itemDesc", action.helpMessage());
    }
}