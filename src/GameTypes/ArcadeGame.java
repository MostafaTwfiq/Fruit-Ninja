package GameTypes;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameSystem.SoundEffect.SoundEffectFactory.FallingObjectsSoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameTypes.PoppingImage.MinusTenImage;
import HandlingData.SaveData;
import Players.Player;
import Players.PlayersCareTaker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.FileInputStream;

public class ArcadeGame extends GamePlay {

    Timer timeCounter;
    Timeline timeUpChecker;

    private Timeline freezeTimeline;
    private double currentFreezeDurationTime;
    private Timeline speedUpTimeLine;
    private double currentSpeedUpDurationTime;
    private boolean freezeIsActivated;
    private boolean speedUpIsActivated;
    private boolean loadingGame;

    public ArcadeGame(Player player, boolean isMuted) {
        super(player, isMuted, "Arcade Game", "arcade", 2, new NumberRange(11, 11));

        setUpTimeCounter();
        setUpTimeUpChecker();

        freezeIsActivated = false;
        speedUpIsActivated = false;

        freezeTimeline = new Timeline();
        currentFreezeDurationTime = 0;
        speedUpTimeLine = new Timeline();
        currentSpeedUpDurationTime = 0;
        loadingGame = false;

        if (player.getArcadeGameMemento() != null) {
            loadingGame = true;
            loadGame();
            player.setArcadeGameMemento(null);
            loadingGame = false;
        }
    }

    private void setUpTimeCounter() {
        timeCounter = new Timer(60, isMuted);

        timeCounter.setLayoutX(sceneWidth - 60);
        timeCounter.setLayoutY(5);

        layout.getChildren().add(timeCounter);
        timeCounter.startTimer();
    }

