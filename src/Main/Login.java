package Main;

import GameSystem.MessageBox;
import Players.Player;
import Players.PlayersCareTaker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

public  class Login {

    private Label playerNameLabel;
    private TextField playerNameField;

    private Stage stage ;
    private GridPane parentLayout;
    private Scene scene;

    private Button addPlayerB;
    private Button okB;

    private PlayersCareTaker playersCareTaker;
    private Player player;


    public Login(PlayersCareTaker playersCareTaker) {
        this.playersCareTaker=playersCareTaker;

        player = new Player();
    }

    public Player show() {
        playerNameLabel = setUpLabel("Enter player name: ");

        playerNameField = setUpTextField("Enter player name.");

        addPlayerB = setUpButton("Add new player");
        setUpAddPlayerBAction();

        okB = setUpButton("Login");
        setUpOkBAction();

        setUpLayout();

        setUpScene();

        setUpStage();

        stage.setScene(scene);
        stage.showAndWait();

        return player;
    }

    public void setUpStage() {
        stage = new Stage();

        stage.setResizable(false);
        stage.setTitle("Login");

        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setScene(scene);
    }

    private void setUpScene()
    {
        scene= new Scene(parentLayout,300,100);
    }

    private Button setUpButton(String text) {
        Button button = new Button();
        button.setText(text);
        button.setPrefHeight(30);
        button.setPrefWidth(150);

        button.setCursor(Cursor.HAND);

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.BLACK);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        button.setEffect(innerShadow);
        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(innerShadow));

        button.setStyle(getButtonStyle());

        return button;
    }

    private String getButtonStyle() {
        return "-fx-background-color: #8b4f2a; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 18; " +
                "-fx-border-radius: 18;" +
                "-fx-border-color: white; " +
                "-fx-border-width: 0.3px; ";
    }

    private Label setUpLabel(String text) {
        Label label = new Label();

        label.setText(text);

        label.setStyle("-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 15; ");

        return label;
    }

    private TextField setUpTextField(String promptText) {
        TextField textField = new TextField();

        textField.setPromptText(promptText);

        textField.setPrefWidth(100);

        textField.setStyle(getTextFieldsStyle());

        return textField;
    }

    private String getTextFieldsStyle(){
        return "-fx-background-color: white; " +
                "-fx-background-radius: 18; " +
                "-fx-border-radius: 18; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 0.3px; ";
    }

    private void setUpLayout() {
        parentLayout = new GridPane();

        parentLayout.setPadding(new Insets(5, 5, 5, 5));
        parentLayout.setAlignment(Pos.CENTER_LEFT);

        parentLayout.setHgap(10);
        parentLayout.setVgap(10);

        GridPane.setConstraints(playerNameLabel, 0, 0);
        GridPane.setConstraints(playerNameField, 1, 0);
        GridPane.setConstraints(addPlayerB, 0, 1);
        GridPane.setConstraints(okB, 1, 1);

        BackgroundImage background = new BackgroundImage(getBackgroundImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        parentLayout.setBackground(new Background(background));

        parentLayout.getChildren().addAll(playerNameLabel, playerNameField, addPlayerB, okB);

    }

    private Image getBackgroundImage() {

        try {

            FileInputStream imagePath = new FileInputStream("resources/systemImages/woodBackground.png");
            return new Image(imagePath);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading GamePlay background.");
        }

        return null;
    }

    private void setUpOkBAction() {
        okB.setOnAction(e -> {
            String playerName = playerNameField.getText();
            if (isValidPlayerName(playerName)) {

                int playerMementoIndex = playersCareTaker.getMementoIndex(playerName);

                if (playerMementoIndex == -1)
                    new MessageBox().messageBox("Alert", "Player name isn't exist.");

                else {
                    player.setData(playersCareTaker.getMemento(playerMementoIndex));
                    stage.close();
                }

            }

            else
                new MessageBox().messageBox("Alert", "Please enter a valid player name.");
        });
    }

    private void setUpAddPlayerBAction() {
        addPlayerB.setOnAction(e->{
            String playerName = playerNameField.getText();

            if (isValidPlayerName(playerName)) {

                int playerMementoIndex = playersCareTaker.getMementoIndex(playerName);

                if (playerMementoIndex != -1)
                    new MessageBox().messageBox("Alert", "Player name is already exist.");

                else {
                    player.setName(playerName);
                    player.setClassicBestScore(0);
                    player.setArcadeBestScore(0);
                    player.setClassicGameMemento(null);
                    player.setArcadeGameMemento(null);
                    playersCareTaker.addMemento(player.getData());

                    stage.close();
                }

            }
            else
                new MessageBox().messageBox("Alert", "Please enter a valid player name.");
        });
    }

    private boolean isValidPlayerName(String input) {
        if (input == null)
            return false;

        else if (input.equals(""))
            return false;

        return true;
    }

}
