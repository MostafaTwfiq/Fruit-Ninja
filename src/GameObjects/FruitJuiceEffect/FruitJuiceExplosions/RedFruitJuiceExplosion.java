package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class RedFruitJuiceExplosion extends FruitJuiceExplosion{

    public RedFruitJuiceExplosion() {
        super("resources/explosions/newRedExplosion.png");
    }

    public RedFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/newRedExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }
}
