package bbrz.textadventure.tools;

import bbrz.textadventure.item.Item;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StringFormatting {

    public String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }

    public String getPrintableCollection(String[] strArr) {
        return String.join(", ", strArr);
    }

    public String getPrintableCollection(List<String> strArr) {
        return String.join(", ", strArr);
    }

    public String getPrintableStringFromItemList(List<Item> items) {
        return items.stream().map(Item::getName).collect(Collectors.joining(", "));
    }
}
