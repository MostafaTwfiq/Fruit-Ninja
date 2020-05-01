package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class WaterMelonHalf extends FallingObjects {

    public WaterMelonHalf() {
        super("resources/halfFruits/waterMelonHalf.png", FallingObjectType.waterMelonHalf);

        isSliceAble = false;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public WaterMelonHalf(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/waterMelonHalf.png", FallingObjectType.waterMelonHalf);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}

