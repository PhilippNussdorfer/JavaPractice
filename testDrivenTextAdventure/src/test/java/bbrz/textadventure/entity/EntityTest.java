package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Item;
import bbrz.textadventure.item.ItemType;
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
class EntityTest {

    Entity entity;

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

    @Mock
    OutputWrapper wrapper;
    @Mock
    Game game;
    @Mock
    Location location;

    @BeforeEach
    void setUp() {
        entity = new Player("Bob", 10, 10, 5, wrapper);
    }

    @Test
    void BPAddMultipleItems() throws NoFreeSpaceException {
        entity.BPAddItems(item, secItem);

        assertTrue(entity.getBackpack().containsAll(List.of(item, secItem)));
        assertEquals(2, entity.getBackpack().size());
    }

    @Test
    void addOneItemToEquipped() {
        Mockito.when(item.getType()).thenReturn(ItemType.SHIELD);
        entity.EQAddItems(item);

        assertTrue(entity.getEquipped().contains(item));
        assertEquals(1, entity.getEquipped().size());
    }

    @Test
    void removeSingleItemFromEquipped() {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        entity.EQAddItems(item);
        entity.EQRemoveItems(game, item);

        assertTrue(entity.getEquipped().isEmpty());
        assertEquals(1, entity.getBackpack().size());
        assertEquals(item, entity.getBackpack().get(0));
    }

    @Test
    void removeMultipleItemsFromEquipped() {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(secItem.getType()).thenReturn(ItemType.SHIELD);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.WEAPON);
        entity.EQAddItems(item, secItem, thirdItem);
        entity.EQRemoveItems(game, item, thirdItem);

        assertEquals(1, entity.getEquipped().size());
        assertEquals(2, entity.getBackpack().size());
        assertTrue(entity.getBackpack().contains(item));
        assertTrue(entity.getBackpack().contains(thirdItem));
    }

    @Test
    void removeItemsFromEquippedWheBackpackIsFull() throws NoFreeSpaceException {
        Mockito.when(item.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(game.getCurrentLocation()).thenReturn(location);

        entity.BPAddItems(item, item, item, item, item, item, item, item, item, item, item, item);
        entity.EQAddItems(item);
        entity.EQRemoveItems(game, item);

        assertTrue(entity.getEquipped().isEmpty());
        Mockito.verify(location, Mockito.times(1)).addItems(item);
    }

    @Test
    void tryToAddToFullEquippedList() {
        Mockito.when(item.getType()).thenReturn(ItemType.SHIELD);
        Mockito.when(secItem.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(item4.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(item5.getType()).thenReturn(ItemType.WEAPON);
        Mockito.when(item6.getType()).thenReturn(ItemType.ARMORED_PANTS);
        Mockito.when(item7.getType()).thenReturn(ItemType.GAUNTLETS);
        Mockito.when(item8.getType()).thenReturn(ItemType.BOOTS);
        Mockito.when(item9.getType()).thenReturn(ItemType.BREASTPLATE);
        entity.EQAddItems(item, secItem, thirdItem, item4, item5, item6, item7, item8, item9);

        assertTrue(entity.getEquipped().contains(item));
        assertTrue(entity.getEquipped().contains(secItem));
        assertTrue(entity.getEquipped().contains(thirdItem));
        assertEquals(8, entity.getEquipped().size());

        Mockito.verify(wrapper, Mockito.times(1)).outErr("The equipped item slots are already full or such an item is already equipped!");
    }

    @Test
    void addMultipleItemsToEquipped() {
        Mockito.when(item.getType()).thenReturn(ItemType.SHIELD);
        Mockito.when(secItem.getType()).thenReturn(ItemType.HELMET);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.ARTIFACT);
        entity.EQAddItems(item, secItem, thirdItem);

        assertTrue(entity.getEquipped().contains(item));
        assertTrue(entity.getEquipped().contains(secItem));
        assertTrue(entity.getEquipped().contains(thirdItem));
        assertEquals(3, entity.getEquipped().size());
    }

    @Test
    void AddMultipleWithOneDoubleType() {
        Mockito.when(item.getType()).thenReturn(ItemType.SHIELD);
        Mockito.when(secItem.getType()).thenReturn(ItemType.SHIELD);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.ARTIFACT);
        entity.EQAddItems(item, secItem, thirdItem);

        assertTrue(entity.getEquipped().contains(item));
        assertTrue(entity.getEquipped().contains(thirdItem));
        assertEquals(2, entity.getEquipped().size());
        Mockito.verify(wrapper, Mockito.times(1)).outErr("The equipped item slots are already full or such an item is already equipped!");
    }

    @Test
    void AddMultipleArtifacts() {
        Mockito.when(item.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(secItem.getType()).thenReturn(ItemType.ARTIFACT);
        Mockito.when(thirdItem.getType()).thenReturn(ItemType.ARTIFACT);
        entity.EQAddItems(item, secItem, thirdItem);

        assertTrue(entity.getEquipped().contains(item));
        assertTrue(entity.getEquipped().contains(secItem));
        assertEquals(2, entity.getEquipped().size());
        Mockito.verify(wrapper, Mockito.times(1)).outErr("The equipped item slots are already full or such an item is already equipped!");
    }

    @Test
    void tryToAddNotEquipAbleItem() {
        Mockito.when(item.getType()).thenReturn(ItemType.CONSUMABLE);
        Mockito.when(item.getName()).thenReturn("name");
        entity.EQAddItems(item);

        assertFalse(entity.getEquipped().contains(item));
        assertEquals(0, entity.getEquipped().size());
        Mockito.verify(wrapper, Mockito.times(1)).outErr("This item is not equip able: name");
    }

    @Test
    void BPAddOneItem() throws NoFreeSpaceException {
        entity.BPAddItems(item);

        assertTrue(entity.getBackpack().contains(item));
        assertEquals(1, entity.getBackpack().size());
    }

    @Test
    void BPAddedToMatch() {
        assertThrows(NoFreeSpaceException.class, ()-> entity.BPAddItems(item, item, item, item, item, item, item, item, item, item, item, item, item));
    }

    @Test
    void BPTryRemoveFromEmptyItems() {
        entity.BPRemoveItems(item);
        Mockito.verify(wrapper, Mockito.times(1)).outErr("Could not find the item: null");
    }

    @Test
    void BPRemoveMultipleItems() throws NoFreeSpaceException {
        entity.BPAddItems(item, secItem);
        entity.BPRemoveItems(item, secItem);

        assertEquals(0, entity.getBackpack().size());
    }

    @Test
    void BPRemoveItem() throws NoFreeSpaceException {
        entity.BPAddItems(secItem, item);
        entity.BPRemoveItems(secItem);

        assertEquals(1, entity.getBackpack().size());
    }

    @Test
    void getsAttacked() {
        entity.getsAttacked(10);

        assertEquals(1, entity.getHp());
    }

    @Test
    void getsAttackedWhereDamageShouldBeOne() {
        entity.getsAttacked(1);

        assertEquals(9, entity.getHp());
    }

    @Test
    void getDmgRole() {
        for (int i = 0; i < 10; i++) {
            var role = entity.getDmgRole();

            System.out.println(role);
            assertTrue(List.of(5, 4, 3).contains(role));
        }
    }
}