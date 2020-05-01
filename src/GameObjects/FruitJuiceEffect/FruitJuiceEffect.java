package GameObjects.FruitJuiceEffect;

import GameObjects.GameObject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class FruitJuiceEffect extends GameObject {

    private Timeline fadingTimeline;
    private double fadingDurationTime;

    private boolean isFaded = false;

    public FruitJuiceEffect(String path, double fadingDurationTime) {
        super(path);
        this.fadingDurationTime = fadingDurationTime;
        setUpFadingTimeLine();
    }

    private void setUpFadingTimeLine() {
        KeyValue fadingKeyValue = new KeyValue(opacityProperty(), 0);

        fadingTimeline = new Timeline(new KeyFrame(Duration.seconds(fadingDurationTime), fadingKeyValue));
        fadingTimeline.setCycleCount(1);
        fadingTimeline.setOnFinished(e -> isFaded = true);
    }

    public void startFading(){
        fadingTimeline.play();
    }

    public void startFadingFrom(double durationTimeInMillis){
        fadingTimeline.playFrom(Duration.millis(durationTimeInMillis));
    }

    public boolean isFaded() {
        return isFaded;
    }

    public Timeline getFadingTimeline() {
        return fadingTimeline;
    }

}
