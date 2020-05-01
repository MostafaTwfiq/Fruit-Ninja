package GameObjects.FallingObjects.HalfFruits;


import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class PineappleHalf1 extends FallingObjects {

    public PineappleHalf1() {
        super("resources/halfFruits/pineappleHalf1.png", FallingObjectType.pineappleHalf1);

        isSliceAble = false;

        objectSize = 80;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public PineappleHalf1(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/halfFruits/pineappleHalf1.png", FallingObjectType.pineappleHalf1);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = false;

        objectSize = 80;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
