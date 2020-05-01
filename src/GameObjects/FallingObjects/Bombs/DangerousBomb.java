package GameObjects.FallingObjects.Bombs;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameSystem.SoundEffect.SoundEffectPlayer;
import javafx.util.Duration;

public final class DangerousBomb extends FallingObjects {

    private SoundEffectPlayer fuseSoundPlayer;

    public DangerousBomb() {
        super("resources/bombs/dangerousBomb.png", FallingObjectType.dangerousBomb);

        fuseSoundPlayer = new SoundEffectFactory().createSoundObj(SoundEffectType.dangerousBombFuse);

        isSliceAble = true;

        objectSize = 90;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public DangerousBomb(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/bombs/dangerousBomb.png", FallingObjectType.dangerousBomb);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        fuseSoundPlayer = new SoundEffectFactory().createSoundObj(SoundEffectType.dangerousBombFuse);

        isSliceAble = true;

        objectSize = 90;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public SoundEffectPlayer getFuseSoundPlayer() {
        return fuseSoundPlayer;
    }

}
