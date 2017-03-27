package EtruarutaGUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

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
     * Sets the scene to be the Menu Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToMenuScene(SceneManager sceneManager) {
        animation.stop();
        Menu menu = new Menu(sceneManager);
        Scene menuScene = menu.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(menuScene);
    }

    /**
     * Sets the scene to be the Instructions Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToInstructionsScene(SceneManager sceneManager) {
        animation.stop();
        Instructions instructions = new Instructions(sceneManager);
        Scene instructionsScene = instructions.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(instructionsScene);
    }

    /**
     * Sets the scene to be the High Score Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToHiScoreScene(SceneManager sceneManager) {
        animation.stop();
        HiScore hiScore = new HiScore(sceneManager);
        Scene hiScoreScene = hiScore.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(hiScoreScene);
    }

    /**
     * Sets the scene to be the High Score Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToSettingsScene(SceneManager sceneManager) {
        animation.stop();
        Settings settings = new Settings(sceneManager);
        Scene settingsScene = settings.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(settingsScene);
    }


    private void setGameLoop(KeyFrame frame) {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
}