package bbrz.textadventure.actions;

import bbrz.textadventure.Game;
import bbrz.textadventure.customException.CommandNotFoundException;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.item.ItemType;
import bbrz.textadventure.locatins.Location;
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
class EquipActionTest {

    EquipAction action;

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
    Backpack bp;

    @BeforeEach
    void setUp() {
        action = new EquipAction(game, "eq", "equip");
    }

    @Test
    void executeWithSingleItemFromLocation() throws CommandNotFoundException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getItems()).thenReturn(List.of(item));
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getEquipped()).thenReturn(equipped);
        Mockito.when(item.getName()).thenReturn("name");
        Mockito.when(player.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(new ArrayList<>());
        Mockito.when(equipped.eqAddItems(item)).thenReturn(true);

        action.execute("eq", "name");
        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(item);
        Mockito.verify(location, Mockito.times(1)).removeItems(item);
    }

    @Test
    void executeWithSingleItemFromLocationAndGetsNotAdded() throws CommandNotFoundException {
        Mockito.when(game.getCurrentLocation()).thenReturn(location);
        Mockito.when(location.getItems()).thenReturn(List.of(item));
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getEquipped()).thenReturn(equipped);
        Mockito.when(item.getName()).thenReturn("name");
        Mockito.when(player.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(new ArrayList<>());
        Mockito.when(equipped.eqAddItems(item)).thenReturn(false);

        action.execute("eq", "name");
        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(item);
        Mockito.verify(location, Mockito.times(0)).removeItems(item);
    }

    @Test
    void executeWithSingleItemFromBackpack() throws CommandNotFoundException {
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getEquipped()).thenReturn(equipped);
        Mockito.when(item.getName()).thenReturn("name");
        Mockito.when(player.getBp()).thenReturn(bp);
        Mockito.when(bp.getBackpack()).thenReturn(List.of(item, secItem));
        Mockito.when(secItem.getName()).thenReturn("no");
        Mockito.when(equipped.eqAddItems(secItem)).thenReturn(true);

        action.execute("eq", "no");
        Mockito.verify(equipped, Mockito.times(1)).eqAddItems(secItem);
        Mockito.verify(bp, Mockito.times(1)).bpRemoveItems(secItem);
    }

    @Test
    void executeWithWrong() {
        assertThrows(CommandNotFoundException.class, () -> action.execute("eq"));
    }

    @Test
    void canExecute() {
        assertTrue(action.canHandle("Eq"));
        assertTrue(action.canHandle("EQuip"));
        assertFalse(action.canHandle("x"));
    }
}