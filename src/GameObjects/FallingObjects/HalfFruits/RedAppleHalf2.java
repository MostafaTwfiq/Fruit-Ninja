package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class RedAppleHalf2 extends FallingObjects {

    public RedAppleHalf2() {
        super("resources/halfFruits/redAppleHalf2.png", FallingObjectType.redAppleHalf2);

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public RedAppleHalf2(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/redAppleHalf2.png", FallingObjectType.redAppleHalf2);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
