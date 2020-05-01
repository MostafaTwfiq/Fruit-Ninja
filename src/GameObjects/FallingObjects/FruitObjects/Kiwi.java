package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class Kiwi extends FallingObjects {

    public Kiwi() {
        super("resources/fruits/kiwi.png", FallingObjectType.kiwi);

        isSliceAble = true;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public Kiwi(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/kiwi.png", FallingObjectType.kiwi);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 50;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
