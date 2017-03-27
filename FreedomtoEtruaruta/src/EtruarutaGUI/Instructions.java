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

        Canvas canvas = new Canvas( 1024, 768 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = UIGenerator.createAnimationBackground(gc);

        addTitle();
        addInstructionsText();
        addMenuButton();

        return instructionsScene;
    }

    private void addTitle() {
        Text titleText = UIGenerator.createText("Instructions", 392, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addInstructionsText() {
        String text = "Aim of this project is to implement a pong like game in Java using OOP principles and the\n" +
                "MVC pattern. The game will involve at least one moving ball that can destroy walls, on\n" +
                "higher difficulty settings multiple balls will be in play to make defending your warlord\n" +
                "harder. Players will be able to control a paddle(s) which can be used to reflect the ball.\n" +
                "The objective of the game is to destroy the other warlords while defending your warlord from \n" +
                "enemy fire.\n" +
                "\n" +
                "In the year 2600, four races are fighting over the planet Etruaruta, a planet orbiting around\n" +
                "Alpha Centauri B located 4.37 light-years from Earth in the southern constellation of\n" +
                "Centaurus. The planet once occupied by an un-civilized race called the Etruarutians has now\n" +
                "been found to a be a major source of Qeflinda, a major source of energy that much of the\n" +
                "galaxy is now dependent on. The Galactic Empire, Martians & the Earthlings have just\n" +
                "landed on Etruaruta. The story of who gains control of this now valuable planet forms the\n" +
                "plotline of this game.\n";
        Text instructionsText = UIGenerator.createText(text, 232, 150, 15);

        root.getChildren().add(instructionsText);
    }

    private void addMenuButton() {
        Button menuButton = UIGenerator.createButton("Back to Menu", 244, 580);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }
}