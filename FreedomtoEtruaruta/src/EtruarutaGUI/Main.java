package EtruarutaGUI;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "Freedom to Etruaruta";
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

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
