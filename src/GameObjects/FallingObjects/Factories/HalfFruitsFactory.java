package GameObjects.FallingObjects.Factories;


import GameObjects.FallingObjects.FallingObjectType;
import GameObjects.FallingObjects.FallingObjects;
import GameObjects.FallingObjects.FruitObjects.*;
import GameObjects.FallingObjects.HalfFruits.*;

public final class HalfFruitsFactory {

    public FallingObjects createHalfFruit(int value) {

        for (FallingObjectType objectType: FallingObjectType.values()){

            if (objectType.getValue() == value)
                return createHalfFruit(objectType);

        }

        return null;
    }


   public FallingObjects createHalfFruit(FallingObjectType halfFruitType){

       switch (halfFruitType){
           case bananaHalf :
               return new BananaHalf();
           case kiwiHalf1:
               return new KiwiHalf1();
           case kiwiHalf2:
               return new KiwiHalf2();
           case orangeHalf:
               return new OrangeHalf();
           case pineappleHalf1:
               return new PineappleHalf1();
           case pineappleHalf2:
               return new PineappleHalf2();
           case redAppleHalf1:
               return new RedAppleHalf1();
           case redAppleHalf2:
               return new RedAppleHalf2();
           case waterMelonHalf:
               return new WaterMelonHalf();
           case goldenWaterMelonHalf:
               return new GoldenWaterMelonHalf();
           case purpleBananaHalf:
               return new PurpleBananaHalf();

       }

        return null;
    }

    public int getFruitHalfEnumNumber(FallingObjectType fruitType, int halfNumber) {
        if (halfNumber != 1 && halfNumber != 2)
            return -1;

        switch (fruitType) {
            case kiwi:
                return halfNumber == 1?FallingObjectType.kiwiHalf1.getValue():FallingObjectType.kiwiHalf2.getValue();
            case banana:
                return FallingObjectType.bananaHalf.getValue();
            case orange:
                return FallingObjectType.orangeHalf.getValue();
            case redApple:
                return halfNumber == 1?FallingObjectType.redAppleHalf1.getValue():FallingObjectType.redAppleHalf2.getValue();
            case pineapple:
                return halfNumber == 1?FallingObjectType.pineappleHalf1.getValue():FallingObjectType.pineappleHalf2.getValue();
            case waterMelon:
                return FallingObjectType.waterMelonHalf.getValue();
            case goldenWaterMelon:
                return FallingObjectType.goldenWaterMelonHalf.getValue();
            case purpleBanana:
                return FallingObjectType.purpleBananaHalf.getValue();
        }

        return -1;
    }
}
