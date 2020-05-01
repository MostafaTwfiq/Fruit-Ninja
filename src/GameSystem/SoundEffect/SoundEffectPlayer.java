package GameSystem.SoundEffect;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public final class SoundEffectPlayer {

    private Clip soundClip;

    public SoundEffectPlayer(String path) {
        loadClip(path);
    }

    /** This method will load the audio from the path parameter.*/
    public void loadClip(String path) {

        try {
            File audioFile = new File("./" + path);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile.toURI().toURL());
            soundClip = AudioSystem.getClip();
            soundClip.open(audioIn);

        } catch (Exception exception) {
            System.out.println("There is something wrong happened while playing audio.");
        }
    }

    /** This method will start the clip (it also will continue it if the clip are paused).*/
    public void playSound() {
        soundClip.start();
    }

    /** This method will pause the clip.*/
    public void pause() {
        soundClip.stop();
    }

    /** This method will reset the clip to be ready to start over.*/
    public void reset() {
        soundClip.setMicrosecondPosition(0);
    }
}
