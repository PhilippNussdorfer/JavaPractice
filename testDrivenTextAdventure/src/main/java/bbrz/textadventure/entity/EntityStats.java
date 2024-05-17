package bbrz.textadventure.entity;

import bbrz.textadventure.item.Backpack;
import bbrz.textadventure.item.Equipped;
import bbrz.textadventure.tools.OutputWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EntityStats {
    private final int hp;
    private final int dmg;
    private final int armor;
    private final Backpack bp;
    private final Equipped eq;
    private final AttackCalc attackCalc;
    private final OutputWrapper wrapper;
}
