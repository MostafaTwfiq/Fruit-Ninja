package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class KiwiHalf2 extends FallingObjects {

    public KiwiHalf2() {
        super("resources/halfFruits/kiwiHalf2.png", FallingObjectType.kiwiHalf2);

        isSliceAble = false;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }
    public KiwiHalf2(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/kiwiHalf2.png", FallingObjectType.kiwiHalf2);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
