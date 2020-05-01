package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class PurpleBananaHalf extends FallingObjects {
    public PurpleBananaHalf() {
        super("resources/halfFruits/purpleBananaHalf.png", FallingObjectType.purpleBananaHalf);

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public PurpleBananaHalf(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/purpleBananaHalf.png", FallingObjectType.purpleBananaHalf);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
