package bbrz.textadventure.item;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Item {
    private String name;
    private String description;
    private ItemType type;
    private int value;
    private ItemStats stats;
}
