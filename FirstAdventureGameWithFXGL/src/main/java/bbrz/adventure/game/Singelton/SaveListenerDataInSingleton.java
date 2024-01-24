package bbrz.adventure.game.Singelton;

import bbrz.adventure.game.Inventory.InventoryView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveListenerDataInSingleton {

    private static SaveListenerDataInSingleton instance;

    private int sourceCol;
    private int sourceRow;
    private InventoryView sourceInvView;

    private int targetCol;
    private int targetRow;
    private InventoryView targetInvView;

    public static SaveListenerDataInSingleton getInstance() {
        if (instance == null) {
            instance = new SaveListenerDataInSingleton();
        }
        return instance;
    }
}
