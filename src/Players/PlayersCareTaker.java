package Players;

import java.util.ArrayList;

public final class PlayersCareTaker {

    private ArrayList<PlayerMemento> mementos;

    public PlayersCareTaker(int numberOfPlayers) {
        this.mementos = new ArrayList<>();
    }

    public PlayersCareTaker() {
        mementos = new ArrayList<>();
    }

    public void addMemento(PlayerMemento memento) {
        this.mementos.add(memento);
    }

    public void addMemento(PlayerMemento memento, int index) {
        this.mementos.remove(index);
        this.mementos.add(index, memento);
    }

    /** This method will return the memento object that has the same parameter playerName,
     otherwise it will return null.*/
    public PlayerMemento getMemento(String playerName) {
        for (PlayerMemento playerMemento : mementos) {

            if (playerMemento.equals(playerName))
                return playerMemento;

        }

        return null;

    }

    public PlayerMemento getMemento(int index) {
        return mementos.get(index);
    }

    /** This method will return the index of memento object that has the same parameter playerName in the array list,
     otherwise it will return -1.*/
    public int getMementoIndex(String playerName) {
        for (PlayerMemento playerMemento : mementos) {

            if (playerMemento.equals(playerName))
                return mementos.indexOf(playerMemento);

        }

        return -1;

    }

    public PlayerMemento[] mementosListToArray() {
        PlayerMemento[] mementosArray = new PlayerMemento[mementos.size()];

        for (int i = 0; i < mementos.size(); i++)
            mementosArray[i] = mementos.get(i);

        return mementosArray;
    }

    public int getLength() {
        return mementos.size();
    }

}
