package GameTypes.PauseGameMenu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;

public final class PauseGameButton extends Pane {

    private ImageView buttonImage;
    private Rectangle clickableRec;

    public PauseGameButton(String imagePath) {
        loadImage(imagePath);

        setUpClickableRec();

        getChildren().addAll(buttonImage, clickableRec);

        setPadding(new Insets(0, 0, 5, 5));

        styleProperty().set(getButtonStyle());
    }

    private String getButtonStyle() {
        return "-fx-border-color: white; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 20; " +
                "-fx-background-color: #a05c2e; " +
                "-fx-background-radius: 20; ";
    }

    private void loadImage(String imagePath) {

        try {

            FileInputStream imagePathFile = new FileInputStream(imagePath);
            buttonImage = new ImageView(new Image(imagePathFile));

            buttonImage.setFitHeight(50);
            buttonImage.setFitWidth(50);

            buttonImage.setEffect(new DropShadow());

            buttonImage.setLayoutX(5);
            buttonImage.setLayoutY(5);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading image in PauseGameButton class.");
        }
    }

    public void setButtonImage(String imagePath) {

        try {

            FileInputStream imagePathFile = new FileInputStream(imagePath);
            buttonImage.setImage(new Image(imagePathFile));

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading image in PauseGameButton class.");
        }
    }

    private void setUpClickableRec() {
        clickableRec = new Rectangle(buttonImage.getFitHeight(), buttonImage.getFitWidth());

        clickableRec.setHeight(50);
        clickableRec.setWidth(50);

        clickableRec.setCursor(Cursor.HAND);

        clickableRec.setOpacity(0);

        clickableRec.setLayoutX(5);
        clickableRec.setLayoutY(5);

    }

    public void setButtonAction(EventHandler<MouseEvent> mouseEvent) {
        clickableRec.setOnMouseClicked(mouseEvent);
    }
}
