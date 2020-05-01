package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class GoldFruitJuiceExplosion extends FruitJuiceExplosion {
    public GoldFruitJuiceExplosion() {
        super("resources/explosions/newGoldExplosion.png");
    }

    public GoldFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/newGoldExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }
}
