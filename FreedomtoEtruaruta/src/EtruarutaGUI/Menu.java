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
        gc = UIGenerator.createAnimationBackground(gc);

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
        Text titleText = UIGenerator.createText("Freedom to Etruaruta", 256, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addStartButton() {
        Button startButton = UIGenerator.createButton("Play Now (1P)", 426, 180);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToPlayNowScene(sceneManager);
            }
        });

        root.getChildren().add(startButton);
    }

    private void addMultiplayer2Button() {
        Button multi2Button = UIGenerator.createButton("Multiplayer (2P)", 426, 240);

        multi2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(multi2Button);
    }

    private void addMultiplayer4Button() {
        Button multi4Button = UIGenerator.createButton("Multiplayer (4P)", 426, 300);

        multi4Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(multi4Button);
    }

    private void addInstructionsButton() {
        Button instructionsButton = UIGenerator.createButton("Instructions", 426, 360);

        instructionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(instructionsButton);
    }

    private void addDemoButton() {
        Button demoButton = UIGenerator.createButton("Demo", 426, 420);

        demoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
            }
        });

        root.getChildren().add(demoButton);
    }

    private void addHighScoresButton() {
        Button highScoresButton = UIGenerator.createButton("High Scores", 426, 480);

        highScoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToHiScoreScene(sceneManager);
            }
        });

        root.getChildren().add(highScoresButton);
    }

    private void addSettingsButton() {
        Button settingsButton = UIGenerator.createButton("Settings", 426, 540);

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToSettingsScene(sceneManager);
            }
        });

        root.getChildren().add(settingsButton);
    }

    private void addExitButton() {
        Button exitButton = UIGenerator.createButton("Exit", 426, 600);

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