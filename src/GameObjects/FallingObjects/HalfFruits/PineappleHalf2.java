package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class PineappleHalf2 extends FallingObjects {

    public PineappleHalf2() {
        super("resources/halfFruits/pineappleHalf2.png", FallingObjectType.pineappleHalf2);

        isSliceAble = false;

        objectSize = 80;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public PineappleHalf2(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/pineappleHalf2.png", FallingObjectType.pineappleHalf2);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 80;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
