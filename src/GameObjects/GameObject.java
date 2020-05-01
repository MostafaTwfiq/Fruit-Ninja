package GameObjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public abstract class GameObject extends ImageView {

    public GameObject(String path){
        setUpObjectImageView(path);
    }

    /** This method will upload a photo to this extended image view object*/
    private void setUpObjectImageView (String path){

        try {

            FileInputStream imagePath = new FileInputStream(path);
            Image objectImage = new Image(imagePath);
            setImage(objectImage);

        } catch (Exception e) {
            System.out.println("There is something wrong in GameObjects.GameObject class.");
        }
    }

    public double getXLocation() {
        return getLayoutX();
    }

    public double getYLocation() {
        return getLayoutY();
    }

    /** This method will update the XAxis point and the YAxis point of the object*/
    public void move(double xPoint, double yPoint) {
        setLayoutX(xPoint);
        setLayoutX(yPoint);
    }
}
