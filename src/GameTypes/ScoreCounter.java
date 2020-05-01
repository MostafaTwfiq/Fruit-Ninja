package GameTypes;

import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.FileInputStream;

public final class ScoreCounter extends VBox {
    private HBox scoreCounterLayout;

    private ImageView halfWaterMelonImageView;

    private Label scoreCounterLabel;

    private Label bestScoreLabel;

    private int bestScore;

    private boolean firstBreakOfBestScore = true;

    private boolean isMuted;

    public ScoreCounter(int bestScore, boolean isMuted){
        this.bestScore = bestScore;

        this.isMuted = isMuted;

        setUpImageView();
        setUpScoreCounterLabel();
        setUpBestScoreLabel();
        setUpScoreCounterLayout();

        getChildren().addAll(scoreCounterLayout, bestScoreLabel);

        setAlignment(Pos.CENTER_LEFT);
    }

    private void setUpScoreCounterLayout() {
        scoreCounterLayout = new HBox(10);

        scoreCounterLayout.setAlignment(Pos.CENTER_LEFT);

        scoreCounterLayout.getChildren().addAll(halfWaterMelonImageView, scoreCounterLabel);
    }

    private void setUpBestScoreLabel() {
        bestScoreLabel = new Label();
        bestScoreLabel.setText(String.format("Best score: %d", bestScore));

        bestScoreLabel.setStyle("-fx-text-fill: white; "+
                "-fx-font-weight: bold; "+
                "-fx-font-size: 20; "
        );

        bestScoreLabel.setEffect(new DropShadow());
    }

    private void setUpScoreCounterLabel() {
        scoreCounterLabel = new Label();
        scoreCounterLabel.setText("0");

        scoreCounterLabel.setStyle("-fx-text-fill: yellow; "+
                "-fx-font-weight: bold; "+
                "-fx-font-size: 45; "
        );

        scoreCounterLabel.setEffect(new DropShadow());
    }

    private void setUpImageView() {

        try {

            FileInputStream imagePath = new FileInputStream("resources/halfFruits/waterMelonHalf.png");
            halfWaterMelonImageView = new ImageView(new Image(imagePath));

            halfWaterMelonImageView.setFitHeight(50);
            halfWaterMelonImageView.setFitWidth(50);

            halfWaterMelonImageView.setEffect(new DropShadow());
            halfWaterMelonImageView.setRotate(220);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading images in ScoreCounter class.");
        }
    }

    public void increaseScore(int increasedValue) {
        scoreCounterLabel.setText(String.format("%d", (Integer.parseInt(scoreCounterLabel.getText())) + increasedValue));

        checkForNewBestScore();

    }

    private void checkForNewBestScore() {
        int scoreCounter = Integer.parseInt(scoreCounterLabel.getText());

        if (bestScore < scoreCounter)
            newBestScoreAction(scoreCounter);

    }

    private void newBestScoreAction(int newBestScore) {
        bestScore = newBestScore;

        bestScoreLabel.setText("Best score: " + String.format("%d", bestScore));

        if (firstBreakOfBestScore) {

            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.newBestScore).playSound();

            bestScoreLabel.setStyle("-fx-text-fill: green; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 20; "
            );

            newBestScoreAnimation();

            firstBreakOfBestScore = false;
        }

    }

    private void newBestScoreAnimation() {
        Timeline increaseSize = new Timeline(new KeyFrame(Duration.millis(100),
                new KeyValue(bestScoreLabel.styleProperty(), getBestScoreStyleProperty()),
                new KeyValue(bestScoreLabel.rotateProperty(), 20)));

        increaseSize.setCycleCount(2);
        increaseSize.setAutoReverse(true);

        increaseSize.play();
    }

    private String getBestScoreStyleProperty() {
        return "-fx-text-fill: green; "+
                "-fx-font-weight: bold; "+
                "-fx-font-size: 25; ";
    }

    public boolean thereIsNewBestScore() {
        return !firstBreakOfBestScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getCurrentScore() {
        return Integer.parseInt(scoreCounterLabel.getText());
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }
}
