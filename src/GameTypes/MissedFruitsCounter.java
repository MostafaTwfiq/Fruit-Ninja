package GameTypes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.FileInputStream;

public final class MissedFruitsCounter extends HBox {

    private ImageView firstX;
    private ImageView secondX;
    private ImageView thirdX;

    private int missedFruitsCount;

    public MissedFruitsCounter() {
        firstX = setUpImageView(30, 30);
        secondX = setUpImageView(40, 40);
        thirdX = setUpImageView(50, 50);

        missedFruitsCount = 0;

        setSpacing(10);
        setPadding(new Insets(10, 10, 10, 10));
        setAlignment(Pos.CENTER);

        getChildren().addAll(firstX, secondX, thirdX);
    }

    private ImageView setUpImageView(int height, int width) {

        ImageView imageView = new ImageView(getBlueXImage());

        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        imageView.setEffect(new DropShadow());

        return imageView;
    }

    public void increaseMissed() {
        switch (missedFruitsCount) {
            case 0:
                firstX.setImage(getRedXImage());
                missedFruitsCount++;
                createVibrationEffect();
                break;

            case 1:
                secondX.setImage(getRedXImage());
                missedFruitsCount++;
                createVibrationEffect();
                break;

            case 2:
                thirdX.setImage(getRedXImage());
                missedFruitsCount++;
                createVibrationEffect();
                break;

        }

    }

    public void decreaseMissed() {
        switch (missedFruitsCount) {
            case 1:
                firstX.setImage(getBlueXImage());
                missedFruitsCount--;
                createVibrationEffect();
                break;

            case 2:
                secondX.setImage(getBlueXImage());
                missedFruitsCount--;
                createVibrationEffect();
                break;

            case 3:
                thirdX.setImage(getBlueXImage());
                missedFruitsCount--;
                createVibrationEffect();
                break;
        }

    }

    public boolean isThreeMissed() {
        return missedFruitsCount == 3;
    }

    private Image getRedXImage() {

        try {
            FileInputStream imagePath = new FileInputStream("resources/systemImages/redX.png");
            return new Image(imagePath);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading images in MissedFruitCounter class.");
        }

        return null;
    }

    private Image getBlueXImage() {
        try {

            FileInputStream imagePath = new FileInputStream("resources/systemImages/blueX.png");
            return new Image(imagePath);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading images in MissedFruitCounter class.");
        }

        return null;
    }

    private void createVibrationEffect() {
        Timeline rotate = new Timeline(new KeyFrame(Duration.millis(100),
                new KeyValue(rotateProperty(), 25)));

        rotate.setCycleCount(1);

        Timeline undoRotate = new Timeline(new KeyFrame(Duration.millis(100),
                new KeyValue(rotateProperty(), 0)));
        undoRotate.setCycleCount(1);

        rotate.setOnFinished(e -> undoRotate.play());

        rotate.play();
    }

    public int getMissedFruitsCount() {
        return missedFruitsCount;
    }

}
