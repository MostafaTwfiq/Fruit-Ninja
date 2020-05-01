package GameObjects.FruitJuiceEffect.FruitJuiceBubbles;

public final class BlueFruitJuiceBubbles extends FruitJuiceBubbles {

    public BlueFruitJuiceBubbles() {
        super("resources/bubbles/blueBubbles.gif");
    }

    public BlueFruitJuiceBubbles(double height, double width) {
        super("resources/bubbles/blueBubbles.gif");
        setFitHeight(height);
        setFitWidth(width);
    }
}
