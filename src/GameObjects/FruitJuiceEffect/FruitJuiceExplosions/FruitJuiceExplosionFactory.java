package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

import GameObjects.FallingObjects.FallingObjectType;

public final class FruitJuiceExplosionFactory {

    public FruitJuiceExplosion createJuiceExplosion(FallingObjectType fruitType) {

        switch (fruitType) {
            case waterMelon:
                return new RedFruitJuiceExplosion(120, 200);
            case goldenWaterMelon:
                return new GoldFruitJuiceExplosion(120, 200);
            case kiwi:
                return new GreenFruitJuiceExplosion(100, 180);
            case orange:
                return new OrangeFruitJuiceExplosion(100, 180);
            case redApple:
                return new YellowFruitJuiceExplosion(100, 180);
            case pineapple:
                return new YellowFruitJuiceExplosion(120, 200);
            case banana:
            case purpleBanana:
                return new TransparentFruitJuiceExplosion(70, 70);

        }

        return null;
    }
}