    private void setUpTimeUpChecker() {
        timeUpChecker = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            if (timeCounter.isTimeUp())
                endArcadeGame();

        }));
        timeUpChecker.setCycleCount(Timeline.INDEFINITE);
        timeUpChecker.play();
    }

    @Override
    protected void missedObjectAction(FallingObjects object) {
        layout.getChildren().remove(object);
        objectsVector.remove(object);

        if (object.isSliceAble() && isBomb(object.getFallingObjectType()))
            stopBombFuse(object);

    }

    @Override
    protected void activeSlicedObjectAction(FallingObjects object) {
        FallingObjectType objectType = object.getFallingObjectType();

        if (isBomb(objectType))
            activeBombObjectAction(object);

        else if (isSpecialFruit(objectType))
            activeSpecialFruitObjectAction(object);

        else
            activeFruitObjectAction(object);

    }

    private boolean isSpecialFruit(FallingObjectType objectType) {
        return objectType == FallingObjectType.goldenWaterMelon || objectType == FallingObjectType.purpleBanana;
    }

    private void activeBombObjectAction(FallingObjects bomb) {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.dangerousBombExplode).playSound();

        layout.getChildren().remove(bomb);
        objectsVector.remove(bomb);

        MinusTenImage minusTenImage = new MinusTenImage();
        minusTenImage.setLayoutX( (sceneWidth / 2.0) - 50 );
        minusTenImage.setLayoutY( (sceneHeight / 2.0) - 50 );

        minusTenImage.getChangeSizeTimeLine().setOnFinished(e -> layout.getChildren().remove(minusTenImage));

        layout.getChildren().add(minusTenImage);
        minusTenImage.startChangingSize();

        timeCounter.decreaseTime(10);

    }

    private void activeFruitObjectAction(FallingObjects fruit) {
        if (!isMuted)
            new FallingObjectsSoundEffectFactory().createSliceSound(fruit).playSound();

        createFruitExplosion(fruit);

        layout.getChildren().remove(fruit);
        objectsVector.remove(fruit);

        if (isCriticalSlice(fruit)) {
            addHalfFruit(fruit, 2, 1.2);
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.criticalSlice).playSound();

            scoreCounter.increaseScore(25);

            createCriticalText(fruit);

        }

        else {
            addHalfFruit(fruit, 1, 1);
            scoreCounter.increaseScore(1);
        }

        if (scoreCounter.getCurrentScore() >= 100 && scoreCounter.getCurrentScore() < 200
                && currentDifficultyStage != 2) {

            if (!freezeIsActivated) {
                activateSecondDifficulty();
            }

        }

        else if (scoreCounter.getCurrentScore() >= 200 && currentDifficultyStage != 3) {
            if (!freezeIsActivated) {
                activateThirdDifficulty();
            }

        }

    }

    private void activeSpecialFruitObjectAction(FallingObjects specialFruit) {
        if (specialFruit.getFallingObjectType() == FallingObjectType.goldenWaterMelon) {
            speedingUpAddingObjectsRate();
            activeFruitObjectAction(specialFruit);
        }

        else if (specialFruit.getFallingObjectType() == FallingObjectType.purpleBanana) {
            freezeObjects();
            activeFruitObjectAction(specialFruit);
        }

    }

    @Override
    protected void checkForNewBestScore() {
        if (scoreCounter.thereIsNewBestScore())
            player.setArcadeBestScore(scoreCounter.getBestScore());

    }

    private void freezeObjects() {
        if (!freezeIsActivated || loadingGame) {

            setFreezeBackground();
            timeCounter.setCountingSpeedRate(0.5);

            freezeIsActivated = true;
            double currentObjectsMovingRate = objectsMovingRate;
            double currentObjectsRotatingRate = objectsRotateRate;
            objectsMovingRate = 0.2;
            objectsRotateRate = 0.2;

            for (int i = 0; i < objectsVector.size(); i++) {
                FallingObjects object = objectsVector.get(i);
                Timeline movingTimeLine = object.getProjectionTimeLine().getMovingTimeLine();
                Timeline rotatingTimeLine = object.getProjectionTimeLine().getRotationTimeLine();

                movingTimeLine.setRate(objectsMovingRate);
                rotatingTimeLine.setRate(objectsRotateRate);
            }

            freezeTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            }));
            freezeTimeline.setOnFinished(e -> {
                unFreezeObjects(currentObjectsMovingRate, currentObjectsRotatingRate);
                freezeIsActivated = false;
            });
            freezeTimeline.setCycleCount(1);

            freezeTimeline.play();
        }

        else
            freezeTimeline.playFrom(Duration.seconds(0));
    }

    private void unFreezeObjects(double originalObjectsMovingRate, double originalObjectsRotatingRate) {

        freezeTimeline.stop();

        setUpBackground();

        objectsMovingRate = originalObjectsMovingRate;
        objectsRotateRate = originalObjectsRotatingRate;

        timeCounter.setCountingSpeedRate(1);

        for (int i = 0; i < objectsVector.size(); i++) {
            FallingObjects object = objectsVector.get(i);
            Timeline movingTimeLine = object.getProjectionTimeLine().getMovingTimeLine();
            Timeline rotatingTimeLine = object.getProjectionTimeLine().getRotationTimeLine();

            movingTimeLine.setRate(originalObjectsMovingRate);
            rotatingTimeLine.setRate(originalObjectsRotatingRate);
        }

    }

    private void setFreezeBackground() {

        try {

            FileInputStream imagePath = new FileInputStream("resources/systemImages/freezeBackground.png");
            background.setImage(new Image(imagePath));
            background.setFitHeight(sceneHeight);
            background.setFitWidth(sceneWidth);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading freeze background.");
        }
    }

    private void speedingUpAddingObjectsRate() {
        if (!speedUpIsActivated) {

            speedUpIsActivated = true;

            int currentTimeBombRatio = bombsRatio;
            bombsRatio = 1;

            double currentAddingObjectsRate = addingObjectsRate;
            addingObjectsRate = 2.5;
            addingFruitsTimeline.setRate(addingObjectsRate);

            speedUpTimeLine = new Timeline(new KeyFrame(Duration.seconds(8), e -> {}));

            speedUpTimeLine.setOnFinished(e -> {
                unSpeedingUpAddingObjectsRate(currentAddingObjectsRate, currentTimeBombRatio);
                speedUpIsActivated = false;
            });
            speedUpTimeLine.setCycleCount(1);

            speedUpTimeLine.play();
        }

        else
            speedUpTimeLine.playFrom(Duration.seconds(0));
    }

    private void unSpeedingUpAddingObjectsRate(double originalAddingObjectsRate, int originalTimeBombRatio) {

        speedUpTimeLine.stop();

        addingObjectsRate = originalAddingObjectsRate;
        addingFruitsTimeline.setRate(addingObjectsRate);

        bombsRatio = originalTimeBombRatio;
    }

    private void endArcadeGame() {
        timeCounter.stopCounter();
        timeUpChecker.stop();
        endGame();
    }

    @Override
    protected void pauseGame() {
        muteAllBombsFuses();

        if (freezeIsActivated) {
            currentFreezeDurationTime = freezeTimeline.getCurrentTime().toMillis();
            freezeTimeline.stop();
        }

        if (speedUpIsActivated) {
            currentSpeedUpDurationTime = speedUpTimeLine.getCurrentTime().toMillis();
            speedUpTimeLine.stop();
        }

        timeCounter.stopCounter();

        timeUpChecker.stop();

        gamePaused = true;

        addingFruitsTimeline.stop();

        objectsCurrentDurationTime = new double[objectsVector.size()];

        for (int i = 0; i < objectsVector.size(); i++) {
            Timeline movingTimeLine = objectsVector.get(i).getProjectionTimeLine().getMovingTimeLine();
            Timeline rotatingTieLine = objectsVector.get(i).getProjectionTimeLine().getRotationTimeLine();
            objectsCurrentDurationTime[i] = movingTimeLine.getCurrentTime().toMillis();
            movingTimeLine.stop();
            rotatingTieLine.stop();

        }

    }

    @Override
    protected void resumeGame() {
        unMuteAllBombsFuses();

        if (freezeIsActivated) {
            freezeTimeline.playFrom(Duration.millis(currentFreezeDurationTime));
            currentSpeedUpDurationTime = 0;
        }

        if (speedUpIsActivated) {
            speedUpTimeLine.playFrom(Duration.millis(currentSpeedUpDurationTime));
            currentSpeedUpDurationTime = 0;
        }

        timeCounter.resumeCounter();

        timeUpChecker.play();

        gamePaused = false;

        addingFruitsTimeline.play();

        for (int i = 0; i < objectsVector.size(); i++) {
            FallingObjects object = objectsVector.get(i);
            FallingRandomData objectData = object.getProjectionTimeLine().getFallingRandomData();
            Timeline movingTimeLine = object.getProjectionTimeLine().getMovingTimeLine();
            Timeline rotatingTieLine = object.getProjectionTimeLine().getRotationTimeLine();

            object.setTranslateX(objectData.getXStartPoint());
            object.setTranslateY(sceneHeight);

            movingTimeLine.playFrom(Duration.millis(objectsCurrentDurationTime[i]));
            rotatingTieLine.play();

        }

    }

    @Override
    protected void restartGame() {
        resetGame();
    }

    protected void resetGame() {
        resetGameStatus("classic");

        layout.getChildren().remove(timeCounter);
        setUpTimeCounter();
        setUpTimeUpChecker();

        if (freezeIsActivated)
            unFreezeObjects(1 ,1);

        if (speedUpIsActivated)
            unSpeedingUpAddingObjectsRate(1, 1);

        freezeTimeline = null;
        speedUpTimeLine = null;
        freezeIsActivated = false;
        speedUpIsActivated = false;

        if (!isMuted)
            new SoundEffectFactory().createSoundObj(SoundEffectType.gameStart).playSound();

    }

    @Override
    protected void muteGameTypeExtraObjects() {
        timeCounter.setMuted(isMuted);
    }

    @Override
    protected void loadGame() {
        ArcadeGameMemento memento = player.getArcadeGameMemento();
        objectsVector = memento.getObjectsVector();
        for (int i = 0; i < objectsVector.size(); i++) {

            layout.getChildren().add(objectsVector.get(i));
            objectsVector.get(i).startMovingTimeLine();

            if (isBomb(objectsVector.get(i).getFallingObjectType()))
                playBombFuse(objectsVector.get(i));

        }

        fruitJuiceVector = memento.getFruitJuiceVector();
        for (int i = 0; i < fruitJuiceVector.size(); i++) {
            layout.getChildren().add(fruitJuiceVector.get(i));
            fruitJuiceVector.get(i).getFadingTimeline().play();
        }

        scoreCounter.increaseScore(memento.getCurrentScore());

        currentDifficultyStage = memento.getCurrentDifficultyStage();
        addingObjectsRate = memento.getAddingObjectsRate();
        objectsMovingRate = memento.getObjectsMovingRate();
        objectsRotateRate = memento.getObjectsRotateRate();
        fruitsRatio = memento.getFruitsRatio();
        bombsRatio = memento.getBombsRatio();
        specialFruitsRatio = memento.getSpecialFruitsRatio();

        if (currentDifficultyStage == 1)
            activateFirstDifficulty();
        else if (currentDifficultyStage == 2)
            activateSecondDifficulty();
        else if (currentDifficultyStage == 3)
            activateThirdDifficulty();

        isMuted = memento.isMuted();

        freezeIsActivated = memento.isFreezeIsActivated();
        speedUpIsActivated = memento.isSpeedUpIsActivated();
        currentFreezeDurationTime = memento.getCurrentFreezeDurationTime();
        currentSpeedUpDurationTime = memento.getCurrentSpeedUpDurationTime();

        if (freezeIsActivated) {
            freezeObjects();
            freezeTimeline.playFrom(Duration.millis(currentFreezeDurationTime));
        }
        if (speedUpIsActivated) {
            speedingUpAddingObjectsRate();
            speedUpTimeLine.playFrom(Duration.millis(currentSpeedUpDurationTime));
        }


        timeCounter.decreaseTime(timeCounter.getRemainingTime() - memento.getCurrentTime());

        pauseGame();
        pauseGameMenu.setChoice("");
        layout.getChildren().addAll(pauseRecLayout, pauseGameMenu);
        pauseTimeLine.playFromStart();

    }

    @Override
    protected void saveGame() {
        ArcadeGameMemento arcadeGameMemento = new ArcadeGameMemento();

        arcadeGameMemento.setPlayerName(player.getName());

        arcadeGameMemento.setObjectsVector(objectsVector);
        arcadeGameMemento.setFruitJuiceVector(fruitJuiceVector);

        arcadeGameMemento.setFreezeIsActivated(freezeIsActivated);
        arcadeGameMemento.setSpeedUpIsActivated(speedUpIsActivated);
        if (freezeIsActivated)
            arcadeGameMemento.setCurrentFreezeDurationTime(freezeTimeline.getCurrentTime().toMillis());
        else
            arcadeGameMemento.setCurrentFreezeDurationTime(0.0);

        if (speedUpIsActivated)
            arcadeGameMemento.setCurrentSpeedUpDurationTime(speedUpTimeLine.getCurrentTime().toMillis());
        else
            arcadeGameMemento.setCurrentSpeedUpDurationTime(0.0);

        arcadeGameMemento.setCurrentTime(timeCounter.getRemainingTime());

        arcadeGameMemento.setCurrentScore(scoreCounter.getCurrentScore());

        arcadeGameMemento.setCurrentDifficultyStage(currentDifficultyStage);
        arcadeGameMemento.setAddingObjectsRate(addingObjectsRate);
        arcadeGameMemento.setObjectsMovingRate(objectsMovingRate);
        arcadeGameMemento.setObjectsRotateRate(objectsRotateRate);
        arcadeGameMemento.setFruitsRatio(fruitsRatio);
        arcadeGameMemento.setBombsRatio(bombsRatio);
        arcadeGameMemento.setSpecialFruitsRatio(specialFruitsRatio);

        arcadeGameMemento.setMuted(isMuted);

        player.setArcadeGameMemento(arcadeGameMemento);

        PlayersCareTaker playersCareTaker = SaveData.createDataSaver(null).getPlayersCareTaker();

        int index = playersCareTaker.getMementoIndex(player.getName());

        playersCareTaker.addMemento(player.getData(), index);
    }

}