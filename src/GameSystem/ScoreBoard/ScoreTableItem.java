package GameSystem.ScoreBoard;

import Players.PlayerMemento;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.util.Random;

public final class ScoreTableItem extends HBox {

    private PlayerMemento playerMemento;

    private Label nameLabel;
    private Label playerNameLabel;
    private Label classicScoreLabel;
    private Label playerClassicScoreLabel;
    private Label arcadeScoreLabel;
    private Label playerArcadeScoreLabel;

    private GridPane labelPane;

    private ImageView playerImageView;

    public ScoreTableItem(PlayerMemento playerMemento) {
        this.playerMemento = playerMemento;

        nameLabel = setUpLabel("Player name: ");
        playerNameLabel = setUpLabel(playerMemento.getPlayerName());
        classicScoreLabel = setUpLabel("Player classic score: ");
        playerClassicScoreLabel = setUpLabel(String.format("%d", playerMemento.getClassicBestScore()));
        arcadeScoreLabel = setUpLabel("Player arcade score: ");
        playerArcadeScoreLabel = setUpLabel(String.format("%d", playerMemento.getArcadeBestScore()));

        setUpImageView();
        setUpLabelsPane();

        setUpHBox();
    }

    private void setUpHBox() {
        setSpacing(20);
        setPrefHeight(100);
        setFillHeight(true);
        setPadding(new Insets(10, 10, 10, 10));
        setAlignment(Pos.CENTER_LEFT);

        styleProperty().set(getHBoxStyle());
        setEffect(new DropShadow());

        getChildren().addAll(playerImageView, labelPane);
    }

    private String getHBoxStyle() {
        return "-fx-border-color: black; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 20; " +
                "-fx-background-color: #b0644a; " +
                "-fx-background-radius: 20; ";
    }

    private void setUpImageView() {
        try {

            FileInputStream imagePath = new FileInputStream("resources/charactersImages/image"
                    + String.format("%d", 1 + new Random().nextInt(5)) + ".png");

            Image image = new Image(imagePath);

            playerImageView = new ImageView(image);
            playerImageView.setFitHeight(60);
            playerImageView.setFitWidth(60);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while adding an item to the table view.");
        }
    }

    private Label setUpLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font("Cambria", 17));
        label.setStyle("-fx-font-weight: bold; ");
        return label;
    }

    private void setUpLabelsPane() {
        labelPane = new GridPane();
        labelPane.setHgap(10);
        labelPane.setVgap(10);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(playerNameLabel, 1, 0);
        GridPane.setConstraints(classicScoreLabel, 0, 1);
        GridPane.setConstraints(playerClassicScoreLabel, 1, 1);
        GridPane.setConstraints(arcadeScoreLabel, 0, 2);
        GridPane.setConstraints(playerArcadeScoreLabel, 1, 2);

        labelPane.getChildren().addAll(nameLabel, playerNameLabel,
                classicScoreLabel, playerClassicScoreLabel,
                arcadeScoreLabel, playerArcadeScoreLabel);
    }

    protected PlayerMemento getPlayerMemento() {
        return playerMemento;
    }
}
