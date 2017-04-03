package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class implements the menu screen. When the game is
 * loaded the user is presented with this menu screen from
 * which they can start game or see the instructions.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Menu implements SceneInterface {
    private SceneManager sceneManager;
    private Scene menuScene;
    private Group root;

    /**
     * Constructor for Menu class
     * @param sceneManager SceneManager currently being used
     */
    public Menu(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the Menu Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        menuScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = GUIComponent.createAnimationBackground(gc);
        SoundManager.playBackground();

        addTitle();
        addStartButton();
        addMultiplayer2Button();
        addMultiplayer4Button();
        addInstructionsButton();
        addDemoButton();
        addHighScoresButton();
        addSettingsButton();
        addExitButton();

        return menuScene;
    }


    private void addTitle() {
        Text titleText = GUIComponent.createText("Freedom to Etruaruta", 256, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addStartButton() {
        Button startButton = GUIComponent.createButton("Play Now (1P)", 426, 180);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToPlayNowScene(sceneManager);
            }
        });

        root.getChildren().add(startButton);
    }

    private void addMultiplayer2Button() {
        Button multi2Button = GUIComponent.createButton("Multiplayer (2P)", 426, 240);

        multi2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(multi2Button);
    }

    private void addMultiplayer4Button() {
        Button multi4Button = GUIComponent.createButton("Multiplayer (4P)", 426, 300);

        multi4Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(multi4Button);
    }

    private void addInstructionsButton() {
        Button instructionsButton = GUIComponent.createButton("Instructions", 426, 360);

        instructionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(instructionsButton);
    }

    private void addDemoButton() {
        Button demoButton = GUIComponent.createButton("Demo", 426, 420);

        demoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(demoButton);
    }

    private void addHighScoresButton() {
        Button highScoresButton = GUIComponent.createButton("High Scores", 426, 480);

        highScoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToHiScoreScene(sceneManager);
            }
        });

        root.getChildren().add(highScoresButton);
    }

    private void addSettingsButton() {
        Button settingsButton = GUIComponent.createButton("Settings", 426, 540);

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToSettingsScene(sceneManager);
            }
        });

        root.getChildren().add(settingsButton);
    }

    private void addExitButton() {
        Button exitButton = GUIComponent.createButton("Exit", 426, 600);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });

        root.getChildren().add(exitButton);
    }
}