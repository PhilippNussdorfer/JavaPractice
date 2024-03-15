package bbrz.textadventure.tools;

import bbrz.textadventure.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StringFormattingTest {

    StringFormatting formatter;
    @Mock
    Item item;
    @Mock
    Item secItem;

    @BeforeEach
    void setUp() {
        formatter = new StringFormatting();
    }

    @Test
    void formatStringLength() {
        var res = formatter.formatStringLength(10, "String");
        assertEquals(10, res.length());
    }

    @Test
    void getPrintableCollection() {
        var arr = formatter.getPrintableCollection(new String[] {"Hello", "World", "Nickel"});
        assertEquals("Hello, World, Nickel", arr);

        var list = formatter.getPrintableCollection(List.of("Hello", "New", "World"));
        assertEquals("Hello, New, World", list);
    }

    @Test
    void getPrintableStringFromItemList() {
        Mockito.when(item.getName()).thenReturn("Candle");
        Mockito.when(secItem.getName()).thenReturn("Matches");
        var res = formatter.getPrintableStringFromItemList(List.of(item, secItem));

        assertEquals("Candle, Matches", res);
    }
}