package GameObjects.FallingObjects;

public enum FallingObjectType {

    //Fruits objects:
    banana(1), kiwi(2), orange(3), pineapple(4), redApple(5), waterMelon(6),

    //special fruits objects:
    goldenWaterMelon(7), purpleBanana(8),

    //Bombs objects:
    fatalBomb(9), dangerousBomb(10), timeBomb(11),

    //Half fruits objects:
    bananaHalf(12), kiwiHalf1(13), kiwiHalf2(14), orangeHalf(15), pineappleHalf1(16),
    pineappleHalf2(17), redAppleHalf1(18), redAppleHalf2(19), waterMelonHalf(20),

    //special half fruits objects:
    purpleBananaHalf(21),goldenWaterMelonHalf(22);


    private int value;

    private FallingObjectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
