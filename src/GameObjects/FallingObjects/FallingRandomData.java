package GameObjects.FallingObjects;

public final class FallingRandomData {

    private int sceneHeight;

    private int sceneWidth;

    private double gravityConstant;

    private boolean rightCurved;

    private double objInitialSpeed;

    private double objMaxHeight;

    private double xStartPoint;

    private double projectionMaxRange;

    private double projectionRange;

    private double projectionAngle;

    private double totalTime;


    private boolean generateRandomDirection() {
        return (int) Math.ceil(Math.random() * 2) == 1;
    }

    private double generateRandomMaxHeight() {
        return 300 + Math.random() * (sceneHeight - 300);
    }

    private double generateMaxRange() {
        return objMaxHeight * 4.0;
    }

    private double generateRandomXPoint() {
        double emptySpace = 100.0;
        return emptySpace + Math.random() * (sceneWidth - emptySpace * 2);
    }

    private double generateRandomRange() {
        int emptySpace = 10;

        if (rightCurved) {
            return Math.abs(sceneWidth - xStartPoint) >= projectionMaxRange
                    ? Math.random() * ( projectionMaxRange - emptySpace)
                    : Math.random() * Math.abs( sceneWidth - xStartPoint - emptySpace );
        }

        else {
            return Math.abs(xStartPoint) >= projectionMaxRange
                    ? Math.random() * ( projectionMaxRange - emptySpace)
                    : Math.random() * Math.abs( xStartPoint - emptySpace );
        }

    }

    private double calculateAngle() {
        return Math.abs( Math.atan( (objMaxHeight * 4) / projectionRange) );
    }

    private double calculateInitialSpeed() {
        return Math.sqrt( (projectionRange * gravityConstant) / Math.sin(2 * projectionAngle));
    }

    private double calculateTotalTime() {
        return 2 * ( (objInitialSpeed * Math.sin(projectionAngle)) / gravityConstant);
    }

    public double getFirstTimeAtYPoint(double yPoint) {
        return ((objInitialSpeed * Math.sin(projectionAngle)
                - Math.sqrt( Math.pow(objInitialSpeed * Math.sin(projectionAngle), 2) - (2 * yPoint * gravityConstant) ) ) )
                / (gravityConstant);
    }

    public double getSecondTimeAtYPoint(double yPoint) {
        return ( (objInitialSpeed * Math.sin(projectionAngle)
                + Math.sqrt( Math.pow(objInitialSpeed * Math.sin(projectionAngle), 2) - (2 * yPoint * gravityConstant) ) ) )
                / (gravityConstant * 1.0);
    }

    public double getXPointAtTime(double time) {
        return objInitialSpeed * Math.cos(projectionAngle) * time;
    }

    public FallingRandomData(int sceneHeight, int sceneWidth) {
        this.sceneHeight = sceneHeight;
        this.sceneWidth = sceneWidth;

        rightCurved = generateRandomDirection();
        gravityConstant = 9.8;
        objMaxHeight = generateRandomMaxHeight();
        projectionMaxRange = generateMaxRange();
        xStartPoint = generateRandomXPoint();
        projectionRange = generateRandomRange();
        projectionAngle = calculateAngle();
        objInitialSpeed = calculateInitialSpeed();
        totalTime = calculateTotalTime();
    }

    public FallingRandomData(int sceneHeight, int sceneWidth,
                             boolean rightCurved, double objMaxHeight,
                             double xStartPoint, double projectionRange) {

        this.sceneHeight = sceneHeight;
        this.sceneWidth = sceneWidth;

        this.rightCurved = rightCurved;
        gravityConstant = 9.8;
        this.objMaxHeight = objMaxHeight;
        projectionMaxRange = generateMaxRange();
        this.xStartPoint = xStartPoint;
        this.projectionRange = projectionRange;
        projectionAngle = calculateAngle();
        objInitialSpeed = calculateInitialSpeed();
        totalTime = calculateTotalTime();
    }

    public double getObjMaxHeight() {
        return objMaxHeight;
    }

    public double getXStartPoint() {
        return xStartPoint;
    }

    public double getProjectionRange() {
        return projectionRange;
    }

    public double getTotalTime() {return totalTime;}

    public int getSceneHeight() {
        return sceneHeight;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public double getGravityConstant() {
        return gravityConstant;
    }

    public double getObjInitialSpeed() {
        return objInitialSpeed;
    }

    public double getProjectionAngle() {
        return projectionAngle;
    }

    public boolean isRightCurved() {
        return rightCurved;
    }

}
