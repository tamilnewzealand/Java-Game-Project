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
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

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
