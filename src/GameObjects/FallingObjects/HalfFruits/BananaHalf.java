package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class BananaHalf extends FallingObjects {

    public BananaHalf() {
        super("resources/halfFruits/bananaHalf.png", FallingObjectType.bananaHalf);

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public BananaHalf(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/bananaHalf.png", FallingObjectType.bananaHalf);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
