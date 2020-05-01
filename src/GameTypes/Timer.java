package GameTypes;

import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public final class Timer extends HBox {

    private Timeline timeCounter;
    private Timeline timeTicker;

    private int totalTime;

    private Label currentTimeLabel;

    private boolean isMuted;

    public Timer(int totalTime, boolean isMuted) {
        this.totalTime = totalTime;

        this.isMuted = isMuted;

        setUpCurrentTimeLabel(totalTime);

        setUpTimeCounter();

        getChildren().add(currentTimeLabel);
        setAlignment(Pos.CENTER);
    }

    private void setUpCurrentTimeLabel(int totalTime) {
        currentTimeLabel = new Label();

        currentTimeLabel.setText(String.format("%d", totalTime));

        currentTimeLabel.setStyle("-fx-text-fill: white; "+
                "-fx-font-weight: bold; "+
                "-fx-font-size: 45; ");
    }

    private void setUpTimeCounter() {

        KeyFrame tikKeyFrame = new KeyFrame(Duration.seconds(1), e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.timeTick).playSound();
        });
        KeyFrame tokKeyFrame = new KeyFrame(Duration.seconds(2), e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.timeTick).playSound();
        });
        timeTicker = new Timeline(tikKeyFrame, tokKeyFrame);
        timeTicker.setCycleCount(Timeline.INDEFINITE);

        timeCounter = new Timeline(new KeyFrame(Duration.seconds(1), e-> {
            int currentTime = Integer.parseInt(currentTimeLabel.getText()) - 1;
            currentTimeLabel.setText(String.format("%d", currentTime));
        }));
        timeCounter.setCycleCount(totalTime);
        timeCounter.setOnFinished(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.timeUp).playSound();

            timeTicker.stop();
        });
    }

    public void startTimer() {
        resetTimer();
        timeTicker.setCycleCount(totalTime);
        timeCounter.play();
        timeTicker.play();
    }

    public void resumeCounter() {
        timeCounter.setCycleCount(Integer.parseInt(currentTimeLabel.getText()));
        timeCounter.playFromStart();
        timeTicker.playFromStart();
    }

    public void stopCounter() {
        timeCounter.stop();
        timeTicker.stop();
    }

    private void resetTimer() {
        currentTimeLabel.setText(String.format("%d", totalTime));
        timeCounter.stop();
        timeTicker.stop();
    }

    public void decreaseTime(int decreasedValue) {
        timeCounter.stop();
        if (Integer.parseInt(currentTimeLabel.getText()) - decreasedValue <= 0) {
            timeCounter.stop();
            timeTicker.stop();
            return;
        }

        int newTime = Integer.parseInt(currentTimeLabel.getText()) - decreasedValue;
        currentTimeLabel.setText(String.format("%d", newTime));
        timeCounter.setCycleCount(newTime);
        timeCounter.playFromStart();
    }

    public boolean isTimeUp() {
        return timeCounter.getStatus() == Animation.Status.STOPPED;
    }

    public void setCountingSpeedRate(double rate) {
        timeCounter.setRate(rate);
        timeTicker.setRate(rate);
    }

    public int getRemainingTime() {
        return Integer.parseInt(currentTimeLabel.getText());
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

}
