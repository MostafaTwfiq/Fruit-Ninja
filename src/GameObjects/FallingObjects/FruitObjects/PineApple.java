package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class PineApple extends FallingObjects {

    public PineApple() {
        super("resources/fruits/pineApple.png", FallingObjectType.pineapple);

        isSliceAble = true;

        objectSize = 150;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public PineApple(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/pineApple.png", FallingObjectType.pineapple);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 150;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
