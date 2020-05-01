package GameObjects.FallingObjects.Factories;

import GameObjects.FallingObjects.Bombs.DangerousBomb;
import GameObjects.FallingObjects.Bombs.FatalBomb;
import GameObjects.FallingObjects.Bombs.TimeBomb;
import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;

public final class BombFactory {

    public FallingObjects createBomb(FallingObjectType bombType) {

        switch (bombType) {
            case fatalBomb:
                return  new FatalBomb();

            case dangerousBomb:
                return new DangerousBomb();

            case timeBomb:
                return new TimeBomb();
        }

        return null;
    }

    public FallingObjects createBomb(int value) {

        //take random value 7 or 8
        for (FallingObjectType bombType : FallingObjectType.values()){

            if(bombType.getValue() == value)
                return createBomb(bombType);

        }

        return null;
    }
}
