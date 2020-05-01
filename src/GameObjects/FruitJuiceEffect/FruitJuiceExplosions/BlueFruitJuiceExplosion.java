package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class BlueFruitJuiceExplosion extends FruitJuiceExplosion{

    public BlueFruitJuiceExplosion() {
        super("resources/explosions/newBlueExplosion.png");
    }

    public BlueFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/newBlueExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }
}
