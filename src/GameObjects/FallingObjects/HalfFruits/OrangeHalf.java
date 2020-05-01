package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class OrangeHalf extends FallingObjects {

    public OrangeHalf() {
        super("resources/halfFruits/orangeHalf.png", FallingObjectType.orangeHalf);

        isSliceAble = false;

        objectSize = 60;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public OrangeHalf(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/orangeHalf.png", FallingObjectType.orangeHalf);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 60;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
