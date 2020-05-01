package GameTypes;

import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FruitJuiceEffect.FruitJuiceEffect;

import java.util.Vector;

public abstract class GamePlayMemento {

    private String playerName;

    private int currentScore;

    private int currentDifficultyStage;
    private double addingObjectsRate;
    private double objectsMovingRate;
    private double objectsRotateRate;
    private int fruitsRatio;
    private int bombsRatio;
    private int specialFruitsRatio;

    private boolean isMuted;

    private Vector<FallingObjects> objectsVector;
    private Vector<FruitJuiceEffect> fruitJuiceVector;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Vector<FallingObjects> getObjectsVector() {
        return objectsVector;
    }

    public void setObjectsVector(Vector<FallingObjects> objectsVector) {
        this.objectsVector = objectsVector;
    }

    public Vector<FruitJuiceEffect> getFruitJuiceVector() {
        return fruitJuiceVector;
    }

    public void setFruitJuiceVector(Vector<FruitJuiceEffect> fruitJuiceVector) {
        this.fruitJuiceVector = fruitJuiceVector;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentDifficultyStage() {
        return currentDifficultyStage;
    }

    public void setCurrentDifficultyStage(int currentDifficultyStage) {
        this.currentDifficultyStage = currentDifficultyStage;
    }

    public double getAddingObjectsRate() {
        return addingObjectsRate;
    }

    public void setAddingObjectsRate(double addingObjectsRate) {
        this.addingObjectsRate = addingObjectsRate;
    }

    public double getObjectsMovingRate() {
        return objectsMovingRate;
    }

    public void setObjectsMovingRate(double objectsMovingRate) {
        this.objectsMovingRate = objectsMovingRate;
    }

    public double getObjectsRotateRate() {
        return objectsRotateRate;
    }

    public void setObjectsRotateRate(double objectsRotateRate) {
        this.objectsRotateRate = objectsRotateRate;
    }

    public int getFruitsRatio() {
        return fruitsRatio;
    }

    public void setFruitsRatio(int fruitsRatio) {
        this.fruitsRatio = fruitsRatio;
    }

    public int getBombsRatio() {
        return bombsRatio;
    }

    public void setBombsRatio(int bombsRatio) {
        this.bombsRatio = bombsRatio;
    }

    public int getSpecialFruitsRatio() {
        return specialFruitsRatio;
    }

    public void setSpecialFruitsRatio(int specialFruitsRatio) {
        this.specialFruitsRatio = specialFruitsRatio;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }
}
