package GameObjects.FruitJuiceEffect.FruitJuiceBubbles;

public final class GreenFruitJuiceBubbles extends FruitJuiceBubbles {

    public GreenFruitJuiceBubbles() {
        super("resources/bubbles/greenBubbles.gif");
    }

    public GreenFruitJuiceBubbles(double height, double width) {
        super("resources/bubbles/greenBubbles.gif");
        setFitHeight(height);
        setFitWidth(width);
    }

}
