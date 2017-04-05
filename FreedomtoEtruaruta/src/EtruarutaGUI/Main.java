package EtruarutaGUI;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is necessary for the game to start, the entry point of the GUI.
 *
 * @autho Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */
public class Main extends Application {

    public static final String TITLE = "Freedom to Etruaruta";
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    /**
     * The following table describes the values
     * that gameMode can be set to and what they
     * do.
     *
     * | gameMode: | Description:     |
     * |    0      | Multiplayer      |
     * |    1      | Level 1 story    |
     * |    2      | Level 1          |
     * |    3      | Level 2 story    |
     * |    4      | Level 2          |
     * |    5      | Level 3 story    |
     * |    6      | Level 3          |
     * |    7      | Game end message |
     * |   99      | Demo Mode        |
     */
    public static int gameMode = 0;
    public static String playerName;
    public static int playerScore = 0;
    public static double speedMultiplier = 1.00;
    public static int numberOfBalls = 1;
    public static double numberOfPaddles = 1.00;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.goToMenuScene(sceneManager);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
