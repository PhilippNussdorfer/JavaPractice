package bbrz.adventure.game.Inventory;

import bbrz.adventure.game.Components.PlayerComponent;
import bbrz.adventure.game.EntityType;
import bbrz.adventure.game.Items.Item;
import bbrz.adventure.game.Items.ItemDesignation;
import bbrz.adventure.game.MamblsAdventure;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InventoryView extends Region {

    private final int windowHeight;
    private final int windowWidth;
    private final List<Node> nodeList = new ArrayList<>();
    protected final GridPane grid;
    private final int invSize;
    private final ListenersForTheInventoryView listeners;
    @Getter
    private Node inventoryNode;
    private List<Item> inventory = new ArrayList<>();
    private final int columnsOfInventory = 4;

    public InventoryView(int height, int width, int invSize) {
        this.windowHeight = height;
        this.windowWidth = width;
        this.invSize = invSize;
        this.listeners = new ListenersForTheInventoryView(width, height);

        this.grid = getGridNew();
        initInv();
        updateInv();

        listeners.enableDragAndSwitch(this);
    }

    private void setRoot(Node node) {
        getChildren().add(node);
    }

    public Node getRoot() {
        return getChildren().get(getChildren().size() - 1);
    }

    public List<Item> setInventory(List<Item> items) {
        for (int j = 0; j < items.size(); j++) {
            if (items.get(j).getDesignation() != ItemDesignation.NULL) {
                for (int i = 0; i < inventory.size(); i++) {
                    if (inventory.get(i).getDesignation() == ItemDesignation.NULL) {
                        inventory.set(i, items.get(j));
                        items.set(j, Item.createNullItem());
                        break;
                    }
                }
            }
        }
        for (Item item : items) {
            if (item.getDesignation() != ItemDesignation.NULL)
                return items;
        }
        return null;
    }

    public void setInventoryToList(List<Item> items) {
        inventory = items;
        updateInv();
    }

    public List<Item> getInventory() {
        updateInv();
        return this.inventory;
    }

    private void clearInventory() {
        this.inventory.clear();
    }

    public void addTakeAllButton(BorderPane borderPane) {
        Label lbl = new Label("Take All");
        lbl.setFont(MamblsAdventure.gameFont);

        BorderPane takeAllBtn = new BorderPane();
        takeAllBtn.setBackground(new Background(new BackgroundImage(FXGL.image("gui/button.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(windowWidth, 30, false, false, false, false))));
        takeAllBtn.setCenter(lbl);


        listeners.makePressable(this, takeAllBtn);
        listeners.makeHoverAble(takeAllBtn, FXGL.image("gui/buttonHover.png"), FXGL.image("gui/button.png"), 35);

        borderPane.setBottom(takeAllBtn);
    }

    public void takeAllItemsToPlayerInventory() {
        setupInventory();
        updateInv();
    }

    private void setupInventory() {
        var playerComponent = FXGL.getGameWorld().getSingleton(EntityType.PLAYER).getComponent(PlayerComponent.class);
        var playerInventoryView = playerComponent.getInventoryview();

        ClearInventoryOrSetInventoryIfNotNull(playerInventoryView);
        playerInventoryView.updateInv();
    }

    private void ClearInventoryOrSetInventoryIfNotNull(InventoryView playerInventoryView) {
        List<Item> itemList = playerInventoryView.setInventory(inventory);

        if (itemList == null) {
            clearInventory();
        } else {
            setInventoryToList(itemList);
        }
    }

    public List<List<Item>> getTwoDListOfInventory() {
        List<List<Item>> result = new ArrayList<>();
        List<Item> tmpItemList = new ArrayList<>();

        for (Item item : inventory) {
            if (columnsOfInventory - 1 == tmpItemList.size()) {
                tmpItemList.add(item);
                result.add(new ArrayList<>(tmpItemList));
                tmpItemList.clear();
            } else {
                tmpItemList.add(item);
            }
        }
        return result;
    }
    
    public List<Item> translateTwoDInventoryToInventoryList(List<List<Item>> twoDInventory) {
        List<Item> result = new ArrayList<>();

        for (List<Item> itemList : twoDInventory) {
            for (int j = 0; j < columnsOfInventory; j++) {
                result.add(itemList.get(j));
            }
        }
        return result;
    }

    private void initInv() {
        if (this.inventory.size() < invSize) {
            int saver = this.inventory.size();
            for (int i = 0; i < this.invSize - (saver); i++) {
                this.inventory.add(Item.createNullItem());
            }
        }
    }

    public void updateInv() {
        for (Node node : this.grid.getChildren()) {
            ((BorderPane) node).setCenter(invElement());
        }

        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getDesignation() != ItemDesignation.NULL) {
                BorderPane borderPane = (BorderPane) this.grid.getChildren().get(i);
                borderPane.setCenter(new ImageView(this.inventory.get(i).getImg()));
            }
        }
    }

    private Node invElement() {
        BorderPane pane = new BorderPane();
        BackgroundSize backgroundSize = new BackgroundSize(25, 25, false, false, false, false);

        pane.setPrefHeight(40);
        pane.setPrefWidth(40);
        pane.setBackground(new Background(new BackgroundImage(FXGL.getAssetLoader().loadImage("gui/slot.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize)));

        listeners.makeHoverAble(pane,
                FXGL.getAssetLoader().loadImage("gui/slotHover.png"),
                FXGL.getAssetLoader().loadImage("gui/slot.png"), backgroundSize);
        return pane;
    }

    private void initNodeList() {
        for (int i = 0; i < this.invSize; i++) {
            nodeList.add(invElement());
        }
    }

    private GridPane getGridNew() {

        GridPane grid = new GridPane();
        initNodeList();

        grid.setPadding(new Insets(20));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setPrefHeight(windowHeight - 20);
        grid.setPrefWidth(windowWidth - 20);
        grid.setAlignment(Pos.CENTER);

        initGrid(grid);
        return grid;
    }

    private void initGrid(GridPane grid) {
        int counter = 0;
        for (int row = 0; row < this.invSize/ columnsOfInventory; row ++) {
            for (int col = 0; col < columnsOfInventory; col ++) {
                if (counter <= this.invSize - 1) {
                    grid.add(nodeList.get(counter), col, row);
                    counter++;
                }
            }
        }
    }

    @NotNull
    private BorderPane getMainBody(Image bodyImage, BorderPane titleBar) {
        BorderPane pane = new BorderPane();

        pane.setPrefHeight(windowHeight);
        pane.setPrefWidth(windowWidth);
        pane.setTop(titleBar);
        pane.setBackground(new Background(new BackgroundImage(bodyImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(windowWidth, windowHeight, false, false, false, false))));
        pane.setCenter(this.grid);

        return pane;
    }

    @NotNull
    private BorderPane getTitleBar(Image headImage, String labelStr) {
        BorderPane titleBar = new BorderPane();
        Label label = new Label(labelStr);

        titleBar.setBackground(new Background(new BackgroundImage(headImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(windowWidth, 30, false, false, false, false))));
        titleBar.setCenter(label);

        label.setFont(MamblsAdventure.gameFont);
        listeners.makeDraggable(this, label);

        return titleBar;
    }

    private void constructWindowForInv(Image bodyImage, Image headImage, String labelStr) {
        BorderPane titleBar = getTitleBar(headImage, labelStr);
        BorderPane mainBody = getMainBody(bodyImage, titleBar);

        this.setRoot(mainBody);
        listeners.makeDraggable(this, titleBar);
        listeners.makeFocusable(this);
    }

    public void getInvNode(Image bodyImage, Image headImage, String labelStr) {
        Pane root = new Pane();
        constructWindowForInv(bodyImage, headImage, labelStr);
        root.getChildren().add(this);

        this.inventoryNode = root;
    }
}
