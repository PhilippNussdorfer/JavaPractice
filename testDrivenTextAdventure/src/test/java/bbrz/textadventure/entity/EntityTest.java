package bbrz.textadventure.entity;

import bbrz.textadventure.Game;
import bbrz.textadventure.OutputWrapper;
import bbrz.textadventure.customException.NoFreeSpaceException;
import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EntityTest {

    Entity entity;

    @Mock
    Item item;
    @Mock
    Item secItem;

    @Mock
    OutputWrapper wrapper;
    @Mock
    Game game;
    @Mock
    Backpack backpack;
    @Mock
    Equipped equipped;
    @Mock
    AttackCalc attackCalc;

    @BeforeEach
    void setUp() {
        entity = new Player("Bob", 10, 10, 5, wrapper, attackCalc, backpack, equipped);
    }

    @Test
    void BPAddMultipleItems() throws NoFreeSpaceException {
        entity.bpAdd(item, secItem);
        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item, secItem);
    }

    @Test
    void addOneItemToEquipped() {
        entity.addEquipment(item);

        Mockito.verify(equipped, Mockito.times(1)).EQAddItems(item);
    }

    @Test
    void removeSingleItemFromEquipped() {
        entity.addEquipment(item);
        entity.dropEquipment(game, item);

        Mockito.verify(equipped, Mockito.times(1)).EQAddItems(item);
        Mockito.verify(equipped, Mockito.times(1)).EQRemoveItems(game, item);

    }

    @Test
    void removeMultipleItemsFromEquipped() {
        entity.addEquipment(item, secItem);
        entity.dropEquipment(game, item, secItem);

        Mockito.verify(equipped, Mockito.times(1)).EQAddItems(item, secItem);
        Mockito.verify(equipped, Mockito.times(1)).EQRemoveItems(game, item, secItem);
    }

    @Test
    void BPAddOneItem() throws NoFreeSpaceException {
        entity.bpAdd(item);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item);
    }

    @Test
    void BPTryRemoveFromEmptyItems() {
        entity.bpDrop(item);

        Mockito.verify(backpack, Mockito.times(1)).bpRemoveItems(item);
    }

    @Test
    void BPRemoveMultipleItems() throws NoFreeSpaceException {
        entity.bpAdd(item, secItem);
        entity.bpDrop(item, secItem);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(item, secItem);
        Mockito.verify(backpack, Mockito.times(1)).bpRemoveItems(item, secItem);
    }

    @Test
    void BPRemoveItem() throws NoFreeSpaceException {
        entity.bpAdd(secItem, item);
        entity.bpDrop(secItem);

        Mockito.verify(backpack, Mockito.times(1)).bpAddItems(secItem, item);
        Mockito.verify(backpack, Mockito.times(1)).bpRemoveItems(secItem);
    }

    @Test
    void getsAttacked() {
        entity.attacked(10);

        Mockito.verify(attackCalc, Mockito.times(1)).getsAttacked(10, 10);
    }

    @Test
    void getsAttackedWhereDamageShouldBeOne() {
        entity.attacked(1);

        Mockito.verify(attackCalc, Mockito.times(1)).getsAttacked(1, 10);
    }

    @Test
    void getDmgRole() {
        var role = entity.roleDmg();

        Mockito.verify(attackCalc, Mockito.times(1)).getDmgRole(5);
    }
}