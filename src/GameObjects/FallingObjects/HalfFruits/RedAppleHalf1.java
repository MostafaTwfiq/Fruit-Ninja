package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class RedAppleHalf1 extends FallingObjects {

    public RedAppleHalf1() {
        super("resources/halfFruits/redAppleHalf1.png", FallingObjectType.redAppleHalf1);

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public RedAppleHalf1(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/redAppleHalf1.png", FallingObjectType.redAppleHalf1);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
