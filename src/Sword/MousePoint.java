package Sword;

public final class MousePoint {

    private double xPoint;
    private double yPoint;

    public MousePoint(double xPoint, double yPoint) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
    }

    public double getXPoint() {
        return xPoint;
    }

    public double getYPoint() {
        return yPoint;
    }

    @Override
    public String toString(){
        return "XPoint: " + xPoint + " YPoint: " + yPoint;
    }
}
