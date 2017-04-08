package EtruarutaGUI;

import javafx.scene.media.AudioClip;
import java.io.File;

/**
 * The class manages the sound and music for the game.
 * Methods in this class are called to play all the
 * sound effects and music necessary for the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class SoundManager {

    /**
     * Plays the collision sound effect.
     */
    public static void playCollision() {
        String url = new File("collision.wav").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

    /**
     * Plays the background music.
     */
    public static void playBackground() {
        String url = new File("background.mp3").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

    /**
     * Plays the speed up sound effect.
     */
    public static void playSpeedUp() {
        String url = new File("167563__benboncan__jet-whoosh.wav").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

    /**
     * Plays the general death sound effect.
     */
    public static void playGeneralDeath() {
        String url = new File("death.wav").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

    /**
     * Plays the explosion sound effect.
     */
    public static void playExplosion() {
        String url = new File("explosion.wav").toURI().toString();
        AudioClip ac = new AudioClip(url);
        ac.play();
    }

}