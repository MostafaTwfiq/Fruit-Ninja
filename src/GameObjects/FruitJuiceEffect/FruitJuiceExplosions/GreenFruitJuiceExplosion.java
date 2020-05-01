package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class GreenFruitJuiceExplosion extends FruitJuiceExplosion {
    public GreenFruitJuiceExplosion() {
        super("resources/explosions/newGreenExplosion.png");
    }

    public GreenFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/newGreenExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }
}
