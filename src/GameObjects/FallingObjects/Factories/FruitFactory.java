package GameObjects.FallingObjects.Factories;

import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FruitObjects.*;

public final class FruitFactory {

    public FallingObjects createFruit(FallingObjectType fruitType) {

        switch (fruitType){
            case kiwi:
                return new Kiwi();
            case banana:
                return new Banana();
            case orange:
                return new Orange();
            case redApple:
                return new RedApple();
            case pineapple:
                return new PineApple();
            case waterMelon:
                return new WaterMelon();
            case goldenWaterMelon:
                return new GoldenWaterMelon();
            case purpleBanana:
                return new PurpleBanana();

        }

        return null;
    }


    public FallingObjects createFruit(int fruitNumber) {

       //take random value from 1 to 8
        for(FallingObjectType fruitType:FallingObjectType.values()){

            if (fruitType.getValue() == fruitNumber)
                return createFruit(fruitType);

        }

        return null;
    }


}
