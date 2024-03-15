package bbrz.textadventure.item;

import bbrz.textadventure.Game;
import bbrz.textadventure.tools.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.entity.Player;
import bbrz.textadventure.locatins.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EquippedTest {

    Equipped equipped;

    @Mock
    OutputWrapper wrapper;
    @Mock
    Game game;
    @Mock
    Location location;
    @Mock
    Player player;
    @Mock
    Backpack backpack;
    @Mock
    List<Item> list;

    @Mock
    Item item;
    @Mock
    Item secItem;
    @Mock
    Item thirdItem;
    @Mock
    Item item4;
    @Mock
    Item item5;
    @Mock
    Item item6;
    @Mock
    Item item7;
    @Mock
    Item item8;
    @Mock
    Item item9;

    @BeforeEach
    void setUp() {
        equipped = new Equipped(wrapper);
    }

    @Test
    void eqAddItems() {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        equipped.eqAddItems(item);

        assertTrue(equipped.getEquipped().contains(item));
        assertEquals(1, equipped.getEquipped().size());
    }

    @Test
    void equipMultipleItems() {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(secItem.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.ARTIFACT);
        equipped.eqAddItems(secItem);
        equipped.eqAddItems(thirdItem);
        equipped.eqAddItems(item);

        assertEquals(3, equipped.getEquipped().size());
        assertTrue(equipped.getEquipped().contains(item));
        assertTrue(equipped.getEquipped().contains(secItem));
        assertTrue(equipped.getEquipped().contains(thirdItem));
    }

    @Test
    void tryToEquipMultipleOfSameType() {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        equipped.eqAddItems(item);
        equipped.eqAddItems(item);

        assertTrue(equipped.getEquipped().contains(item));
        assertEquals(1, equipped.getEquipped().size());
        Mockito.verify(wrapper, Mockito.times(1)).outErr("The equipped item slots are already full or such an item is already equipped!");
    }

    @Test
    void tryToEquipMoreThanTwoArtifacts() {
        Mockito.when(item.getType()).thenReturn(ItemType.ARTIFACT);
        equipped.eqAddItems(item);
        equipped.eqAddItems(item);
        equipped.eqAddItems(item);

        assertTrue(equipped.getEquipped().contains(item));
        assertEquals(2, equipped.getEquipped().size());
        Mockito.verify(wrapper, Mockito.times(1)).outErr("The equipped item slots are already full or such an item is already equipped!");
    }

    @Test
    void equippedIsFull() {
        Mockito.when(item.getType()).thenReturn(ItemType.WEAPON);
        Mockito.when(secItem.getType()).thenReturn(ItemType.SHIELD);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.BOOTS);
        Mockito.when(item4.getType()).thenReturn(ItemType.BREASTPLATE);
        Mockito.when(item5.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(item6.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(item7.getType()).thenReturn(ItemType.GAUNTLETS);
        Mockito.when(item8.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(item9.getType()).thenReturn(ItemType.ARMORED_PANTS);

        equipped.eqAddItems(item);
        equipped.eqAddItems(secItem);
        equipped.eqAddItems(thirdItem);
        equipped.eqAddItems(item4);
        equipped.eqAddItems(item5);
        equipped.eqAddItems(item6);
        equipped.eqAddItems(item7);
        equipped.eqAddItems(item8);
        equipped.eqAddItems(item9);

        Mockito.verify(wrapper, Mockito.times(1)).outErr("The equipped item slots are already full or such an item is already equipped!");
    }

    @Test
    void tryToEquipAnItemThatIsNotEquipAble() {
        Mockito.when(item.getType()).thenReturn(ItemType.CONSUMABLE);
        equipped.eqAddItems(item);

        Mockito.verify(wrapper, Mockito.times(1)).outErr("This item is not equip able: null because it is an: CONSUMABLE.");
    }

    @Test
    void eqRemoveItems() throws NoFreeSpaceException {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getBp()).thenReturn(backpack);
        Mockito.when(backpack.getBACKPACK_SPACE()).thenReturn(12);
        Mockito.when(backpack.getBackpack()).thenReturn(list);
        Mockito.when(list.size()).thenReturn(0);

        equipped.eqAddItems(item);
        assertEquals(1, equipped.getEquipped().size());

        equipped.eqRemoveItems(game, item);
        assertEquals(0, equipped.getEquipped().size());
        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item);
    }

    @Test
    void removeItemAndFillBackpack() throws NoFreeSpaceException {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getBp()).thenReturn(backpack);
        Mockito.when(backpack.getBACKPACK_SPACE()).thenReturn(12);
        Mockito.when(backpack.getBackpack()).thenReturn(list);
        Mockito.when(list.size()).thenReturn(11);

        equipped.eqAddItems(item);
        equipped.eqRemoveItems(game, item);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item);
    }

    @Test
    void removeEquipmentWhenBackpackIsFull() throws NoFreeSpaceException {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(game.getPlayer()).thenReturn(player);
        Mockito.when(player.getBp()).thenReturn(backpack);
        Mockito.when(backpack.getBACKPACK_SPACE()).thenReturn(12);
        Mockito.when(backpack.getBackpack()).thenReturn(list);
        Mockito.when(list.size()).thenReturn(12);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);

        equipped.eqAddItems(item);
        equipped.eqRemoveItems(game, item);
        Mockito.verify(location, Mockito.times(1)).addItems(item);
    }
}