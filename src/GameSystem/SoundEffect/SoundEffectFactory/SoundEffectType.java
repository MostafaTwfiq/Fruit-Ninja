package GameSystem.SoundEffect.SoundEffectFactory;

public enum SoundEffectType {

    //Bomb sounds:
    dangerousBombExplode("bombSounds"), dangerousBombFuse("bombSounds"),
    fatalBombExplode("bombSounds"), fatalBombFuse("bombSounds"),
    timeBombExplode("bombSounds"), timeBombFuse("bombSounds"),

    //Combo sounds:
    combo1("combosSounds"), combo2("combosSounds"),
    comboBlitz1("combosSounds"), comboBlitz2("combosSounds"),

    //Game sounds:
    extraLife("gameRoundSounds"), gameOver("gameRoundSounds"),
    gameStart("gameRoundSounds"), newBestScore("gameRoundSounds"),
    swordSwipe1("gameRoundSounds"), swordSwipe2("gameRoundSounds"),
    swordSwipe3("gameRoundSounds"), throwBomb("gameRoundSounds"),
    throwFruit("gameRoundSounds"), timeBeep("gameRoundSounds"),
    timeTick("gameRoundSounds"), timeTok("gameRoundSounds"),
    timeUp("gameRoundSounds"),

    //Slices sounds:
    cleanSlice("slicesSound"), criticalSlice("slicesSound"),
    pomSlice("slicesSound"), splatterMedium1("slicesSound"),
    splatterMedium2("slicesSound"), splatterSmall("slicesSound");


    private String folderName;

    private SoundEffectType (String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
