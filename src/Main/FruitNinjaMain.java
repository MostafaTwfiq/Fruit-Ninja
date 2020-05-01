package Main;

import GameObjects.FallingObjects.HalfFruits.*;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.RedFruitJuiceExplosion;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.YellowFruitJuiceExplosion;
import GameSystem.FruitButtons.ArcadeGameButton;
import GameSystem.FruitButtons.ClassicGameButton;
import GameSystem.FruitButtons.QuitButton;
import GameSystem.FruitButtons.ScoreBoardButton;
import GameSystem.ScoreBoard.ScoreBoard;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameTypes.ArcadeGame;
import GameTypes.ClassicGame;
import HandlingData.SaveData;
import Players.Player;
import Players.PlayersCareTaker;
import Sword.Sword;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import Sword.MousePoint;

import java.io.FileInputStream;

public final class FruitNinjaMain {

    private Object nextStage;

    private PlayersCareTaker playersCareTaker;
    private Player player;

    private Stage stage;

    private ClassicGameButton classicGB;
    private ArcadeGameButton arcadeGB;
    private ScoreBoardButton scoreBoardB;
    private QuitButton quitB;

    private ImageView background;

    private Pane layout;

    private int sceneHeight;
    private int sceneWidth;

    private Scene scene;

    private Sword sword;

    private Pane swordLayout;

    private boolean isMuted;

    private ImageView soundStatusImageView;

    public FruitNinjaMain(Player player, PlayersCareTaker playersCareTaker,
                          int sceneHeight, int sceneWidth) {
        stage = null;
        classicGB = null;
        arcadeGB = null;
        scoreBoardB = null;
        quitB = null;
        background = null;
        layout = null;
        scene = null;
        nextStage = null;
        sword = null;
        swordLayout = null;
        isMuted = false;
        this.playersCareTaker = playersCareTaker;
        this.player = player;
        this.sceneHeight = sceneHeight;
        this.sceneWidth = sceneWidth;
    }

    public FruitNinjaMain(Player player, PlayersCareTaker playersCareTaker) {
        stage = null;
        classicGB = null;
        arcadeGB = null;
        scoreBoardB = null;
        quitB = null;
        background = null;
        layout = null;
        scene = null;
        nextStage = null;
        isMuted = false;
        this.playersCareTaker = playersCareTaker;
        this.player = player;
        sceneHeight = 500;
        sceneWidth = 700;
    }

    public Object show() {
        setUpStage();

        setUpClassicGB();
        setUpClassicGBAction();

        setUpArcadeGB();
        setUpArcadeGBAction();

        setUpScoreBoardB();
        setUpScoreBoardBAction();

        setUpQuitB();
        setUpQuitBAction();

        swordLayout = new Pane();
        sword = new Sword(swordLayout, isMuted);

        setUpLayout();

        setUpSoundStatusImageView();

        setUpScene();

        stage.setScene(scene);
        stage.showAndWait();

        return nextStage;
    }

