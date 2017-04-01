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
        gc = UIGenerator.createAnimationBackground(gc);
        SoundManager.playBackground();

        addTitle();
        addHiScoreText();
        addMenuButton();

        return hiScoreScene;
    }

    private void addTitle() {
        Text titleText = UIGenerator.createText("High Scores", 392, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addHiScoreText() {
        String text = "Pos: Player Name: Score:  \n" +
                " 1) RANDOM PLAYER 150\n" +
                " 2) RANDOM PLAYER 140\n" +
                " 3) RANDOM PLAYER 130\n" +
                " 4) RANDOM PLAYER 120\n" +
                " 5) RANDOM PLAYER 110\n" +
                " 6) RANDOM PLAYER 100\n" +
                " 7) RANDOM PLAYER  90\n" +
                " 8) RANDOM PLAYER  80\n" +
                " 9) RANDOM PLAYER  70\n" +
                "10) RANDOM PLAYER  60";
        Text instructionsText = UIGenerator.createText(text, 353, 150, 26);

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