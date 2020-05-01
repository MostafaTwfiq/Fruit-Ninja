package GameObjects.FruitJuiceEffect.FruitJuiceBubbles;

public final class OrangeFruitJuiceBubbles extends FruitJuiceBubbles {

    public OrangeFruitJuiceBubbles() {
        super("resources/bubbles/orangeBubbles.gif");
    }

    public OrangeFruitJuiceBubbles(double height, double width) {
        super("resources/bubbles/orangeBubbles.gif");
        setFitHeight(height);
        setFitWidth(width);
    }

}
