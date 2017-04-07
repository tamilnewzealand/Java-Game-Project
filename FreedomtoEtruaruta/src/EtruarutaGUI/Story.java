package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

/**
 * This class presents the story scene. The user
 * is presented with the story for playing the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Story implements SceneInterface {
    private SceneManager sceneManager;
    private Scene storyScene;
    private Group root;
    private TextField name;
    private EventHandler<KeyEvent> keyPressHandler;

    /**
     * Constructor for Story class
     * @param sceneManager SceneManager currently being used
     */
    public Story(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the Story Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        storyScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = GUIComponent.createAnimationBackground(gc);

        WebView webview = new WebView();
        webview.setPrefSize(Main.WIDTH, Main.HEIGHT);
        root.getChildren().add(webview);

        if (Main.gameMode == 1) {
            //Creating a GridPane container
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(300, 100, 100, 450));
            grid.setVgap(50);
            grid.setHgap(50);
            //Defining the Name text field
            name = new TextField();
            name.setPromptText("Enter your first name.");
            name.setPrefColumnCount(10);
            name.getText();
            GridPane.setConstraints(name, 0, 0);
            grid.getChildren().add(name);
            root.getChildren().add(grid);
        }

        switch (Main.gameMode) {
            case 1:
                addStoryAText();
                webview.getEngine().load("https://www.youtube.com/embed/DmJhGD98lP8?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0");
                Main.gameMode = 2;
                addMenuButton();
                break;
            case 3:
                addStoryBText();
                webview.getEngine().load("https://www.youtube.com/embed/DmJhGD98lP8?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0");
                Main.gameMode = 4;
                addMenuButton();
                break;
            case 5:
                addStoryCText();
                webview.getEngine().load("https://www.youtube.com/embed/DmJhGD98lP8?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0");
                Main.gameMode = 6;
                addMenuButton();
                break;
            case 7:
                addWinMessageText();
                webview.getEngine().load("https://www.youtube.com/embed/DmJhGD98lP8?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0");
                ScoreManager.addScore(Main.playerName, Main.playerScore);
                Main.gameMode = 0;
                break;
            default:
                break;
        }
        addExitButton();
        handleInputs();
        storyScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);

        return storyScene;
    }

    private void addStoryAText() {
        String text = "In 2572, an ancient alien source of Qeflinda was discovered on the surface of Etruaruta. Qeflinda is\n" +
                "also known as an elixir of immortality and grants the drinker eternal life. The Earthlings and\n" +
                "Martians have decided to colonize this planet to claim this Qeflinda. As a general of the Galactic\n" +
                "Empire, will you be able to defend the invasion? Please enter your name below:";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 22);

        root.getChildren().add(instructionsText);
    }

    private void addStoryBText() {
        String text = "It is now 2602, you have been able to maintain a ceasefire with the invaders for the last 30 years and\n" +
                "keep them at bay orbiting the planet Etruaruta. Intelligence reports suggest that they are planning a\n" +
                "new attack against the planet when the citizens are celebrating Republic Day. Are you ready for the\n" +
                "next invasion?";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 22);

        root.getChildren().add(instructionsText);
    }

    private void addStoryCText() {
        String text = "After three months of fierce fighting, the Earthlings and Martians have banded together and sent\n" +
                "you into exile in orbit. The locals have been enslaved and you are facing an eminent loss. The locals\n" +
                "are planning a coup to send out the invaders. Will you be able to defeat the invaders and give\n" +
                "Freedom to the people of Etruaruta?";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 22);

        root.getChildren().add(instructionsText);
    }

    private void addWinMessageText() {
        String text = "Well Done " + Main.playerName + ", you have successfully gained freedom for Etruaruta!!!\n" +
                "Your Score: " + Main.playerScore;
        Text instructionsText = GUIComponent.createText(text, 50, 150, 30);

        root.getChildren().add(instructionsText);
    }

    private void addMenuButton() {
        Button menuButton;
        if (Main.gameMode > 3) menuButton = GUIComponent.createButton("Continue Game", 30, 480);
        else menuButton = GUIComponent.createButton("Start Game", 30, 480);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                storyScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                if (Main.gameMode == 2) {
                    Main.playerName = name.textProperty().getValue();
                    if (!((Main.playerName == null) ||(Main.playerName == ""))) {
                        sceneManager.goToGameScene(sceneManager);
                    }
                } else sceneManager.goToGameScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }

    private void addExitButton() {
        Button menuButton = GUIComponent.createButton("Return to Main Menu", 30, 540);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.gameMode = 0;
                storyScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }

    private void handleInputs(){
        keyPressHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                storyScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    sceneManager.goToMenuScene(sceneManager);
                } else if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (Main.gameMode == 2) {
                        Main.playerName = name.textProperty().getValue();
                        if (!((Main.playerName == null) ||(Main.playerName == ""))) {
                            sceneManager.goToGameScene(sceneManager);
                        }
                    } else sceneManager.goToGameScene(sceneManager);
                }

            }
        };
    }
}