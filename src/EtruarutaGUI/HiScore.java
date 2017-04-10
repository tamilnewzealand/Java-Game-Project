package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * This class presents the High Score scene. The user
 * is presented with the list of high scores for the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */
public class HiScore implements SceneInterface {
    private SceneManager sceneManager;
    private Scene hiScoreScene;
    private Group root;

    /**
     * Constructor for HiScore class
     * @param sceneManager SceneManager currently being used
     */
    public HiScore(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the HiScore Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        hiScoreScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = GUIComponent.createAnimationBackground(gc);
        SoundManager.playBackground();

        addTitle();
        addHiScoreText();
        addMenuButton();

        return hiScoreScene;
    }

    private void addTitle() {
        Text titleText = GUIComponent.createText("High Scores", 392, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addHiScoreText() {
        String text = ScoreManager.getHighscoreString();
        Text instructionsText = GUIComponent.createText(text, 353, 150, 26);

        root.getChildren().add(instructionsText);
    }

    private void addMenuButton() {
        Button menuButton = GUIComponent.createButton("Back to Menu", 244, 580);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });
        menuButton.setTextFill(Paint.valueOf("#FF3333"));
        menuButton.defaultButtonProperty().bind(menuButton.focusedProperty());
        root.getChildren().add(menuButton);
    }
}