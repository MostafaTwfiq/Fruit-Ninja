package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class Orange extends FallingObjects {

    public Orange() {
        super("resources/fruits/orange.png", FallingObjectType.orange);

        isSliceAble = true;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public Orange(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/orange.png", FallingObjectType.orange);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
