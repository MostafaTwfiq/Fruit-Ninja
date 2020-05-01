package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class RedApple extends FallingObjects {

    public RedApple() {
        super("resources/fruits/redApple.png", FallingObjectType.redApple);

        isSliceAble = true;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public RedApple(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/redApple.png", FallingObjectType.redApple);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
