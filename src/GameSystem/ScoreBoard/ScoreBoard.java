package GameSystem.ScoreBoard;

import GameObjects.FallingObjects.HalfFruits.WaterMelonHalf;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.RedFruitJuiceExplosion;
import GameSystem.FruitButtons.BackToMainButton;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import HandlingData.SaveData;
import Players.Player;
import Players.PlayerMemento;
import Players.PlayersCareTaker;
import Sword.Sword;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Sword.MousePoint;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Comparator;

public final class ScoreBoard {

    private ScoreTable scoreTable;

    private PlayersCareTaker playersCareTaker;

    private Player player;

    private HBox scoreTableLayout;

    private Pane parentLayout;

    private ImageView background;

    private BackToMainButton backToMainB;

    private Sword sword;

    private Pane swordLayout;

    private Scene scene;
    private int sceneHeight;
    private int sceneWidth;

    private Stage stage;

    private boolean isMuted;

    public ScoreBoard(Player player, PlayersCareTaker playersCareTaker, boolean isMuted) {

        this.player = player;
        this.playersCareTaker = playersCareTaker;
        this.isMuted = isMuted;

        sceneHeight = 500;
        sceneWidth = 700;
    }

    public ScoreBoard(Player player, PlayersCareTaker playersCareTaker,
                      boolean isMuted, int sceneHeight, int sceneWidth) {

        this.player = player;
        this.playersCareTaker = playersCareTaker;
        this.isMuted = isMuted;

        this.sceneHeight = sceneHeight;
        this.sceneWidth = sceneWidth;
    }

    public void show() {
        setUpStage();

        swordLayout = new Pane();
        sword = new Sword(swordLayout, isMuted);

        scoreTable = new ScoreTable(340, 340, 680);
        setUpScoreTableLayout();
        addToTable();

        setUpBackToMainB();

        setUpParentLayout();

        setUpScene();

        stage.setScene(scene);
        stage.showAndWait();
    }

    private void setUpStage() {
        stage = new Stage();
        stage.setTitle("Score board");
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            playersCareTaker.addMemento(player.getData(), playersCareTaker.getMementoIndex(player.getName()));
            SaveData.createDataSaver(playersCareTaker).saveData();
            System.exit(0);
        });
    }

    private void setUpScene() {
        scene = new Scene(parentLayout, sceneWidth, sceneHeight);

        scene.setOnDragDetected(e -> scene.startFullDrag());

        scene.setOnMouseDragged(e -> {
            sword.addPoint(new MousePoint(e.getX(), e.getY()));
            sword.drawSword();
        });

        scene.setOnMouseReleased(e -> sword.removeSword());
    }

    private void setUpBackToMainB() {
        backToMainB = new BackToMainButton();
        backToMainB.setLayoutX(sceneWidth - 150);
        backToMainB.setLayoutY(350);

        backToMainB.changeButtonSize(150);

        backToMainB.getFruitImageView().setOnMouseDragEntered(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.splatterMedium1).playSound();

            parentLayout.getChildren().remove(backToMainB);

            RedFruitJuiceExplosion explosion = new RedFruitJuiceExplosion(200, 200);
            explosion.setLayoutX(sceneWidth - 150);
            explosion.setLayoutY(350);

            WaterMelonHalf waterMelonHalf1 = new WaterMelonHalf();
            waterMelonHalf1.setFitHeight(50);
            waterMelonHalf1.setFitWidth(50);
            waterMelonHalf1.setLayoutX(sceneWidth - 150);
            waterMelonHalf1.setLayoutY(450);

            WaterMelonHalf waterMelonHalf2 = new WaterMelonHalf();
            waterMelonHalf2.setFitHeight(50);
            waterMelonHalf2.setFitWidth(50);
            waterMelonHalf2.setLayoutX(sceneWidth - 50);
            waterMelonHalf2.setLayoutY(420);
            waterMelonHalf2.setRotate(180);

            parentLayout.getChildren().addAll(explosion, waterMelonHalf1, waterMelonHalf2);

            Timeline sleep = new Timeline(new KeyFrame(Duration.millis(50), n -> {
                this.stage.close();
            }));
            sleep.play();
        });
    }

    private void setUpScoreTableLayout() {
        scoreTableLayout = new HBox();

        scoreTableLayout.setAlignment(Pos.CENTER);

        scoreTableLayout.getChildren().add(scoreTable);

        scoreTableLayout.setLayoutX(10);
        scoreTableLayout.setLayoutY(10);
    }

    private void setUpParentLayout() {
        parentLayout = new Pane();
        parentLayout.setPadding(new Insets(10, 10, 10, 10));

        setUpBackground();

        parentLayout.getChildren().addAll(background, scoreTableLayout, backToMainB, swordLayout);
    }

    private void setUpBackground() {
        try {

            FileInputStream imagePath = new FileInputStream("resources/systemImages/woodBackground.png");
            background = new ImageView(new Image(imagePath));

            background.setFitHeight(sceneHeight);
            background.setFitWidth(sceneWidth);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading background in ScoreBoard class.");
        }
    }

    public PlayerMemento[] sortScore() {
        PlayerMemento[] playersMementos = playersCareTaker.mementosListToArray();

        Arrays.sort(playersMementos, new Comparator<PlayerMemento>() {

            @Override
            public int compare(PlayerMemento playerMemento1, PlayerMemento playerMemento2) {

                return playerMemento2.getClassicBestScore() - playerMemento1.getClassicBestScore();

            }

        });

        return playersMementos;
    }


    public void addToTable() {
        PlayerMemento[] playersMementos = sortScore();

        for(int i = 0; i < playersMementos.length; i++)
            scoreTable.add(playersMementos[i]);

    }
}
