package GameObjects.FruitJuiceEffect.FruitJuiceBubbles;

public final class YellowFruitJuiceBubbles extends FruitJuiceBubbles {

    public YellowFruitJuiceBubbles() {
        super("resources/bubbles/yellowBubbles.gif");
    }

    public YellowFruitJuiceBubbles(double height, double width) {
        super("resources/bubbles/yellowBubbles.gif");
        setFitHeight(height);
        setFitWidth(width);
    }

}
