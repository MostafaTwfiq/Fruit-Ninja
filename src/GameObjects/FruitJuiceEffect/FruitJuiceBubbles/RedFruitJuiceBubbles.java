package GameObjects.FruitJuiceEffect.FruitJuiceBubbles;

public final class RedFruitJuiceBubbles extends FruitJuiceBubbles {

    public RedFruitJuiceBubbles() {
        super("resources/bubbles/redBubbles.gif");
    }

    public RedFruitJuiceBubbles(double height, double width) {
        super("resources/bubbles/redBubbles.gif");
        setFitHeight(height);
        setFitWidth(width);
    }

}
