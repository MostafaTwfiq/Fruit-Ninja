package Players;

import GameTypes.ArcadeGameMemento;
import GameTypes.ClassicGameMemento;

public final class PlayerMemento {

    private int classicBestScore;
    private int arcadeBestScore;
    private String playerName;

    private ClassicGameMemento classicGameMemento;
    private ArcadeGameMemento arcadeGameMemento;

    public PlayerMemento(String playerName, int classicBestScore, int arcadeBestScore,
                         ClassicGameMemento classicGameMemento, ArcadeGameMemento arcadeGameMemento) {

        this.playerName = playerName;
        this.classicBestScore = classicBestScore;
        this.arcadeBestScore = arcadeBestScore;

        this.classicGameMemento = classicGameMemento;
        this.arcadeGameMemento = arcadeGameMemento;
    }

    public int getClassicBestScore() {
        return this.classicBestScore;
    }

    public int getArcadeBestScore() {
        return arcadeBestScore;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public ClassicGameMemento getClassicGameMemento() {
        return classicGameMemento;
    }

    public ArcadeGameMemento getArcadeGameMemento() {
        return arcadeGameMemento;
    }

    /** This method are overrode from the Object class
     * This method will check if the player name of this memento are equals,
     to the string object that has been received from the parameter
     * If the parameter obj are an instance of PlayerMemento class,
     it will check if this object and the parameter are referencing the same object in the heap.*/
    @Override
    public boolean equals(Object obj){
        if (obj instanceof PlayerMemento)
            return this == obj;

        return playerName.equals(obj);
    }

    /** This method are overrode from the Object class.
     * This method will be useful while debugging.*/
    @Override
    public String toString() {
        return "Player name: " + playerName + "\n"
                + "Player classic best score: " + classicBestScore
                + "Player arcade best score: " + arcadeBestScore;
    }
}
