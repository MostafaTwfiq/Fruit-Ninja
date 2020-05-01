package GameObjects.FallingObjects;

import GameObjects.GameObject;

public abstract class FallingObjects extends GameObject {

    private FallingObjectType fallingObjectType;

    protected ProjectionTimeLine projectionTimeLine;

    protected double objectSize;

    protected boolean isSliceAble;

    public FallingObjects(String path, FallingObjectType fallingObjectType) {
        super(path);
        this.fallingObjectType = fallingObjectType;
    }

    public void startMovingTimeLine() {
        projectionTimeLine.getMovingTimeLine().play();
        projectionTimeLine.getRotationTimeLine().play();
    }

    public void setMovingRate(double rate) {
        projectionTimeLine.getMovingTimeLine().setRate(rate);
    }

    public ProjectionTimeLine getProjectionTimeLine() {
        return projectionTimeLine;
    }

    public void setProjectionTimeLine(ProjectionTimeLine projectionTimeLine) {
        this.projectionTimeLine = projectionTimeLine;
        projectionTimeLine.createProjectionTimeLine();
    }

    public FallingObjectType getFallingObjectType() {
        return fallingObjectType;
    }

    public double getObjectSize() {
        return objectSize;
    }

    public boolean isSliceAble() {
        return isSliceAble;
    }

}