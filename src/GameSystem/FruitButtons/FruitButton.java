package GameSystem.FruitButtons;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.FileInputStream;

public abstract class FruitButton extends Pane {

    private ImageView ringImageView;

    private ImageView fruitImageView;

    public FruitButton(String ringImagePath, String fruitImagePath) {
        loadRingImage(ringImagePath);
        loadFruitImage(fruitImagePath);
        createRotationAnimation();

        setPrefHeight(200);
        setPrefWidth(200);
        getChildren().addAll(ringImageView, fruitImageView);
    }

    private void loadRingImage(String ringImagePath) {

        try {

            FileInputStream imagePath = new FileInputStream(ringImagePath);
            Image image = new Image(imagePath);
            ringImageView = new ImageView(image);
            ringImageView.setFitHeight(200);
            ringImageView.setFitWidth(200);

            ringImageView.setEffect(new DropShadow());

        } catch (Exception e) {
            System.out.println("There is something wrong happened while setting a fruit button.");
        }
    }

    private void loadFruitImage(String fruitImagePath) {

        try {

            FileInputStream imagePath = new FileInputStream(fruitImagePath);
            Image image = new Image(imagePath);
            fruitImageView = new ImageView(image);
            fruitImageView.setFitHeight(100);
            fruitImageView.setFitWidth(100);

            fruitImageView.setEffect(new DropShadow());

            fruitImageView.setLayoutX(50);
            fruitImageView.setLayoutY(50);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while setting a fruit button.");
        }
    }

    private void createRotationAnimation() {
        KeyValue ringRotationKeyValue = new KeyValue(ringImageView.rotateProperty(), 360);

        KeyValue fruitYRotationKeyValue = new KeyValue(fruitImageView.rotateProperty(), 360);

        Timeline rotationTimeLine = new Timeline(new KeyFrame(Duration.seconds(6),
                ringRotationKeyValue,
                fruitYRotationKeyValue));

        rotationTimeLine.setCycleCount(Timeline.INDEFINITE);
        rotationTimeLine.play();
    }

    public ImageView getFruitImageView() {
        return fruitImageView;
    }

    public void changeButtonSize(int newSize) {
        setPrefHeight(newSize);
        setPrefWidth(newSize);
        ringImageView.setFitHeight(newSize);
        ringImageView.setFitWidth(newSize);
        fruitImageView.setFitHeight(newSize / 2.0);
        fruitImageView.setFitWidth(newSize / 2.0);
        fruitImageView.setLayoutX(newSize / 4.0);
        fruitImageView.setLayoutY(newSize / 4.0);
    }

}
