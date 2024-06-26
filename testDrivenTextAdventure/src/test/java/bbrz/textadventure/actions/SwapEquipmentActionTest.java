package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.entity.EntityStats;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.item.ItemType;
import bbrz.textadventure.locations.Location;
import bbrz.textadventure.tools.StringFormatter;
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
class SwapEquipmentActionTest {

    SwapEquipmentAction action;
    @Mock
    Game game;
    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    Player player;
    @Mock
    Equipped equipped;
    @Mock
    Location location;
    @Mock
    Backpack backpack;
    @Mock
    EntityStats stats;
    @Mock
    StringFormatter formatter;

    @BeforeEach
    void setUp() {
        action = new SwapEquipmentAction(game, "swap", "swap-eq", "swap-equipment");
    }

    @Test
    void execute() throws NoFreeSpaceException, CommandNotFoundException {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getEq()).thenReturn(equipped);
        Mockito.when(stats.getBp()).thenReturn(backpack);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item));
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(item.getName()).thenReturn("bronze helmet");
        Mockito.when(backpack.getBackpack()).thenReturn(List.of(secItem));
        Mockito.when(secItem.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(secItem.getName()).thenReturn("iron helmet");
        Mockito.when(location.getItems()).thenReturn(new ArrayList<>());

        action.execute("swap", "bronze helmet", "iron helmet");
        Mockito.verify(player, Mockito.times(1)).dropEquipment(game, item);
        Mockito.verify(player, Mockito.times(1)).addEquipment(secItem);
    }

    @Test
    void executeWithOneNotExistingItem() throws NoFreeSpaceException, CommandNotFoundException {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getEq()).thenReturn(equipped);
        Mockito.when(stats.getBp()).thenReturn(backpack);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item));
        Mockito.when(item.getName()).thenReturn("bronze helmet");
        Mockito.when(backpack.getBackpack()).thenReturn(List.of(secItem));
        Mockito.when(secItem.getName()).thenReturn("iron helmet");
        Mockito.when(location.getItems()).thenReturn(new ArrayList<>());

        assertThrows(CommandNotFoundException.class, () -> action.execute("swap", "bronze helmet", "iron"));
        Mockito.verify(player, Mockito.times(0)).dropEquipment(game, item);
    }

    @Test
    void throwsCommandNotFoundException() {
        assertThrows(CommandNotFoundException.class, ()->action.execute("swap"));
        assertThrows(CommandNotFoundException.class, ()->action.execute("swap", "item"));
    }

    @Test
    void throwsIllegalArgumentException() {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getStats()).thenReturn(stats);
        Mockito.when(stats.getEq()).thenReturn(equipped);
        Mockito.when(stats.getBp()).thenReturn(backpack);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(equipped.getEquippedList()).thenReturn(List.of(item));
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(item.getName()).thenReturn("bronze helmet");
        Mockito.when(backpack.getBackpack()).thenReturn(List.of(secItem));
        Mockito.when(secItem.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(secItem.getName()).thenReturn("iron helmet");
        Mockito.when(location.getItems()).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, ()->action.execute("swap", "bronze helmet", "iron helmet"));
    }

    @Test
    void canHandle() {
        assertTrue(action.canHandle("swap"));
        assertTrue(action.canHandle("Swap-Eq"));
        assertFalse(action.canHandle("exiT"));
    }

    @Test
    void helpMessage() {
        Mockito.when(game.getFormatter()).thenReturn(formatter);
        Mockito.when(formatter.getPrintableCollection(Mockito.any(String[].class))).thenReturn("swap, swap-eq, swap-equipment ");
        Mockito.when(formatter.formatStringLength(Mockito.anyInt(), Mockito.anyString())).thenReturn("Swap Equipment ", "Swaps the equipped item with an item you want to equip make sure they are from te same type\n" +
                "                   <Command> <Equipped Item Name> <Item Name of new Equipment>                                         ", "swap, swap-eq, swap-equipment ");

        assertEquals("Swap Equipment  => Swaps the equipped item with an item you want to equip make sure they are from te same type\n" +
                "                   <Command> <Equipped Item Name> <Item Name of new Equipment>                                          | Commands => swap, swap-eq, swap-equipment ", action.helpMessage());
    }
}