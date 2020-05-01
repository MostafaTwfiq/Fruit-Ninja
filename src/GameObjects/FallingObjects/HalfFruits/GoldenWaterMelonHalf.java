package GameObjects.FallingObjects.HalfFruits;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class GoldenWaterMelonHalf extends FallingObjects {
    public GoldenWaterMelonHalf() {
        super("resources/halfFruits/goldWaterMelonHalf.png", FallingObjectType.goldenWaterMelonHalf);

        isSliceAble = false;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public GoldenWaterMelonHalf(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/goldWaterMelonHalf.png", FallingObjectType.goldenWaterMelonHalf);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }


}
