package GameTypes.PoppingImage;

import GameTypes.PoppingImage.PoppingImage;

public final class CriticalImage extends PoppingImage {

    public CriticalImage() {
        super("resources/systemImages/critical.png", 50, 100, 200, 1.5);

        setFitHeight(20);
        setFitWidth(40);
    }
}
