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
 * This class presents the credits scene. The user
 * is presented with the credits for the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Credits implements SceneInterface {
    private SceneManager sceneManager;
    private Scene creditsScene;
    private Group root;

    /**
     * Constructor for Credits class
     * @param sceneManager SceneManager currently being used
     */
    public Credits(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the Instructions Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        creditsScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = GUIComponent.createAnimationBackground(gc);

        addTitle();
        addCreditsText();
        addMenuButton();

        return creditsScene;
    }

    private void addTitle() {
        Text titleText = GUIComponent.createText("Credits", 365, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addCreditsText() {
        String text = "167563__benboncan__jet-whoosh.wav - Benboncan \n" + "https://www.freesound.org/people/Benboncan/sounds/167563/ - No changes made \n\n" +
                "stock footage scifi spaceship jupiter flyby 2 - \nhttps://www.youtube.com/watch?v=bumX659SFlE \n\n" +
                "stock footage spaceship Goliath class heavy freighter departs Earth - \nhttps://www.youtube.com/watch?v=cxYr4V3dNT0 \n\n" +
                "Spaceship Battle in Atmosphere - https://www.youtube.com/watch?v=Rlh9bfPTBNs - Approval \nreceived from author \n\n";

        Text instructionsText = GUIComponent.createText(text, 50, 150, 24);

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
        menuButton.setTextFill(Paint.valueOf("#FF3333"));
        menuButton.defaultButtonProperty().bind(menuButton.focusedProperty());
        root.getChildren().add(menuButton);
    }
}