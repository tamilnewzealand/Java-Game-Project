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

/**
 * This class presents the intro scene and plays
 * the intro video.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */
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
        introScene = new Scene(root, width, height, Color.ORANGE);

        Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
        root.getChildren().add(canvas);

        Media media = new Media("file:/e:/intro.flv");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.play();
        //mediaView.setFitWidth(Main.WIDTH);
        //mediaView.setFitHeight(Main.HEIGHT);

        root.getChildren().add(mediaView);
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