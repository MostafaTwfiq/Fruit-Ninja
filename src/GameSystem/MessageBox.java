package GameSystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;


public final class MessageBox {

    private Stage stage;

    private Label messageLabel;

    private Button okB;

    private HBox okBLayout;
    private HBox labelLayout;
    private VBox parentLayout;

    private Scene scene;

    public final void messageBox(String title, String message){
        //Stage:
        stage = setUpPrimaryStage(title);

        //Message label:
        messageLabel = setUpMessageLabel(message);

        //Buttons:
        okB = setUpOkB();

        //Button layout:
        okBLayout = setUpOkBLayout();

        //Label layout:
        labelLayout = setUpLabelLayout();

        //Parent layout:
        parentLayout = setUpParentLayout();

        //Scene:
        scene = new Scene(parentLayout, 300,80);

        //Show stage:
        stage.setScene(scene);
        stage.showAndWait();
    }

    private Stage setUpPrimaryStage(String title){
        Stage primaryStage = new Stage();
        primaryStage.setTitle(title);
        primaryStage.setResizable(false);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        return primaryStage;
    }

    private Label setUpMessageLabel(String message){
        Label messageLabel = new Label();
        messageLabel.setFont(new Font("Cambria", 15));
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: white; ");
        return messageLabel;
    }

    private Button setUpOkB(){
        Button okB= new Button();
        okB.setText("Ok");

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.BLACK);
        Glow glow = new Glow();
        glow.setLevel(0.2);
        okB.setEffect(innerShadow);
        okB.setOnMouseEntered(e -> okB.setEffect(glow));
        okB.setOnMouseExited(e -> okB.setEffect(innerShadow));

        okB.setCursor(Cursor.HAND);
        okB.setPrefHeight(40);
        okB.setPrefWidth(40);
        okB.setStyle(setUpOkBStyle());
        okB.setOnAction(e -> stage.close());
        return okB;
    }

    private String setUpOkBStyle(){
        return "-fx-background-color: #8b4f2a; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 12; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 18;" +
                "-fx-border-radius: 18;" +
                "-fx-border-color: white; " +
                "-fx-border-width: 0.3px; ";
    }

    private HBox setUpOkBLayout(){
        HBox okBLayout = new HBox();
        okBLayout.getChildren().add(okB);
        okBLayout.setAlignment(Pos.CENTER);
        return okBLayout;
    }

    private HBox setUpLabelLayout(){
        HBox labelLayout = new HBox();

        labelLayout.getChildren().addAll(messageLabel);
        labelLayout.setAlignment(Pos.CENTER);
        return labelLayout;
    }

    private VBox setUpParentLayout(){
        VBox parentLayout = new VBox(10);

        BackgroundImage background = new BackgroundImage(getBackgroundImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        parentLayout.setBackground(new Background(background));

        parentLayout.setPadding(new Insets(10,10,10,10));
        parentLayout.setAlignment(Pos.CENTER);
        parentLayout.getChildren().addAll(messageLabel, okBLayout);
        return parentLayout;
    }

    private Image getBackgroundImage() {

        try {

            FileInputStream imagePath = new FileInputStream("resources/systemImages/woodBackground.png");
            return new Image(imagePath);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading GamePlay background.");
        }

        return null;
    }
}



















