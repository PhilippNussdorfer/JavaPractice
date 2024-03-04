package bbrz.textadventure.colors;

import lombok.Getter;

public enum TextColor {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    DARK_BROWN("\u001B[33m"),
    MAGENTA("\u001B[95m"),
    BLUE("\u001B[34m"),
    CYAN("\u001B[36m"),
    RESET("\u001B[0m");

    @Getter
    private final String value;
    TextColor(String value) {
        this.value = value;
    }
}
