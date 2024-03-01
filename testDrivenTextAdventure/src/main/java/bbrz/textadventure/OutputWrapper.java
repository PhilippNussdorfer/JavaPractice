package bbrz.textadventure;

import bbrz.textadventure.colors.TextColor;

public class OutputWrapper {

    public void outPrintln(String msg) {
        System.out.println(msg);
    }

    public void outPrint(String msg) {
        System.out.print(msg);
    }

    public void outErr(String msg) {
        System.out.println(TextColor.RED.getValue() + msg + TextColor.RESET.getValue());
    }

    public void outPrintColored(String msg, TextColor color) {
        System.out.print(color.getValue() + msg + TextColor.RESET.getValue());
    }

    public void outPrintlnColored(String msg, TextColor color) {
        System.out.println(color.getValue() + msg + TextColor.RESET.getValue());
    }
}
