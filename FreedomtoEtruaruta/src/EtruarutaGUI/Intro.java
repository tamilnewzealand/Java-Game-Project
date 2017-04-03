package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Intro implements SceneInterface {
    private SceneManager sceneManager;
    private Scene introScene;
    private Group root;

    /**
     * Constructor for Intro class
     * @param sceneManager SceneManager currently being used
     */
    public Intro(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the Intro Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        introScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
        root.getChildren().add(canvas);

        final MediaView introVid = new MediaView(new MediaPlayer(new Media("intro.flv")));
        root.getChildren().add(introVid);
        addMenuButton();

        addMenuButton();

        return introScene;
    }

    private void addMenuButton() {
        Button menuButton = GUIComponent.createButton("Skip", 244, 580);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }
}