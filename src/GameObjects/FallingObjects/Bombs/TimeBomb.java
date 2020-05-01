package GameObjects.FallingObjects.Bombs;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FallingRandomData;
import GameObjects.FallingObjects.ProjectionTimeLine;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectFactory;
import GameSystem.SoundEffect.SoundEffectFactory.SoundEffectType;
import GameSystem.SoundEffect.SoundEffectPlayer;
import javafx.util.Duration;

public final class TimeBomb extends FallingObjects {

    private SoundEffectPlayer fuseSoundPlayer;

    public TimeBomb() {
        super("resources/bombs/timeBomb.png", FallingObjectType.timeBomb);

        fuseSoundPlayer = new SoundEffectFactory().createSoundObj(SoundEffectType.timeBombFuse);

        isSliceAble = true;

        objectSize = 90;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    /** This constructor will be useful while loading the game from a xml file.*/
    public TimeBomb(FallingRandomData fallingRandomData, double loadedTime) {
        super("resources/bombs/timeBomb.png", FallingObjectType.timeBomb);
        projectionTimeLine = new ProjectionTimeLine(this, fallingRandomData);
        projectionTimeLine.createProjectionTimeLine();
        projectionTimeLine.getMovingTimeLine().jumpTo(Duration.millis(loadedTime));

        fuseSoundPlayer = new SoundEffectFactory().createSoundObj(SoundEffectType.timeBombFuse);

        isSliceAble = true;

        objectSize = 90;
        setFitHeight(objectSize);
        setFitWidth(objectSize);
    }

    public SoundEffectPlayer getFuseSoundPlayer() {
        return fuseSoundPlayer;
    }
}
