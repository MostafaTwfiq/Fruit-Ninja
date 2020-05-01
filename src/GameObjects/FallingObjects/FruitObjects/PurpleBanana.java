package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public class PurpleBanana extends FallingObjects {

    public PurpleBanana() {
        super("resources/fruits/PurpleBanana.png", FallingObjectType.purpleBanana);

        isSliceAble = true;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public PurpleBanana(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/PurpleBanana.png", FallingObjectType.purpleBanana);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 70;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
