package bbrz.adventure.game.Components;

import lombok.Getter;

public enum EnemyIndicator {
    SKELETON("skeleton"),
    GOBLIN("goblin"),
    SPIDER("spider"),
    WOLF("wolf");

    @Getter
    private final String value;

    private EnemyIndicator(String value) {
        this.value = value;
    }
}
