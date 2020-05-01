package GameSystem.SoundEffect.SoundEffectFactory;

import GameSystem.SoundEffect.SoundEffectPlayer;

public class SoundEffectFactory {

    public SoundEffectPlayer createSoundObj(SoundEffectType soundEffectType) {

                return new SoundEffectPlayer("resources/soundEffects/"
                        + soundEffectType.getFolderName() + "/"
                        + soundEffectType.toString()
                        + ".wav");
    }

}
