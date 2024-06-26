package bbrz.textadventure.tools;

import bbrz.textadventure.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class StringFormatter {

    public String formatStringLength(int length, String strToFormat) {
        return String.format("%1$-" + length + "s", strToFormat);
    }

    public String getPrintableCollection(String[] strArr) {
        return String.join(", ", strArr);
    }

    public String getPrintableCollection(List<String> strList) {
        return String.join(", ", strList);
    }

    public String getPrintableStringFromItemList(List<Item> items) {
        return items.stream().map(Item::getName).collect(Collectors.joining(", "));
    }
}
