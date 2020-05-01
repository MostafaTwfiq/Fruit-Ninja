package GameTypes;

public class ArcadeGameMemento extends GamePlayMemento{

    private int currentTime;

    private double currentFreezeDurationTime;
    private double currentSpeedUpDurationTime;
    private boolean freezeIsActivated;
    private boolean speedUpIsActivated;

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public double getCurrentFreezeDurationTime() {
        return currentFreezeDurationTime;
    }

    public void setCurrentFreezeDurationTime(double currentFreezeDurationTime) {
        this.currentFreezeDurationTime = currentFreezeDurationTime;
    }

    public double getCurrentSpeedUpDurationTime() {
        return currentSpeedUpDurationTime;
    }

    public void setCurrentSpeedUpDurationTime(double currentSpeedUpDurationTime) {
        this.currentSpeedUpDurationTime = currentSpeedUpDurationTime;
    }

    public boolean isFreezeIsActivated() {
        return freezeIsActivated;
    }

    public void setFreezeIsActivated(boolean freezeIsActivated) {
        this.freezeIsActivated = freezeIsActivated;
    }

    public boolean isSpeedUpIsActivated() {
        return speedUpIsActivated;
    }

    public void setSpeedUpIsActivated(boolean speedUpIsActivated) {
        this.speedUpIsActivated = speedUpIsActivated;
    }


}
