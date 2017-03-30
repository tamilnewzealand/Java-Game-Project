package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class presents the instructions scene. The user
 * is presented with the instructions for playing the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Instructions implements SceneInterface {
    private SceneManager sceneManager;
    private Scene instructionsScene;
    private Group root;

    /**
     * Constructor for Instructions class
     * @param sceneManager SceneManager currently being used
     */
    public Instructions(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the Instructions Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        instructionsScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = GUIComponent.createAnimationBackground(gc);

        addTitle();
        addInstructionsText();
        addMenuButton();

        return instructionsScene;
    }

    private void addTitle() {
        Text titleText = GUIComponent.createText("Instructions", 50, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addInstructionsText() {
        String text = "Control the top paddle using the following \n" +
                "Press UP to move upwards\n" +
                "Press DOWN to move down\n" +
                "Press LEFT to move left\n" +
                "Press RIGHT to move right\n";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 30);

        root.getChildren().add(instructionsText);
    }

    private void addMenuButton() {
        Button menuButton = GUIComponent.createButton("Back to Menu", 30, 540);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }
}