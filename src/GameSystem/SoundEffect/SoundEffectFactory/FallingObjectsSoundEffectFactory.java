package GameSystem.SoundEffect.SoundEffectFactory;

import GameObjects.FallingObjects.Bombs.DangerousBomb;
import GameObjects.FallingObjects.Bombs.FatalBomb;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FruitObjects.*;
import GameSystem.SoundEffect.SoundEffectPlayer;

public class FallingObjectsSoundEffectFactory extends SoundEffectFactory{

    public SoundEffectPlayer createThrowSound(FallingObjects object) {
        if (object instanceof DangerousBomb || object instanceof FatalBomb)
            return createSoundObj(SoundEffectType.throwBomb);

        else
            return createSoundObj(SoundEffectType.throwFruit);

    }

    public SoundEffectPlayer createSliceSound(FallingObjects object) {
        if (object instanceof DangerousBomb)
            return createSoundObj(SoundEffectType.dangerousBombExplode);

        else if (object instanceof FatalBomb)
            return createSoundObj(SoundEffectType.fatalBombExplode);

        else if (object instanceof Banana || object instanceof PurpleBanana)
            return createSoundObj(SoundEffectType.cleanSlice);

        else if (object instanceof GoldenWaterMelon || object instanceof WaterMelon)
            return createSoundObj(SoundEffectType.splatterMedium1);

        else if (object instanceof Kiwi)
            return createSoundObj(SoundEffectType.pomSlice);

        else if (object instanceof Orange)
            return createSoundObj(SoundEffectType.splatterSmall);

        else if (object instanceof PineApple)
            return createSoundObj(SoundEffectType.splatterMedium2);

        else if (object instanceof RedApple)
            return createSoundObj(SoundEffectType.splatterMedium2);

        return null;
    }
    
}
