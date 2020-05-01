package GameObjects.FruitJuiceEffect.FruitJuiceBubbles;

import GameObjects.FallingObjects.FallingObjectType;

public final class FruitJuiceBubblesFactory {

    public FruitJuiceBubbles createJuiceExplosion(FallingObjectType fruitType) {

        switch (fruitType) {
            case waterMelon:
                return new RedFruitJuiceBubbles(70, 70);
            case goldenWaterMelon:
            case pineapple:
            case redApple:
                return new YellowFruitJuiceBubbles(70, 70);
            case kiwi:
                return new GreenFruitJuiceBubbles(70, 70);
            case orange:
                return new OrangeFruitJuiceBubbles(70, 70);
            case banana:
            case purpleBanana:
                FruitJuiceBubbles bubbles =  new BlueFruitJuiceBubbles(70, 70);
                bubbles.setOpacity(0);
                return bubbles;

        }

        return null;
    }

}
