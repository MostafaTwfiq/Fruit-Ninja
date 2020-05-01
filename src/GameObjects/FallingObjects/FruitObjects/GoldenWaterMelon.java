package GameObjects.FallingObjects.FruitObjects;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameSystem.SoundEffect.SoundEffectPlayer;
import javafx.util.Duration;

public final class GoldenWaterMelon extends FallingObjects {

    public GoldenWaterMelon() {
        super("resources/fruits/goldWaterMelon.png", FallingObjectType.goldenWaterMelon);

        isSliceAble = true;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public GoldenWaterMelon(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/fruits/goldWaterMelon.png", FallingObjectType.waterMelon);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        isSliceAble = true;

        objectSize = 100;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

}
