package bbrz.adventure.game.Inventory;

import bbrz.adventure.game.Items.Item;
import bbrz.adventure.game.Singelton.SaveListenerDataInSingleton;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ListenersForTheInventoryView {
    private final double windowWidth;
    private final double windowHeight;
    private final SaveListenerDataInSingleton dataInstance = SaveListenerDataInSingleton.getInstance();

    public ListenersForTheInventoryView(double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void makeHoverAble(BorderPane node, Image hoverImage, Image noHoverImage, int heightOfHoverObject) {
        node.setOnMouseEntered(e -> node.setBackground(new Background(new BackgroundImage(hoverImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(windowWidth, heightOfHoverObject, false, false, false, false)))));

        node.setOnMouseExited(e -> node.setBackground(new Background(new BackgroundImage(noHoverImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(windowWidth, heightOfHoverObject, false, false, false, false)))));
    }

    public void makeHoverAble(BorderPane node, Image hoverImage, Image noHoverImage, BackgroundSize backgroundSize) {
        node.setOnMouseEntered(e -> node.setBackground(new Background(new BackgroundImage(hoverImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize))));

        node.setOnMouseExited(e -> node.setBackground(new Background(new BackgroundImage(noHoverImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize))));
    }

    public void makeFocusable(InventoryView view) {
        view.setOnMouseClicked(e -> view.toFront());
    }

    public void makeDraggable(InventoryView view, Node node) {

        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);

        node.setOnMousePressed(e -> {
            xOffset.set(e.getSceneX() - view.getLayoutX());
            yOffset.set(e.getSceneY() - view.getLayoutY());

            view.toFront();
        });

        node.setOnMouseDragged(e -> {
            double cornerXPos = e.getSceneX() - xOffset.get();
            double cornerYPos = e.getSceneY() - yOffset.get();

            double xScale = FXGL.getGameScene().getWidth() / FXGL.getAppWidth();
            double yScale = FXGL.getGameScene().getHeight() / FXGL.getAppHeight();

            cornerXPos /= xScale;
            cornerYPos /= yScale;

            setWindowBoundsY(view, cornerXPos, cornerYPos, FXGL.getAppHeight(), windowHeight);
            setWindowBoundsX(view, cornerYPos, cornerXPos, FXGL.getAppWidth(), windowWidth);
        });
    }

    private void setWindowBoundsX(InventoryView view, double firstCorner, double secCorner, double appWidth, double windowWidth) {
        if (secCorner < 0) {
            view.setLayoutX(0d);

        } else if (secCorner + windowWidth > appWidth) {
            view.setLayoutX(appWidth - windowWidth);

        } else if (appWidth >= secCorner + windowWidth && 0 <= firstCorner) {
            view.setLayoutX(secCorner);
        }
    }

    private void setWindowBoundsY(InventoryView view, double firstCorner, double secCorner, double appHeight, double windowHeight) {
        if (secCorner < 0) {
            view.setLayoutY(0d);

        } else if (secCorner + windowHeight > appHeight) {
            view.setLayoutY(appHeight - windowHeight);

        } else if (appHeight >= secCorner + windowHeight && 0 <= firstCorner) {
            view.setLayoutY(secCorner);
        }
    }

    public void makePressable(InventoryView view, BorderPane node) {
        node.setOnMousePressed(e -> view.takeAllItemsToPlayerInventory());
    }

    public void enableDragAndSwitch(InventoryView view) {
        List<Node> gridChild = view.grid.getChildren();

        for (Node node : gridChild) {
            BorderPane child = (BorderPane) node;

            child.setOnDragDetected(event -> {
                if (child.getCenter() != null) {
                    dataInstance.setSourceInvView(view);
                    dragBoardSetup(child);
                    event.consume();
                }
            });

            child.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.ANY));

            child.setOnDragDropped(event -> {
                event.acceptTransferModes(TransferMode.ANY);
                dataInstance.setTargetInvView(view);
                prepareAndSwitchInventoryElements(child);
                event.consume();
            });
        }
    }

    private void syncInventoryWithView() {
        var sourceView = dataInstance.getSourceInvView();
        List<List<Item>> sourceListTwoD = sourceView.getTwoDListOfInventory();

        if (sourceView == dataInstance.getTargetInvView()) {
            swapItems(sourceListTwoD, sourceListTwoD);
            sourceView.setInventoryToList(sourceView.translateTwoDInventoryToInventoryList(sourceListTwoD));
        } else {
            var targetView = dataInstance.getTargetInvView();
            List<List<Item>> targetListTwoD = targetView.getTwoDListOfInventory();

            swapItems(sourceListTwoD, targetListTwoD);

            sourceView.setInventoryToList(sourceView.translateTwoDInventoryToInventoryList(sourceListTwoD));
            targetView.setInventoryToList(targetView.translateTwoDInventoryToInventoryList(targetListTwoD));
        }
    }

    private void swapItems(List<List<Item>> sourceListTwoD, List<List<Item>> targetListTwoD) {

        int sourceRow = dataInstance.getSourceRow();
        int sourceCol = dataInstance.getSourceCol();
        int targetRow = dataInstance.getTargetRow();
        int targetCol = dataInstance.getTargetCol();

        if (isWithinBounds(sourceListTwoD, sourceRow, sourceCol) &&
                isWithinBounds(targetListTwoD, targetRow, targetCol)) {

            var source = sourceListTwoD.get(sourceRow).get(sourceCol);
            var target = targetListTwoD.get(targetRow).get(targetCol);

            sourceListTwoD.get(sourceRow).set(sourceCol, target);
            targetListTwoD.get(targetRow).set(targetCol, source);
        } else {
            System.out.println("OutOfBounds");
        }
    }

    private boolean isWithinBounds(List<List<Item>> twoDList, int row, int col) {
        return row >= 0 && row < twoDList.size() && col >= 0 && col < twoDList.get(0).size();
    }

    private void dragBoardSetup(BorderPane child) {
        Node centerImgView = child.getCenter();
        if (centerImgView instanceof ImageView itemImgView) {
            Dragboard dragboard = itemImgView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();
            Image itemImg = itemImgView.getImage();

            clipboardContent.putImage(itemImg);
            dragboard.setContent(clipboardContent);

            dataInstance.setSourceCol(GridPane.getColumnIndex(child));
            dataInstance.setSourceRow(GridPane.getRowIndex(child));
        }
    }

    private void prepareAndSwitchInventoryElements(BorderPane child) {
        dataInstance.setTargetCol(GridPane.getColumnIndex(child));
        dataInstance.setTargetRow(GridPane.getRowIndex(child));

        if (isSourceAndTargetTheSame())
            return;

        syncInventoryWithView();
        this.enableDragAndSwitch(dataInstance.getSourceInvView());
        this.enableDragAndSwitch(dataInstance.getTargetInvView());
    }

    private boolean isSourceAndTargetTheSame() {
        return dataInstance.getSourceInvView() == dataInstance.getTargetInvView() &&
                (dataInstance.getSourceCol() == dataInstance.getTargetCol() &&
                        dataInstance.getSourceRow() == dataInstance.getTargetRow());
    }
}