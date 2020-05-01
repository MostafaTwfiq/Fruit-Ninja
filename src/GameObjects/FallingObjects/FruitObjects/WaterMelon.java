package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import javafx.util.Duration;

public final class WaterMelon extends FallingObjects {

    public WaterMelon() {
        super("resources/fruits/waterMelon.png", FallingObjectType.waterMelon);

        isSliceAble = true;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public WaterMelon(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/waterMelon.png", FallingObjectType.waterMelon);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
