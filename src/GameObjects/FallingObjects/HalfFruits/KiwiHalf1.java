package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class KiwiHalf1 extends FallingObjects {

    public KiwiHalf1() {
        super("resources/halfFruits/kiwiHalf1.png", FallingObjectType.kiwiHalf1);

        isSliceAble = false;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public KiwiHalf1(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/kiwiHalf1.png", FallingObjectType.kiwiHalf1);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
