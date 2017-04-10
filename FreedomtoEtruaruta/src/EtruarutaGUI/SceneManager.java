package EtruarutaGUI;

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
     * Sets the scene to be the intro Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToIntroScene(SceneManager sceneManager) {
        animation.stop();
        Intro intro = new Intro(sceneManager);
        Scene introScene = intro.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(introScene);
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
     * Sets the scene to be the Play Now Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToGameScene(SceneManager sceneManager) {
        animation.stop();
        PlayNow playNow = new PlayNow(sceneManager);
        Scene playNowScene = playNow.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(playNowScene);
    }

    /**
     * Sets the scene to be the Play Now Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToPlayNowScene(SceneManager sceneManager) {
        if (Main.gameMode == 0) Main.gameMode = 1;
        animation.stop();
        Story story = new Story(sceneManager);
        Scene storyScene = story.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(storyScene);
    }

    /**
     * Sets the scene to be the Multiplayer Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToMultiplayerScene(SceneManager sceneManager) {
        Main.gameMode = 0;
        animation.stop();
        PlayNow playNow = new PlayNow(sceneManager);
        Scene playNowScene = playNow.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(playNowScene);
    }

    /**
     * Sets the scene to be the Demo Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToDemoScene(SceneManager sceneManager) {
        Main.gameMode = 99;
        animation.stop();
        PlayNow playNow = new PlayNow(sceneManager);
        Scene playNowScene = playNow.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(playNowScene);
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
     * Sets the scene to be the Credits Scene
     * @param sceneManager SceneManager currently being used
     */
    public void goToCreditsScene(SceneManager sceneManager) {
        animation.stop();
        Credits credits = new Credits(sceneManager);
        Scene creditsScene = credits.init(Main.WIDTH, Main.HEIGHT);
        stage.setScene(creditsScene);
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
}