package GameObjects.FruitJuiceEffect.FruitJuiceExplosions;

public final class OrangeFruitJuiceExplosion extends FruitJuiceExplosion{

    public OrangeFruitJuiceExplosion() {
        super("resources/explosions/newOrangeExplosion.png");
    }

    public OrangeFruitJuiceExplosion(double height, double width) {
        super("resources/explosions/newOrangeExplosion.png");
        setFitHeight(height);
        setFitWidth(width);
    }

}
