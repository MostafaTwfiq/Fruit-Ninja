package GameTypes.PoppingImage;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;

public abstract class PoppingImage extends ImageView {

    Timeline changeSizeTimeLine;

    int finalHeight;
    int finalWidth;

    double changingSizeDurationTimeInMillis;
    double fadingDurationTimeInSeconds;

    public PoppingImage(String imagePath, int finalHeight, int finalWidth,
                        double changingSizeDurationTimeInMillis, double fadingDurationTimeInSeconds) {

        this.finalHeight = finalHeight;
        this.finalWidth = finalWidth;
        this.changingSizeDurationTimeInMillis = changingSizeDurationTimeInMillis;
        this.fadingDurationTimeInSeconds = fadingDurationTimeInSeconds;

        loadImage(imagePath);

        setUpChangeSizeTimeLine();
    }

    private void loadImage(String imagePath) {

        try {

            FileInputStream imagePathFile = new FileInputStream(imagePath);
            setImage(new Image(imagePathFile));

            setEffect(new DropShadow());

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading critical image.");
        }
    }

    private void setUpChangeSizeTimeLine() {
        KeyValue changingHeightKeyValue = new KeyValue(fitHeightProperty(), finalHeight);
        KeyValue changingWidthKeyValue = new KeyValue(fitWidthProperty(), finalWidth);
        KeyFrame changingSizeKeyFrame = new KeyFrame(Duration.millis(changingSizeDurationTimeInMillis), changingHeightKeyValue, changingWidthKeyValue);

        KeyValue fadingKeyValue = new KeyValue(opacityProperty(), 0);
        KeyFrame fadingKeyFrame = new KeyFrame(Duration.seconds(fadingDurationTimeInSeconds), fadingKeyValue);

        changeSizeTimeLine = new Timeline(changingSizeKeyFrame, fadingKeyFrame);
    }

    public void startChangingSize() {
        changeSizeTimeLine.play();
    }

    public Timeline getChangeSizeTimeLine() {
        return changeSizeTimeLine;
    }
}
