package GameObjects.FallingObjects;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public final class ProjectionTimeLine {

    private FallingObjects fallingObject;
    private FallingRandomData fallingRandomData;

    private Timeline movingTimeLine;
    private Timeline rotationTimeLine;

    private double timeScalingF = 135;

    public ProjectionTimeLine(FallingObjects fallingObject,
                              FallingRandomData fallingRandomData) {

        this.fallingObject = fallingObject;
        this.fallingRandomData = fallingRandomData;

        movingTimeLine = null;
        rotationTimeLine = null;

        fallingObject.setTranslateX(fallingRandomData.getXStartPoint());
        fallingObject.setTranslateY(fallingRandomData.getSceneHeight());
    }

    public void createProjectionTimeLine() {

        if (movingTimeLine != null && rotationTimeLine != null)
            return;

        Interpolator curveInterpolator = new Interpolator() {
            @Override
            protected double curve(double t) {
                return -4 * ( t - 0.5 ) * ( t - 0.5 ) + 1;
            }
        };

        KeyValue rotationKayValue = new KeyValue(fallingObject.rotateProperty(), 360);

        KeyFrame rotationKeyFrame = new KeyFrame(Duration.millis(fallingRandomData.getTotalTime() * timeScalingF), rotationKayValue);

        KeyValue xKeyValue = new KeyValue(fallingObject.translateXProperty(),
                fallingRandomData.getXStartPoint() + getProjectionRange());

        KeyFrame xKeyFrame = new KeyFrame(Duration.millis(fallingRandomData.getTotalTime() * timeScalingF), xKeyValue);

        KeyValue yKeyValue = new KeyValue(fallingObject.translateYProperty(),
                fallingRandomData.getSceneHeight() - fallingRandomData.getObjMaxHeight(), curveInterpolator);

        KeyFrame yKeyFrame = new KeyFrame(Duration.millis(fallingRandomData.getTotalTime() * timeScalingF), yKeyValue);

        movingTimeLine = new Timeline(xKeyFrame, yKeyFrame);

        rotationTimeLine = new Timeline(rotationKeyFrame);
        rotationTimeLine.setCycleCount(1);
        rotationTimeLine.setOnFinished(e -> {
            fallingObject.setRotate(0);
            rotationTimeLine.play();
        });

    }

    private double getProjectionRange() {
        return fallingRandomData.isRightCurved()
                ? fallingRandomData.getProjectionRange()
                : -fallingRandomData.getProjectionRange();
    }

    public FallingRandomData getFallingRandomData() {
        return fallingRandomData;
    }

    public double getTimeScalingF() {
        return timeScalingF;
    }

    public Timeline getMovingTimeLine() {
        return movingTimeLine;
    }

    public Timeline getRotationTimeLine() {
        return rotationTimeLine;
    }
}
