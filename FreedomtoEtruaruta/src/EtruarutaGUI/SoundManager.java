package EtruarutaGUI;

import javafx.scene.media.AudioClip;
import java.io.File;

public class SoundManager {

    public static void playCollision() {
        String url = new File("collision.wav").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

    public static void playBackground() {
        String url = new File("background.mp3").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

}