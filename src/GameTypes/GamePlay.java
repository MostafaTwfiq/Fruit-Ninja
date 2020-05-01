package GameTypes;

import GameObjects.FallingObjects.Bombs.DangerousBomb;
import GameObjects.FallingObjects.Bombs.FatalBomb;
import GameObjects.FallingObjects.Bombs.TimeBomb;
import GameObjects.FallingObjects.Factories.BombFactory;
import GameObjects.FallingObjects.Factories.FruitFactory;
import GameObjects.FallingObjects.Factories.HalfFruitsFactory;
import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import GameObjects.FruitJuiceEffect.FruitJuiceEffect;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.BlueFruitJuiceExplosion;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.FruitJuiceExplosion;
import GameObjects.FruitJuiceEffect.FruitJuiceExplosions.FruitJuiceExplosionFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameSystem.SoundEffect.SoundEffectPlayer;
import GameTypes.PauseGameMenu.PauseGameMenu;
import GameTypes.PoppingImage.CriticalImage;
import HandlingData.SaveData;
import Players.Player;
import Players.PlayersCareTaker;
import Sword.Sword;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Sword.MousePoint;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Vector;

public abstract class GamePlay {

    protected Stage stage;
    protected Pane layout;
    protected ImageView background;
    private Pane swordLayout;
    private Scene scene;
    protected int sceneHeight;
    protected int sceneWidth;
    protected MousePoint mouseCurrentPoint;

    protected Player player;
    protected Vector<FallingObjects> objectsVector;
    protected Vector<FruitJuiceEffect> fruitJuiceVector;
    protected Timeline addingFruitsTimeline;
    private AnimationTimer updatingGameTimer;
    protected ScoreCounter scoreCounter;
    private Sword sword;
    protected Vector<SoundEffectPlayer> bombsFuseSounds;

    protected double addingObjectsRate;
    protected double objectsMovingRate;
    protected double objectsRotateRate;
    protected int fruitsRatio;
    protected int bombsRatio;
    protected int specialFruitsRatio;

    private NumberRange fruitNumbersRange;
    private NumberRange bombsNumbersRange;
    private NumberRange specialFruitsNumbersRange;

    protected int currentDifficultyStage;
    protected boolean isMuted;

    protected boolean gamePaused;
    protected double[] objectsCurrentDurationTime;
    protected PauseGameMenu pauseGameMenu;
    private ImageView pauseImageView;
    private Rectangle pauseClickableRec;
    protected Rectangle pauseRecLayout;
    protected Timeline pauseTimeLine;

    public GamePlay(Player player, boolean isMuted, String stageTitle,
                    String gameType, int specialFruitsRatio, NumberRange bombsRange) {

        this.player = player;
        this.isMuted = isMuted;
        sceneHeight = 500;
        sceneWidth = 700;
        swordLayout = new Pane();
        sword = new Sword(swordLayout, isMuted);
        setUpLayout();
        setUpScoreCounter(gameType);
        setUpScene();
        setUpStage(stageTitle);

        objectsVector = new Vector<>();
        bombsFuseSounds = new Vector<>();
        fruitJuiceVector = new Vector<>();
        setUpAddingFruitsTimeline();
        setUpUpdatingGameTimer();

        this.specialFruitsRatio = specialFruitsRatio;
        activateFirstDifficulty();

        fruitNumbersRange = new NumberRange(1, 6);
        specialFruitsNumbersRange = new NumberRange(7, 8);
        bombsNumbersRange = bombsRange;

        gamePaused = false;

        setUpPauseGameTools();
    }

    public void play() {
        if (!gamePaused) {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.gameStart).playSound();

            addingFruitsTimeline.play();
        }
        updatingGameTimer.start();