    private void setUpStage() {
        stage = new Stage();
        stage.setTitle("Fruit ninja");
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            playersCareTaker.addMemento(player.getData(), playersCareTaker.getMementoIndex(player.getName()));
            SaveData.createDataSaver(playersCareTaker).saveData();
            System.exit(0);
        });
    }

    private void setUpScene() {
        scene = new Scene(layout, sceneWidth, sceneHeight);
        scene.setOnDragDetected(e -> scene.startFullDrag());

        scene.setOnMouseDragged(e -> {
            sword.addPoint(new MousePoint(e.getX(), e.getY()));
            sword.drawSword();
        });

        scene.setOnMouseReleased(e -> sword.removeSword());
    }

    private void setUpClassicGB() {
        classicGB = new ClassicGameButton();
        classicGB.setLayoutX(500);
        classicGB.setLayoutY(30);
    }

    private void setUpArcadeGB() {
        arcadeGB = new ArcadeGameButton();
        arcadeGB.setLayoutX(400);
        arcadeGB.setLayoutY(200);
    }

    private void setUpScoreBoardB() {
        scoreBoardB = new ScoreBoardButton();
        scoreBoardB.setLayoutX(220);
        scoreBoardB.setLayoutY(290);
    }

    private void setUpQuitB() {
        quitB = new QuitButton();
        quitB.setLayoutX(10);
        quitB.setLayoutY(300);
    }

    private void setUpLayout() {
        layout = new Pane();
        setUpBackground();
        layout.getChildren().addAll(background, classicGB, arcadeGB, scoreBoardB, quitB, swordLayout);
    }

    private void setUpBackground() {

        try {

            FileInputStream backgroundPath = new FileInputStream("resources/systemImages/mainBackground.jpeg");
            Image image = new Image(backgroundPath);
            background = new ImageView(image);
            background.setFitHeight(sceneHeight);
            background.setFitWidth(sceneWidth);

        } catch (Exception e) {
            System.out.println("There is some thing wrong happened while loading main background.");
        }
    }

    private void setUpSoundStatusImageView() {
        soundStatusImageView = new ImageView();
        loadHighVolumeImage();

        soundStatusImageView.setFitHeight(50);
        soundStatusImageView.setFitWidth(50);

        soundStatusImageView.setLayoutX(5);
        soundStatusImageView.setLayoutY(5);

        Rectangle clickableRec = new Rectangle(soundStatusImageView.getFitWidth(), soundStatusImageView.getFitHeight());
        clickableRec.setFill(Color.TRANSPARENT);
        clickableRec.setOpacity(0);
        clickableRec.setCursor(Cursor.HAND);
        clickableRec.setLayoutX(soundStatusImageView.getLayoutX());
        clickableRec.setLayoutY(soundStatusImageView.getLayoutY());

        clickableRec.setOnMouseClicked(e -> {
            if (isMuted)
                loadHighVolumeImage();
            else
                loadLowVolumeImage();

            isMuted = !isMuted;
        });

        layout.getChildren().addAll(soundStatusImageView, clickableRec);
    }

    private void loadHighVolumeImage() {
        try {

            FileInputStream imagePath = new FileInputStream("resources/pauseMenu/highVolume.png");
            soundStatusImageView.setImage(new Image(imagePath));

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading image in FruitNinjaMain class.");
        }
    }

    private void loadLowVolumeImage() {
        try {

            FileInputStream imagePath = new FileInputStream("resources/pauseMenu/lowVolume.png");
            soundStatusImageView.setImage(new Image(imagePath));

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading image in FruitNinjaMain class.");
        }
    }

    public void setUpClassicGBAction() {
        classicGB.getFruitImageView().setOnMouseDragEntered(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.splatterMedium1).playSound();

            layout.getChildren().remove(classicGB);

            RedFruitJuiceExplosion explosion = new RedFruitJuiceExplosion(200, 200);
            explosion.setLayoutX(500);
            explosion.setLayoutY(30);

            WaterMelonHalf waterMelonHalf1 = new WaterMelonHalf();
            waterMelonHalf1.setLayoutX(500);
            waterMelonHalf1.setLayoutY(130);

            WaterMelonHalf waterMelonHalf2 = new WaterMelonHalf();
            waterMelonHalf2.setLayoutX(600);
            waterMelonHalf2.setLayoutY(100);
            waterMelonHalf2.setRotate(180);

            layout.getChildren().addAll(explosion, waterMelonHalf1, waterMelonHalf2);

            nextStage = new ClassicGame(player, isMuted);

            Timeline sleep = new Timeline(new KeyFrame(Duration.millis(50), n -> {
                this.stage.close();
            }));
            sleep.play();

        });
    }

       public void setUpArcadeGBAction(){
        arcadeGB.getFruitImageView().setOnMouseDragEntered(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.cleanSlice).playSound();

            layout.getChildren().remove(arcadeGB);

            YellowFruitJuiceExplosion explosion = new YellowFruitJuiceExplosion(200, 200);
            explosion.setLayoutX(400);
            explosion.setLayoutY(200);

            BananaHalf bananaHalf1= new BananaHalf();
            bananaHalf1.setLayoutX(400);
            bananaHalf1.setLayoutY(300);

            BananaHalf bananaHalf2= new BananaHalf();
            bananaHalf2.setLayoutX(500);
            bananaHalf2.setLayoutY(200);
            bananaHalf2.setRotate(270);

            layout.getChildren().addAll(explosion, bananaHalf1,bananaHalf2);

            nextStage = new ArcadeGame(player, isMuted);

            Timeline sleep = new Timeline(new KeyFrame(Duration.millis(50), n -> {
                this.stage.close();
            }));
            sleep.play();

        });
    }

       public void setUpScoreBoardBAction(){
        scoreBoardB.getFruitImageView().setOnMouseDragEntered(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.splatterSmall).playSound();

            layout.getChildren().remove(scoreBoardB);

            RedFruitJuiceExplosion explosion = new RedFruitJuiceExplosion(200,200);
            explosion.setLayoutX(220);
            explosion.setLayoutY(290);

            RedAppleHalf1 redAppleHalf1 = new RedAppleHalf1();
            redAppleHalf1.setLayoutX(220);
            redAppleHalf1.setLayoutY(390);
            redAppleHalf1.setRotate(45);

            RedAppleHalf2 redAppleHalf2 = new RedAppleHalf2();
            redAppleHalf2.setLayoutX(320);
            redAppleHalf2.setLayoutY(360);
            redAppleHalf2.setRotate(45);

            layout.getChildren().addAll(explosion, redAppleHalf1,redAppleHalf2);

            nextStage = new ScoreBoard(player, playersCareTaker, isMuted, sceneHeight, sceneWidth);

            Timeline sleep = new Timeline(new KeyFrame(Duration.millis(50), n -> {
                this.stage.close();
            }));
            sleep.play();

         });
    }

        public void setUpQuitBAction(){
        quitB.getFruitImageView().setOnMouseDragEntered(e -> {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.splatterMedium2).playSound();

            layout.getChildren().remove(quitB);

            YellowFruitJuiceExplosion explosion = new YellowFruitJuiceExplosion(200,200);
            explosion.setLayoutX(10);
            explosion.setLayoutY(300);

            KiwiHalf1 kiwiHalf1= new KiwiHalf1();
            kiwiHalf1.setLayoutX(10);
            kiwiHalf1.setLayoutY(400);
            kiwiHalf1.setRotate(90);

            KiwiHalf2 kiwiHalf2= new KiwiHalf2();
            kiwiHalf2.setLayoutX(110);
            kiwiHalf2.setLayoutY(370);
            kiwiHalf2.setRotate(90);

            layout.getChildren().addAll(explosion, kiwiHalf1,kiwiHalf2);

            Timeline sleep = new Timeline(new KeyFrame(Duration.millis(500), n -> {
                playersCareTaker.addMemento(player.getData(), playersCareTaker.getMementoIndex(player.getName()));
                SaveData.createDataSaver(playersCareTaker).saveData();
                System.exit(0);
            }));
            sleep.play();

        });
    }
}
