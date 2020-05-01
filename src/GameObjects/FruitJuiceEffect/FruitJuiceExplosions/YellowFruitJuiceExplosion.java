package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class YellowFruitJuiceExplosion extends FruitJuiceExplosion{

    public YellowFruitJuiceExplosion() {
        super("resources/explosions/newYellowExplosion.png");
    }

    public YellowFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/newYellowExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }

}
