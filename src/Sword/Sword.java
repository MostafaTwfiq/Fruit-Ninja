package Sword;

import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameSystem.SoundEffect.SoundEffectPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.Random;

public final class Sword {

    private double minLengthBetweenPoints = 8;

    private double thickness = 10;

    private PointsVector allPoints;

    private ImageView starsEffect;

    private Pane layout;

    private boolean isMuted;

    private Timeline swordSwipeTimeline;

    public Sword(Pane layout, boolean isMuted) {
        this.layout = layout;
        this.isMuted = isMuted;

        allPoints = new PointsVector(15);

        setUpStarsEffect();

        Timeline starsFading = new Timeline(new KeyFrame(Duration.millis(500),
                new KeyValue(starsEffect.opacityProperty(), 0.2)));
        starsFading.setCycleCount(Timeline.INDEFINITE);
        starsFading.play();

        Timeline minimizeSwordLength = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if (allPoints.getLength() > 10) {
                allPoints.remove(allPoints.getLength() - 1);
                drawSword();
            }
        }));
        minimizeSwordLength.setCycleCount(Timeline.INDEFINITE);
        minimizeSwordLength.play();

        setUpSwordSwipeTimeLine();
    }

    private void setUpStarsEffect() {

        try {
            FileInputStream imagePath = new FileInputStream("resources/sword/stars.gif");
            starsEffect = new ImageView(new Image(imagePath));
            starsEffect.setFitHeight(40);
            starsEffect.setFitWidth(40);

        } catch (Exception e) {
            System.out.println("There is something wrong happened while loading start effect.");
        }
    }

    public void removeSword() {
        allPoints.clear();
        layout.getChildren().clear();
    }

    public void drawSword() {
        layout.getChildren().clear();

        swordSwipeTimeline.playFromStart();

        if (allPoints.getLength() >= 5) {
            starsEffect.setLayoutX(allPoints.get(allPoints.getLength() - 1).getXPoint() - 20);
            starsEffect.setLayoutY(allPoints.get(allPoints.getLength() - 1).getYPoint() - 20);
            layout.getChildren().add(starsEffect);
            drawLines();
        }

    }

    private void drawFirstLine() {
        double thickness = 3;

        MousePoint point1 = allPoints.get(allPoints.getLength() - 1);
        MousePoint point2 = allPoints.get(allPoints.getLength() - 2);

        double angle = getThicknessAngle(point1, point2);

        MousePoint pointC = createPointC(point2, angle, thickness);

        MousePoint pointD = createPointD(point2, angle, thickness);

        Polygon polygon = createPolygon(point1, pointC, pointD, point1);

        Line line1 = createLine(point1, pointC);

        Line line2 = createLine(point1, pointD);

        layout.getChildren().addAll(polygon, line1, line2);
    }

    private void drawLastLine() {

        MousePoint point1 = allPoints.get(0);
        MousePoint point2 = allPoints.get(1);

        double angle = getThicknessAngle(point1, point2);

        MousePoint pointC = createPointC(point2, angle, thickness);

        MousePoint pointD = createPointD(point2, angle, thickness);

        Polygon polygon = createPolygon(point1, pointC, pointD, point1);
        Line line1 = createLine(point1, pointC);
        Line line2 = createLine(point1, pointD);

        layout.getChildren().addAll(polygon, line1, line2);
    }


    private void drawLines(){
        drawFirstLine();

        for (int i = allPoints.getLength() - 2; i > 1; i--) {
            double thickness = createThickness(i);

            MousePoint point1 = allPoints.get(i);
            MousePoint point2 = allPoints.get(i - 1);

            double angle = getThicknessAngle(point1, point2);
            MousePoint point1C = createPointC(point1, angle, thickness);
            MousePoint point1D = createPointD(point1, angle, thickness);
            MousePoint point2C = createPointC(point2, angle, thickness);
            MousePoint point2D = createPointD(point2, angle, thickness);

            Polygon polygon1 = createPolygon(point1C, point2C, point2D, point1D);
            Polygon polygon2 = createPolygon(point1C, point2D, point2C, point1D);
            Line line1 = createLine(point1C, point2C);
            Line line2 = createLine(point1D, point2D);

            layout.getChildren().addAll(polygon1, polygon2, line1, line2);
        }

        drawLastLine();
    }

    private double createThickness(int index) {
        return this.thickness * ((allPoints.getLength() - index + 2) * 1.0 / allPoints.getLength());
    }

    private double getThicknessAngle(MousePoint point1, MousePoint point2) {
        double angle = Math.toDegrees(Math.acos(Math.abs(point2.getXPoint() - point1.getXPoint()) / distBetweenTwoPoints(point1, point2)));
        MousePoint tempPoint1 = (point1.getYPoint() > point2.getYPoint()) ? (point1) : (point2);
        MousePoint tempPoint2 = (point1.getYPoint() < point2.getYPoint()) ? (point1) : (point2);
        angle = Math.toRadians(angle) * ((tempPoint1.getXPoint() > tempPoint2.getXPoint()) ? (1) : (-1));
        return angle;
    }

    private MousePoint createPointC(MousePoint point1, double angle, double thickness) {
        return new MousePoint(point1.getXPoint() - (thickness * Math.sin(angle)),
                point1.getYPoint() + (thickness * Math.cos(angle)));
    }

    private MousePoint createPointD(MousePoint point1, double angle, double thickness) {
        return new MousePoint(point1.getXPoint() + (thickness * Math.sin(angle)),
                point1.getYPoint() - (thickness * Math.cos(angle)));
    }

    private Polygon createPolygon(MousePoint point1, MousePoint point2,
                                 MousePoint point3, MousePoint point4) {

        Polygon polygon = new Polygon();
        polygon.setFill(Color.DARKCYAN);
        polygon.setStroke(Color.DARKCYAN);
        polygon.setScaleX(1.1);

        polygon.getPoints().addAll(
                point1.getXPoint(),point1.getYPoint(),
                point2.getXPoint(),point2.getYPoint(),
                point3.getXPoint(),point3.getYPoint(),
                point4.getXPoint(),point4.getYPoint(),
                point1.getXPoint(),point1.getYPoint());

        return polygon;
    }

    private Line createLine(MousePoint point1, MousePoint point2) {
        Line line = new Line();
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.setStartX(point1.getXPoint());
        line.setEndX(point2.getXPoint());
        line.setStartY(point1.getYPoint());
        line.setEndY(point2.getYPoint());
        return line;
    }

    public void addPoint(MousePoint point) {
        if (allPoints.getLength() == 0) {
            allPoints.add(point);
            return;
        }

        double lineLength = distBetweenTwoPoints(allPoints.get(0), point);

        if (lineLength >= minLengthBetweenPoints)
            allPoints.add(point);

    }

    private double distBetweenTwoPoints(MousePoint p1, MousePoint p2) {
        return Math.sqrt( Math.pow(p2.getXPoint() - p1.getXPoint(), 2)
                + Math.pow(p2.getYPoint() - p1.getYPoint(), 2) );
    }

    public void setLayout(Pane layout) {
        this.layout = layout;
    }

    private void setUpSwordSwipeTimeLine() {
        swordSwipeTimeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {

            if (allPoints.getLength() >= 8) {

                MousePoint point1 = allPoints.get(0);
                MousePoint point2 = allPoints.get(1);

                MousePoint point3 = allPoints.get(allPoints.getLength() - 1);
                MousePoint point4 = allPoints.get(allPoints.getLength() - 2);

                double angle1 = getThicknessAngle(point1, point2);
                double angle2 = getThicknessAngle(point3, point4);

                if (angle1 < 0 && angle2 > 0 || angle1 > 0 && angle2 < 0) {

                    if (!isMuted)
                        createRandomSwordSwipe().playSound();

                }
            }

        }));
        swordSwipeTimeline.setCycleCount(1);
        swordSwipeTimeline.playFromStart();
    }

    private SoundEffectPlayer createRandomSwordSwipe() {
        int randomNumber = new Random().nextInt(3);
        if (randomNumber == 0)
            return new SoundEffectFactory().createSoundObj(SoundEffectType.swordSwipe1);
        else if (randomNumber == 1)
            return new SoundEffectFactory().createSoundObj(SoundEffectType.swordSwipe2);
        else if (randomNumber == 2)
            return new SoundEffectFactory().createSoundObj(SoundEffectType.swordSwipe3);

        return null;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public void stopSwordSound() {
        swordSwipeTimeline.stop();
    }

}
