package bbrz.textadventure.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemTest {
    @Mock
    ItemStats stats;

    @Test
    void builderAndGetter() {
        Item item = Item.builder().name("candle").value(2).stats(stats).description("desc").type(ItemType.MISC).build();

        assertEquals("candle", item.getName());
        assertEquals(stats, item.getStats());
        assertEquals("desc", item.getDescription());
        assertEquals(2, item.getValue());
        assertEquals(ItemType.MISC, item.getType());
    }
}