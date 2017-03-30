package EtruarutaGUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * The game uses one JavaFX stage, this class handles all scene
 * changes necessary for the game. Provides public methods to
 * switch from one game scene to another.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class SceneManager {

    private Stage stage;
    private Timeline animation;

    /**
     * Constructor for SceneManager class
     * @param primaryStage the Stage that this SceneManager uses
     */
    public SceneManager(Stage primaryStage) {
        this.stage = primaryStage;
        this.animation = new Timeline();
        stage.getIcons().add(new Image("etruaruta.png"));
        stage.show();
    }

    /**
     * Sets the scene to the Menu Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToMenuScene(SceneManager sceneManager) {
        animation.stop();
        Menu menu = new Menu(sceneManager);
        Scene menuScene = menu.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(menuScene);
    }

    /**
     * Sets the scene to the Play Now Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToPlayNowScene(SceneManager sceneManager) {
        animation.stop();
        PlayNow playNow = new PlayNow(sceneManager);
        Scene playNowScene = playNow.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(playNowScene);
    }

    /**
     * Sets the scene to the Instructions Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToInstructionsScene(SceneManager sceneManager) {
        animation.stop();
        Instructions instructions = new Instructions(sceneManager);
        Scene instructionsScene = instructions.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(instructionsScene);
    }
}