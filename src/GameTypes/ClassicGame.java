package GameTypes;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameSystem.SoundEffect.SoundEffectFactory.FallingObjectsSoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import HandlingData.SaveData;
import Players.Player;
import Players.PlayersCareTaker;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ClassicGame extends GamePlay{

    private MissedFruitsCounter missedFruitsCounter;

    public ClassicGame(Player player, boolean isMuted) {
        super(player, isMuted, "Classic Game", "classic", 0, new NumberRange(9, 10));

        setUpMissedCounter();

        if (player.getClassicGameMemento() != null) {
            loadGame();
            player.setClassicGameMemento(null);
        }

    }

    private void setUpMissedCounter() {
        missedFruitsCounter = new MissedFruitsCounter();

        missedFruitsCounter.setLayoutX(sceneWidth - 160);
        missedFruitsCounter.setLayoutY(5);

        layout.getChildren().add(missedFruitsCounter);
    }

    @Override
    protected void missedObjectAction(FallingObjects object) {
        layout.getChildren().remove(object);
        objectsVector.remove(object);

        if (object.isSliceAble() && !isBomb(object.getFallingObjectType())) {
            missedFruitsCounter.increaseMissed();
            checkIfThreeMissed();
        }

        else if (object.isSliceAble() && isBomb(object.getFallingObjectType()))
            stopBombFuse(object);

    }

    private void checkIfThreeMissed() {
        if (missedFruitsCounter.isThreeMissed())
            endGame();
    }

    @Override
    protected void activeSlicedObjectAction(FallingObjects object) {
        FallingObjectType objectType = object.getFallingObjectType();

        if (isBomb(objectType))
            activeBombObjectAction(object);

        else
            activeFruitObjectAction(object);

    }

    private void activeBombObjectAction(FallingObjects bomb) {
        if (bomb.getFallingObjectType() == FallingObjectType.dangerousBomb) {
            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.dangerousBombExplode).playSound();

            layout.getChildren().remove(bomb);
            objectsVector.remove(bomb);

            missedFruitsCounter.increaseMissed();
            checkIfThreeMissed();
        }

        else if (bomb.getFallingObjectType() == FallingObjectType.fatalBomb)
            activeFatalBombAction();

    }

    private void activeFatalBombAction() {
        Rectangle explosionLight = new Rectangle(sceneWidth, sceneHeight);
        explosionLight.setFill(Color.WHITE);
        explosionLight.setOpacity(0);
        layout.getChildren().add(explosionLight);

        KeyValue explosionLightKeyValue = new KeyValue(explosionLight.opacityProperty(), 1);
        KeyFrame explosionLightKeyFrame = new KeyFrame(Duration.seconds(1.5), explosionLightKeyValue);

        Timeline bombExplosionTimeLine = new Timeline(explosionLightKeyFrame,
                new KeyFrame(Duration.seconds(4), e -> {

                    layout.getChildren().remove(explosionLight);
                    endGame();
        }));

        if (!isMuted)
            new SoundEffectFactory().createSoundObj(SoundEffectType.fatalBombExplode).playSound();

        pauseGame();
        bombExplosionTimeLine.play();
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


        if (scoreCounter.getCurrentScore() >= 100 && scoreCounter.getCurrentScore() < 300
                && currentDifficultyStage != 2) {

            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.extraLife).playSound();

            activateSecondDifficulty();
            missedFruitsCounter.decreaseMissed();

        }

        else if (scoreCounter.getCurrentScore() >= 300 && currentDifficultyStage != 3) {

            if (!isMuted)
                new SoundEffectFactory().createSoundObj(SoundEffectType.extraLife).playSound();

            activateThirdDifficulty();
            missedFruitsCounter.decreaseMissed();

        }

    }

    @Override
    protected void checkForNewBestScore() {
        if (scoreCounter.thereIsNewBestScore())
            player.setClassicBestScore(scoreCounter.getBestScore());

    }

    @Override
    protected void pauseGame() {
        muteAllBombsFuses();

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

        layout.getChildren().remove(missedFruitsCounter);
        setUpMissedCounter();

        if (!isMuted)
            new SoundEffectFactory().createSoundObj(SoundEffectType.gameStart).playSound();

    }

    @Override
    protected void muteGameTypeExtraObjects() {}

    @Override
    protected void loadGame() {
        ClassicGameMemento memento = player.getClassicGameMemento();
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

        for (int i = 0; i < memento.getMissedFruitsCount(); i++)
            missedFruitsCounter.increaseMissed();

        scoreCounter.increaseScore(memento.getCurrentScore());
        currentDifficultyStage = memento.getCurrentDifficultyStage();
        addingObjectsRate = memento.getAddingObjectsRate();
        objectsMovingRate = memento.getObjectsMovingRate();
        objectsRotateRate = memento.getObjectsRotateRate();
        fruitsRatio = memento.getFruitsRatio();
        bombsRatio = memento.getBombsRatio();
        specialFruitsRatio = memento.getSpecialFruitsRatio();
        isMuted = memento.isMuted();

        pauseGame();
        pauseGameMenu.setChoice("");
        layout.getChildren().addAll(pauseRecLayout, pauseGameMenu);
        pauseTimeLine.playFromStart();

    }

    @Override
    protected void saveGame() {
        ClassicGameMemento classicGameMemento = new ClassicGameMemento();

        classicGameMemento.setPlayerName(player.getName());

        classicGameMemento.setObjectsVector(objectsVector);
        classicGameMemento.setFruitJuiceVector(fruitJuiceVector);

        classicGameMemento.setMissedFruitsCount(missedFruitsCounter.getMissedFruitsCount());

        classicGameMemento.setCurrentScore(scoreCounter.getCurrentScore());

        classicGameMemento.setCurrentDifficultyStage(currentDifficultyStage);
        classicGameMemento.setAddingObjectsRate(addingObjectsRate);
        classicGameMemento.setObjectsMovingRate(objectsMovingRate);
        classicGameMemento.setObjectsRotateRate(objectsRotateRate);
        classicGameMemento.setFruitsRatio(fruitsRatio);
        classicGameMemento.setBombsRatio(bombsRatio);
        classicGameMemento.setSpecialFruitsRatio(specialFruitsRatio);

        classicGameMemento.setMuted(isMuted);

        player.setClassicGameMemento(classicGameMemento);

        PlayersCareTaker playersCareTaker = SaveData.createDataSaver(null).getPlayersCareTaker();

        int index = playersCareTaker.getMementoIndex(player.getName());

        playersCareTaker.addMemento(player.getData(), index);
    }

}
