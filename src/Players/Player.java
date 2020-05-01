package Players;

import GameTypes.ArcadeGameMemento;
import GameTypes.ClassicGameMemento;

public class Player {

    private String name;
    private int classicBestScore;
    private int arcadeBestScore;

    private ClassicGameMemento classicGameMemento;
    private ArcadeGameMemento arcadeGameMemento;

    public Player(String name, int classicBestScore, int arcadeBestScore,
                  ClassicGameMemento classicGameMemento, ArcadeGameMemento arcadeGameMemento) {

        this.name = name;
        this.classicBestScore = classicBestScore;
        this.arcadeBestScore = arcadeBestScore;

        this.classicGameMemento = classicGameMemento;
        this.arcadeGameMemento = arcadeGameMemento;
    }

    public Player() {
        name = "";
        this.classicBestScore = 0;
        this.arcadeBestScore = 0;

        this.classicGameMemento = null;
        this.arcadeGameMemento = null;
    }

    public void setData(PlayerMemento memento) {
        this.name = memento.getPlayerName();
        this.classicBestScore = memento.getClassicBestScore();
        this.arcadeBestScore = memento.getArcadeBestScore();

        this.classicGameMemento = memento.getClassicGameMemento();
        this.arcadeGameMemento = memento.getArcadeGameMemento();
    }

    public PlayerMemento getData() {
        return new PlayerMemento(this.name, this.classicBestScore, this.arcadeBestScore,
                this.classicGameMemento, this.arcadeGameMemento);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassicBestScore() {
        return this.classicBestScore;
    }

    public void setClassicBestScore(int classicBestScore) {
        this.classicBestScore = classicBestScore;
    }

    public int getArcadeBestScore() {
        return arcadeBestScore;
    }

    public void setArcadeBestScore(int arcadeBestScore) {
        this.arcadeBestScore = arcadeBestScore;
    }

    public ClassicGameMemento getClassicGameMemento() {
        return classicGameMemento;
    }

    public void setClassicGameMemento(ClassicGameMemento classicGameMemento) {
        this.classicGameMemento = classicGameMemento;
    }

    public ArcadeGameMemento getArcadeGameMemento() {
        return arcadeGameMemento;
    }

    public void setArcadeGameMemento(ArcadeGameMemento arcadeGameMemento) {
        this.arcadeGameMemento = arcadeGameMemento;
    }
}
