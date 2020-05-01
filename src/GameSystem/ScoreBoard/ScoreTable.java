package GameSystem.ScoreBoard;

import Players.PlayerMemento;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public final class ScoreTable extends ScrollPane {

    private VBox containerLayout;

    public ScoreTable(int maxHeight, double tableHeight, double tableWidth) {
        setUpContainerLayout(tableWidth);

        setPrefHeight(tableHeight);
        setPrefWidth(tableWidth);
        setMaxHeight(maxHeight);
        setContent(containerLayout);

        containerLayout.prefWidthProperty().bind(widthProperty());
        containerLayout.prefHeightProperty().bind(heightProperty());
    }

    private void setUpContainerLayout(double tableWidth) {
        containerLayout = new VBox(10);
        containerLayout.setPadding(new Insets(15, 25, 15, 15));
        containerLayout.setAlignment(Pos.TOP_LEFT);
        containerLayout.styleProperty().set("-fx-background-color: #7c2406; ");
    }

    public void add(PlayerMemento playerMemento) {
        containerLayout.getChildren().add(new ScoreTableItem(playerMemento));
    }

    public void remove(PlayerMemento playerMemento) {
        for (int i = 0; i < containerLayout.getChildren().size(); i++) {
            ScoreTableItem tableItem = (ScoreTableItem) containerLayout.getChildren().get(i);

            if (tableItem.getPlayerMemento().equals(playerMemento)) {
                containerLayout.getChildren().remove(tableItem);
                break;
            }

        }
    }
}
