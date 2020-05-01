package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class TransparentFruitJuiceExplosion extends FruitJuiceExplosion {
    public TransparentFruitJuiceExplosion() {
        super("resources/explosions/transparentExplosion.png");
    }

    public TransparentFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/transparentExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }
}