        stage.showAndWait();
    }

    private void setUpStage(String stageTitle) {
        stage = new Stage();
        stage.setTitle(stageTitle);
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            if (gamePaused)
                resumeGame();

            boolean thereIsNoGameToSave = false;
            for (int i = 0; i < layout.getChildren().size(); i++) {

                if (layout.getChildren().get(i) instanceof GamePlayScoreBoard)
                    thereIsNoGameToSave = true;

            }

            if (!thereIsNoGameToSave)
                saveGame();

            PlayersCareTaker playersCareTaker = SaveData.createDataSaver(null).getPlayersCareTaker();
            int index = playersCareTaker.getMementoIndex(player.getName());
            playersCareTaker.addMemento(player.getData(), index);

            SaveData saver = SaveData.createDataSaver(null);
            saver.saveData();
            System.exit(0);
        });
        stage.setScene(scene);
    }

    private void setUpScene() {
        scene = new Scene(layout, sceneWidth, sceneHeight);

        scene.setOnDragDetected(e -> scene.startFullDrag());

        scene.setOnMouseDragged(e -> {
            mouseCurrentPoint = new MousePoint(e.getX(), e.getY());
            sword.addPoint(mouseCurrentPoint);
            sword.drawSword();
        });

        scene.setOnMouseReleased(e -> sword.removeSword());
    }

    private void setUpScoreCounter(String gameType) {
        int playerGameTypeBestScore = gameType.equals("classic") ?
                player.getClassicBestScore() :
                player.getArcadeBestScore();

        scoreCounter = new ScoreCounter(playerGameTypeBestScore, isMuted);
        scoreCounter.setLayoutX(2);
        scoreCounter.setLayoutY(2);

        layout.getChildren().add(scoreCounter);
    }

    private void setUpPauseGameTools() {
        setUpPauseImage();
        setUpPauseClickableRec();
        setUpPauseRectangleLayout();
        setUpPauseTimeLine();
        setUpPauseGameMenu();

        layout.getChildren().addAll(pauseImageView, pauseClickableRec);
    }

    private void setUpPauseClickableRec() {
        pauseClickableRec = new Rectangle(pauseImageView.getFitWidth(), pauseImageView.getFitHeight());
        pauseClickableRec.setOpacity(0);
        pauseClickableRec.setCursor(Cursor.HAND);
        pauseClickableRec.setLayoutX(5);
        pauseClickableRec.setLayoutY(sceneHeight - pauseImageView.getFitHeight() - 5);

        setUpPauseClickableRecAction();
    }

    private void setUpPauseClickableRecAction() {
        pauseClickableRec.setOnMouseClicked(e -> {
            pauseGame();

            pauseGameMenu.setChoice("");
            layout.getChildren().addAll(pauseRecLayout, pauseGameMenu);

            pauseTimeLine.playFromStart();

        });
    }

    private void setUpPauseRectangleLayout() {
        pauseRecLayout = new Rectangle(sceneWidth, sceneHeight);
        pauseRecLayout.setOpacity(0.5);
    }

    private void setUpPauseGameMenu() {
        pauseGameMenu = new PauseGameMenu(isMuted);
        pauseGameMenu.setLayoutX( (sceneWidth / 2.0) - 150);
        pauseGameMenu.setLayoutY( (sceneHeight / 2.0) - 30);
    }

    private void setUpPauseImage() {
        try {

            FileInputStream imagePath = new FileInputStream("resources/pauseMenu/pause.png");
            pauseImageView = new ImageView(new Image(imagePath));
            pauseImageView.setFitHeight(30);
            pauseImageView.setFitWidth(30);

            pauseImageView.setEffect(new DropShadow());
            pauseImageView.setLayoutX(5);
            pauseImageView.setLayoutY(sceneHeight - pauseImageView.getFitHeight() - 5);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading pause image in GamePlay class.");
        }
    }

    private void setUpPauseTimeLine() {
        pauseTimeLine = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if (!pauseGameMenu.getChoice().equals("")) {
                isMuted = pauseGameMenu.isMuted();
                scoreCounter.setMuted(isMuted);
                sword.setMuted(isMuted);
                muteGameTypeExtraObjects();
                String choice = pauseGameMenu.getChoice();
                layout.getChildren().remove(pauseGameMenu);
                layout.getChildren().remove(pauseRecLayout);

                activePauseMenuChoice(choice);
                pauseTimeLine.stop();
            }

        }));
        pauseTimeLine.setCycleCount(Timeline.INDEFINITE);
    }

    private void setUpLayout() {
        layout = new Pane();

        background = new ImageView();
        setUpBackground();

        layout.getChildren().addAll(background, swordLayout);
    }

    protected void setUpBackground() {

        try {

            FileInputStream imagePath = new FileInputStream("resources/systemImages/woodBackground.png");
            background.setImage(new Image(imagePath));
            background.setFitHeight(sceneHeight);
            background.setFitWidth(sceneWidth);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading GamePlay background.");
        }
    }

    private void setUpUpdatingGameTimer() {
        updatingGameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateGameStatus();
            }
        };
    }

    private void setUpAddingFruitsTimeline() {
        KeyFrame addingObjectsKeyFrame = setUpAddingObjectsKeyFrame();
        addingFruitsTimeline = new Timeline(addingObjectsKeyFrame);

        addingFruitsTimeline.setCycleCount(Timeline.INDEFINITE);
        addingFruitsTimeline.setRate(addingObjectsRate);
    }

    private KeyFrame setUpAddingObjectsKeyFrame() {
        return new KeyFrame(Duration.millis(2000), e -> {
            addSpecificNumberOfObjects(generateRandomNumberOfNewObjects());
        });
    }

    private void addSpecificNumberOfObjects(int numberOfNewObject) {
        boolean fruitExist = false;
        boolean bombExist = false;
        for (int i = 0; i < numberOfNewObject; i++) {
            FallingObjects fallingObject = createRandomFallingObject();
            if (fallingObject == null)
                throw new RuntimeException();

            if (isBomb(fallingObject.getFallingObjectType())) {
                bombExist = true;
                playBombFuse(fallingObject);
            }
            else
                fruitExist = true;

            FallingRandomData objectRandomData = new FallingRandomData(sceneHeight, sceneWidth);
            ProjectionTimeLine objectProjectionTimeLine = new ProjectionTimeLine(fallingObject, objectRandomData);
            fallingObject.setProjectionTimeLine(objectProjectionTimeLine);

            layout.getChildren().add(fallingObject);
            objectsVector.add(fallingObject);
            fallingObject.getProjectionTimeLine().getMovingTimeLine().setRate(objectsMovingRate);
            fallingObject.getProjectionTimeLine().getRotationTimeLine().setRate(objectsRotateRate);
            fallingObject.startMovingTimeLine();
        }

        if (fruitExist && !isMuted)
            new SoundEffectFactory().createSoundObj(SoundEffectType.throwFruit).playSound();
        if (bombExist && !isMuted)
            new SoundEffectFactory().createSoundObj(SoundEffectType.throwBomb).playSound();

    }

    protected void playBombFuse(FallingObjects bomb) {
        if (bomb.getFallingObjectType() == FallingObjectType.dangerousBomb && !isMuted) {
            ((DangerousBomb) bomb).getFuseSoundPlayer().playSound();
            bombsFuseSounds.add( ((DangerousBomb) bomb).getFuseSoundPlayer() );
        }

        else if (bomb.getFallingObjectType() == FallingObjectType.timeBomb && !isMuted) {
            ((TimeBomb) bomb).getFuseSoundPlayer().playSound();
            bombsFuseSounds.add( ((TimeBomb) bomb).getFuseSoundPlayer() );
        }

        else if (bomb.getFallingObjectType() == FallingObjectType.fatalBomb && !isMuted) {
            ((FatalBomb) bomb).getFuseSoundPlayer().playSound();
            bombsFuseSounds.add( ((FatalBomb) bomb).getFuseSoundPlayer() );
        }

    }

    protected void stopBombFuse(FallingObjects bomb) {
        if (bomb.getFallingObjectType() == FallingObjectType.dangerousBomb) {
            SoundEffectPlayer bombFuse = ((DangerousBomb) bomb).getFuseSoundPlayer();
            bombFuse.pause();
            bombFuse.reset();
            bombsFuseSounds.remove(bombFuse);
        }

        else if (bomb.getFallingObjectType() == FallingObjectType.timeBomb) {
            SoundEffectPlayer bombFuse = ((TimeBomb) bomb).getFuseSoundPlayer();
            bombFuse.pause();
            bombFuse.reset();
            bombsFuseSounds.remove(bombFuse);
        }

        else if (bomb.getFallingObjectType() == FallingObjectType.fatalBomb) {
            SoundEffectPlayer bombFuse = ((FatalBomb) bomb).getFuseSoundPlayer();
            bombFuse.pause();
            bombFuse.reset();
            bombsFuseSounds.remove(bombFuse);
        }
    }

    private FallingObjects createRandomFallingObject() {
        int objectNumber = createRandomFallingObjectNumber();
        int fruitsFirstNumber = fruitNumbersRange.getFirstNumber();
        int fruitsLastNumber = fruitNumbersRange.getLastNumber();
        int bombsFirstNumber = bombsNumbersRange.getFirstNumber();
        int bombsLastNumber = bombsNumbersRange.getLastNumber();
        int specialFruitsFirstNumber = specialFruitsNumbersRange.getFirstNumber();
        int specialFruitsLastNumber = specialFruitsNumbersRange.getLastNumber();

        if (objectNumber >= fruitsFirstNumber && objectNumber <= fruitsLastNumber
                ||objectNumber >= specialFruitsFirstNumber && objectNumber <= specialFruitsLastNumber)
            return new FruitFactory().createFruit(objectNumber);

        else if (objectNumber >= bombsFirstNumber && objectNumber <= bombsLastNumber)
            return new BombFactory().createBomb(objectNumber);

        else
            return null;
    }

    private int createRandomFallingObjectNumber() {
        int fruitsFirstNumber = fruitNumbersRange.getFirstNumber();
        int fruitsLastNumber = fruitNumbersRange.getLastNumber();
        int bombsFirstNumber = bombsNumbersRange.getFirstNumber();
        int bombsLastNumber = bombsNumbersRange.getLastNumber();
        int specialFruitsFirstNumber = specialFruitsNumbersRange.getFirstNumber();
        int specialFruitsLastNumber = specialFruitsNumbersRange.getLastNumber();

        int fruitNumber = fruitsFirstNumber + new Random().nextInt(fruitsLastNumber - fruitsFirstNumber + 1);
        int bombNumber = bombsFirstNumber + new Random().nextInt(bombsLastNumber - bombsFirstNumber + 1);
        int specialFruitNumber = specialFruitsFirstNumber
                + new Random().nextInt(specialFruitsLastNumber - specialFruitsFirstNumber + 1);

        int sampleSpace = fruitsRatio + bombsRatio + specialFruitsRatio;
        int sampleSpaceRandomNumber = new Random().nextInt(sampleSpace);

        if (sampleSpaceRandomNumber >= 0 && sampleSpaceRandomNumber < fruitsRatio)
            return fruitNumber;
        else if(sampleSpaceRandomNumber >= fruitsRatio && sampleSpaceRandomNumber < bombsRatio + fruitsRatio)
            return bombNumber;
        else if (sampleSpaceRandomNumber >= fruitsRatio + bombsRatio && sampleSpaceRandomNumber < sampleSpace)
            return specialFruitNumber;
        else
            return -1;
    }

    private int generateRandomNumberOfNewObjects() {
        int oneObjectRatio = 4;
        int twoObjectsRatio = 3;
        int threeObjectsRatio = 2;
        int fourObjectsRatio = 2;
        int onePlusTwo = oneObjectRatio + twoObjectsRatio;
        int onePlusTwoPlusThree = oneObjectRatio + twoObjectsRatio + threeObjectsRatio;

        int sampleSpace = oneObjectRatio + twoObjectsRatio + threeObjectsRatio + fourObjectsRatio;
        int sampleSpaceRandomNumber = new Random().nextInt(sampleSpace + 1);

        if (sampleSpaceRandomNumber > 0 && sampleSpaceRandomNumber <= oneObjectRatio)
            return 1;
        else if(sampleSpaceRandomNumber > oneObjectRatio && sampleSpaceRandomNumber <= onePlusTwo)
            return 2;
        else if (sampleSpaceRandomNumber > onePlusTwo && sampleSpaceRandomNumber <= onePlusTwoPlusThree)
            return 3;
        else if (sampleSpaceRandomNumber > onePlusTwoPlusThree && sampleSpaceRandomNumber <= sampleSpace)
            return 4;
        else
            return -1;
    }

    private void updateGameStatus() {
        for (int i = 0; i < objectsVector.size(); i++) {
            FallingObjects object = objectsVector.get(i);

            if (isMissed(object)){
                missedObjectAction(object);
                continue;
            }

            object.setOnMouseDragEntered(e -> {
                if (object.isSliceAble())
                    activeSlicedObjectAction(object);
            });

            updateExplosionsStatus();
        }

    }

    private boolean isMissed(FallingObjects object) {
        return object.getProjectionTimeLine().getMovingTimeLine().getStatus() == Animation.Status.STOPPED && !gamePaused;
    }

    protected abstract void missedObjectAction(FallingObjects object);

    protected boolean isBomb(FallingObjectType objectType) {
        return objectType == FallingObjectType.dangerousBomb || objectType == FallingObjectType.fatalBomb
                || objectType == FallingObjectType.timeBomb;
    }

    protected boolean isCriticalSlice(FallingObjects fruit) {
        return mouseCurrentPoint.getXPoint() >= fruit.getTranslateX() + (fruit.getObjectSize() / 2.0) - 50
                && mouseCurrentPoint.getXPoint() <= fruit.getTranslateX() - (fruit.getObjectSize() / 2.0) + 50
                && mouseCurrentPoint.getYPoint() >= fruit.getTranslateY() + (fruit.getObjectSize() / 2.0) - 50
                && mouseCurrentPoint.getYPoint() <= fruit.getTranslateY() - (fruit.getObjectSize() / 2.0) + 50;
    }

    private void updateExplosionsStatus() {
        for (int i = 0; i < fruitJuiceVector.size(); i++) {
            FruitJuiceEffect fruitJuiceEffect = fruitJuiceVector.get(i);

            if (fruitJuiceEffect.isFaded()) {
                layout.getChildren().remove(fruitJuiceEffect);
                fruitJuiceVector.remove(fruitJuiceEffect);
            }

        }

    }

    protected abstract void activeSlicedObjectAction(FallingObjects object);

    protected void createFruitExplosion(FallingObjects fruit) {
            double fruitXPoint = fruit.getTranslateX();
            double fruitYPoint = fruit.getTranslateY();

            FruitJuiceExplosion explosion = new FruitJuiceExplosionFactory().createJuiceExplosion(fruit.getFallingObjectType());
            explosion.setLayoutX(fruitXPoint);
            explosion.setLayoutY(fruitYPoint);

            fruitJuiceVector.add(explosion);
            layout.getChildren().addAll(explosion);

            explosion.startFading();
    }

    protected void createCriticalText(FallingObjects fruit) {
        CriticalImage criticalImage = new CriticalImage();

        criticalImage.setLayoutX(fruit.getTranslateX());
        criticalImage.setLayoutY(fruit.getTranslateY());

        Timeline criticalTimeline = criticalImage.getChangeSizeTimeLine();
        criticalTimeline.setOnFinished(e -> layout.getChildren().remove(criticalImage));

        FruitJuiceEffect blueExplosion = new BlueFruitJuiceExplosion(120, 200);
        blueExplosion.setLayoutX(fruit.getTranslateX());
        blueExplosion.setLayoutY(fruit.getTranslateY());
        blueExplosion.setRotate(90);

        layout.getChildren().addAll(criticalImage, blueExplosion);
        fruitJuiceVector.add(blueExplosion);

        blueExplosion.startFading();
        criticalImage.startChangingSize();
    }

    protected void addHalfFruit(FallingObjects fruit, double halfRotationSpeedFactor, double halfMovingFactor) {
        FallingRandomData randomData = fruit.getProjectionTimeLine().getFallingRandomData();

        FallingRandomData firstHalfData = getFirstHalfRandomData(fruit, randomData);
        FallingRandomData secondHalfData = getSecondHalfRandomData(fruit, randomData);

        FallingObjects firstHalf = setUpFirstHalf(fruit, randomData, firstHalfData, halfRotationSpeedFactor, halfMovingFactor);

        FallingObjects secondHalf = setUpSecondHalf(fruit, randomData, secondHalfData, halfRotationSpeedFactor, halfMovingFactor);

        layout.getChildren().addAll(firstHalf, secondHalf);
        objectsVector.add(firstHalf);
        objectsVector.add(secondHalf);
    }

    private FallingObjects setUpFirstHalf(FallingObjects object, FallingRandomData objectsRandomData, FallingRandomData data,
                                          double halfRotationSpeedFactor, double halfMovingFactor) {

        HalfFruitsFactory halfFruitsFactory = new HalfFruitsFactory();
        FallingObjects firstHalf = halfFruitsFactory.createHalfFruit(halfFruitsFactory.getFruitHalfEnumNumber(object.getFallingObjectType(), 1));
        firstHalf.setProjectionTimeLine(new ProjectionTimeLine(firstHalf, data));

        Timeline halfMovingTimeLine = firstHalf.getProjectionTimeLine().getMovingTimeLine();
        Timeline halfRotationTimeLine = firstHalf.getProjectionTimeLine().getRotationTimeLine();

        double firstTimeAtY = objectsRandomData.getFirstTimeAtYPoint(sceneHeight - object.getTranslateY());
        double startingMoment = firstTimeAtY * firstHalf.getProjectionTimeLine().getTimeScalingF();

        halfMovingTimeLine.setRate(objectsMovingRate * 1.5 * halfMovingFactor);
        halfMovingTimeLine.playFrom(Duration.millis(startingMoment));

        firstHalf.setRotate(object.getRotate() * ( object.getRotate() > 180?-1:1 ) );
        halfRotationTimeLine.setRate(objectsRotateRate * 2.5 * halfRotationSpeedFactor);
        halfRotationTimeLine.play();

        return firstHalf;
    }

    private FallingObjects setUpSecondHalf(FallingObjects object, FallingRandomData objectsRandomData, FallingRandomData data,
                                           double halfRotationSpeedFactor, double halfMovingFactor) {

        HalfFruitsFactory halfFruitsFactory = new HalfFruitsFactory();
        FallingObjects secondHalf = halfFruitsFactory.createHalfFruit(halfFruitsFactory.getFruitHalfEnumNumber(object.getFallingObjectType(), 2));

        secondHalf.setProjectionTimeLine(new ProjectionTimeLine(secondHalf, data));

        Timeline halfMovingTimeLine = secondHalf.getProjectionTimeLine().getMovingTimeLine();
        Timeline halfRotationTimeLine = secondHalf.getProjectionTimeLine().getRotationTimeLine();

        double secondTimeAtY = objectsRandomData.getSecondTimeAtYPoint(sceneHeight - object.getTranslateY());
        double startingMoment = secondTimeAtY * object.getProjectionTimeLine().getTimeScalingF() + 100;

        halfMovingTimeLine.setRate(objectsMovingRate * 1.2 * halfMovingFactor);
        halfMovingTimeLine.playFrom(Duration.millis(startingMoment));

        secondHalf.setRotate( (object.getRotate() * ( object.getRotate() > 180?-1:1 )) + 180);
        halfRotationTimeLine.setRate(objectsRotateRate * 3.5 * halfRotationSpeedFactor);
        halfRotationTimeLine.play();

        return secondHalf;
    }

    private FallingRandomData getFirstHalfRandomData(FallingObjects object, FallingRandomData fruitData) {
        boolean isRightCurved = fruitData.isRightCurved();

        double halfMaxHeight = sceneHeight - object.getTranslateY() + 30; // 30 is the increase on the object current height.

        double halfXStartPoint = object.getTranslateX();

        double halfProjectionRange = (4 * sceneHeight - object.getTranslateY() + 50) / Math.tan(Math.toRadians(85));

        return new FallingRandomData(sceneHeight, sceneWidth, isRightCurved,
                halfMaxHeight, halfXStartPoint, halfProjectionRange);
    }

    private FallingRandomData getSecondHalfRandomData(FallingObjects object, FallingRandomData fruitData) {
        boolean isRightCurved = fruitData.isRightCurved();

        double halfMaxHeight = fruitData.getObjMaxHeight();

        double halfXStartPoint = generateSecondHalfLastXPoint(object, fruitData) + (fruitData.getProjectionRange() * (isRightCurved?-1:1));

        double halfProjectionRange = fruitData.getProjectionRange();

        return new FallingRandomData(sceneHeight, sceneWidth, isRightCurved,
                halfMaxHeight, halfXStartPoint, halfProjectionRange);
    }

    private double generateSecondHalfLastXPoint(FallingObjects object, FallingRandomData fruitData) {
        double secondTimeAtCurrentYPoint = fruitData.getSecondTimeAtYPoint(sceneHeight - object.getTranslateY());

        double xPointAtSecondTime = fruitData.getXPointAtTime(secondTimeAtCurrentYPoint);

        //The length between current xPoint of the object and the xPoint in the end of the projection.
        double deltaX = fruitData.getProjectionRange() - xPointAtSecondTime;

        //The last xPoint in the end of the projection
        double halfLastXPoint = object.getTranslateX() + ( deltaX * (fruitData.isRightCurved() ?1:-1) );

        return halfLastXPoint;
    }

    protected void activateFirstDifficulty() {
        currentDifficultyStage = 1;
        addingObjectsRate = 1;
        addingFruitsTimeline.setRate(addingObjectsRate);
        objectsMovingRate = 1;
        objectsRotateRate = 1;
        fruitsRatio = 20;
        bombsRatio = 2;
    }

    protected void activateSecondDifficulty() {
        currentDifficultyStage = 2;
        addingObjectsRate = 1.2;
        addingFruitsTimeline.setRate(addingObjectsRate);
        objectsMovingRate = 1.2;
        objectsRotateRate = 1.2;
        fruitsRatio = 20;
        bombsRatio = 5;
    }

    protected void activateThirdDifficulty() {
        currentDifficultyStage = 3;
        addingObjectsRate = 1.4;
        addingFruitsTimeline.setRate(addingObjectsRate);
        objectsMovingRate = 1.4;
        objectsRotateRate = 1.4;
        fruitsRatio = 20;
        bombsRatio = 10;
    }

    protected void endGame() {

        muteAllBombsFuses();

        sword.stopSwordSound();

        if (!isMuted)
            new SoundEffectFactory().createSoundObj(SoundEffectType.gameOver).playSound();

        checkForNewBestScore();

        PlayersCareTaker playersCareTaker = SaveData.createDataSaver(null).getPlayersCareTaker();
        int index = playersCareTaker.getMementoIndex(player.getName());
        playersCareTaker.addMemento(player.getData(), index);

        addingFruitsTimeline.stop();
        updatingGameTimer.stop();

        layout.getChildren().remove(swordLayout);

        layout.getChildren().addAll(new GamePlayScoreBoard(stage, sceneHeight, sceneWidth,
                scoreCounter.getCurrentScore(), scoreCounter.getBestScore(), isMuted), swordLayout);
    }

    protected void muteAllBombsFuses() {
        for (int i = 0; i < bombsFuseSounds.size(); i++)
            bombsFuseSounds.get(i).pause();
    }

    protected void unMuteAllBombsFuses() {
        for (int i = 0; i < bombsFuseSounds.size(); i++) {

            if (!isMuted)
                bombsFuseSounds.get(i).playSound();

        }
    }

    protected abstract void checkForNewBestScore();

    protected abstract void muteGameTypeExtraObjects();

    protected abstract void pauseGame();

    protected abstract void resumeGame();

    protected abstract void restartGame();

    protected abstract void saveGame();

    protected abstract void loadGame();

    private void activePauseMenuChoice(String choice) {
        switch (choice) {
            case "resume":
                resumeGame();
                break;

            case "restart":
                restartGame();
                break;

            case "end":
                closeGame();
                break;
        }
    }

    protected void resetGameStatus(String gameType) {
        for (int i = 0; i < objectsVector.size(); i++)
            layout.getChildren().remove(objectsVector.get(i));

        for (int i = 0; i < fruitJuiceVector.size(); i++)
            layout.getChildren().remove(fruitJuiceVector.get(i));

        layout.getChildren().remove(scoreCounter);
        setUpScoreCounter(gameType);

        objectsVector = new Vector<>();
        bombsFuseSounds = new Vector<>();
        fruitJuiceVector = new Vector<>();
        gamePaused = false;
        addingFruitsTimeline.play();
    }

    private void closeGame() {
        muteAllBombsFuses();

        sword.stopSwordSound();
        addingFruitsTimeline.stop();
        updatingGameTimer.stop();

        stage.close();
    }
}
