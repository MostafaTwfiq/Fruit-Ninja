package GameTypes;

import GameObjects.FallingObjects.HalfFruits.WaterMelonHalf;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.RedFruitJuiceExplosion;
import GameSystem.FruitButtons.BackToMainButton;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import Sword.Sword;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;

public final class GamePlayScoreBoard extends Pane {

    private Stage stage;

    private ImageView background;

    private Sword sword;

    private int currentScore;
    private int bestScore;

    private Label currentScoreLabel;
    private Label bestScoreLabel;

    private BackToMainButton backToMainButton;

    private int sceneHeight;
    private int sceneWidth;

    private boolean isMuted;

    public GamePlayScoreBoard(Stage stage, int sceneHeight, int sceneWidth,
                              int currentScore, int bestScore, boolean isMuted) {

        this.stage = stage;
        this.sceneHeight = sceneHeight;
        this.sceneWidth = sceneWidth;
        this.currentScore = currentScore;
        this.bestScore = bestScore;
        this.isMuted = isMuted;

        currentScoreLabel = setUpLabel("Round score: " + String.format("%d", currentScore));
        currentScoreLabel.setLayoutX(10);
        currentScoreLabel.setLayoutY(10);

        bestScoreLabel = setUpLabel("Best score: " + String.format("%d", bestScore));
        bestScoreLabel.setLayoutX(10);
        bestScoreLabel.setLayoutY(150);

        setUpBackToMainButton();

        setPrefHeight(sceneHeight);
        setPrefWidth(sceneWidth);
        getChildren().addAll(currentScoreLabel, bestScoreLabel, backToMainButton);
        setPadding(new Insets(10, 10, 10, 10));
        setUpBackground();
    }

    private void setUpBackground() {
        try {
            FileInputStream backgroundPath = new FileInputStream("resources/systemImages/woodBackground.png");
            Image backgroundImage = new Image(backgroundPath);
            BackgroundImage background = new BackgroundImage(backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            setBackground(new Background(background));
        } catch (Exception e) {
            System.out.println("There ");
        }
    }

    private void setUpBackToMainButton() {
        backToMainButton = new BackToMainButton();
        backToMainButton.setLayoutX(sceneWidth - 210);
        backToMainButton.setLayoutY(300);
        backToMainButton.getFruitImageView().setOnMouseDragEntered(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.splatterMedium1).playSound();

            getChildren().remove(backToMainButton);

            RedFruitJuiceExplosion explosion = new RedFruitJuiceExplosion(200, 200);
            explosion.setLayoutX(sceneWidth - 210);
            explosion.setLayoutY(300);

            WaterMelonHalf waterMelonHalf1 = new WaterMelonHalf();
            waterMelonHalf1.setLayoutX(sceneWidth - 210);
            waterMelonHalf1.setLayoutY(400);

            WaterMelonHalf waterMelonHalf2 = new WaterMelonHalf();
            waterMelonHalf2.setLayoutX(sceneWidth - 110);
            waterMelonHalf2.setLayoutY(370);
            waterMelonHalf2.setRotate(180);

            getChildren().addAll(explosion, waterMelonHalf1, waterMelonHalf2);

            Timeline sleep = new Timeline(new KeyFrame(Duration.millis(50), n -> {
                this.stage.close();
            }));
            sleep.play();

        });
    }

    private Label setUpLabel(String text) {
        Label label = new Label();

        label.setText(text);

        label.setStyle(getLabelStyle());

        return label;
    }

    private String getLabelStyle() {
        String textColor = (currentScore != bestScore) ? ("white") : ("gold");
        return "-fx-text-fill: " + textColor + "; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 50; ";
    }
}
