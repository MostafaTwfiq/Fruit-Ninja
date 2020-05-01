package GameTypes.PauseGameMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public final class PauseGameMenu extends HBox {

    private PauseGameButton resumeB;
    private PauseGameButton restartRoundB;
    private PauseGameButton endRoundB;
    private PauseGameButton soundStatusB;

    private boolean isMuted;

    private String choice;

    public PauseGameMenu(boolean isMuted) {
        this.isMuted = isMuted;

        setUpResumeB();
        setUpRestartB();
        setUpEndRoundB();
        setUpSoundStatusB();

        getChildren().addAll(soundStatusB, endRoundB, restartRoundB, resumeB);

        setAlignment(Pos.CENTER);

        setSpacing(10);

        setPadding(new Insets(5, 5, 5, 5));

        styleProperty().set(getLayoutStyle());

        choice = "";
    }

    private String getLayoutStyle() {
        return "-fx-border-color: black; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 20; " +
                "-fx-background-color: #8b4f2a; " +
                "-fx-background-radius: 20; ";
    }

    private void setUpResumeB() {
        resumeB = new PauseGameButton("resources/pauseMenu/end.png");

        resumeB.setButtonAction(e -> {
            this.choice = "resume";
        });

    }

    private void setUpRestartB() {
        restartRoundB = new PauseGameButton("resources/pauseMenu/replay.png");

        restartRoundB.setButtonAction(e -> {
            this.choice = "restart";
        });

    }

    private void setUpEndRoundB() {
        endRoundB = new PauseGameButton("resources/pauseMenu/left.png");

        endRoundB.setButtonAction(e -> {
            this.choice = "end";
        });

    }

    private void setUpSoundStatusB() {
        String imagePath = isMuted ? "resources/pauseMenu/lowVolume.png" : "resources/pauseMenu/highVolume.png";

        soundStatusB = new PauseGameButton(imagePath);

        soundStatusB.setButtonAction(e -> {
            String path = !isMuted ? "resources/pauseMenu/lowVolume.png" : "resources/pauseMenu/highVolume.png";
            soundStatusB.setButtonImage(path);
            isMuted = !isMuted;
        });

    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean isMuted() {
        return isMuted;
    }


}
