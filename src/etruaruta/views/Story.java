package etruaruta.views;

import etruaruta.Main;
import etruaruta.GUIComponent;
import etruaruta.controllers.SceneManager;
import etruaruta.score.ScoreManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;

/**
 * This class presents the story scene. The user
 * is presented with the story for playing the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class Story implements SceneInterface {
    private SceneManager sceneManager;
    private Scene storyScene;
    private Group root;
    private TextField name;
    private EventHandler<KeyEvent> keyPressHandler;
    private WebView webView = new WebView();
    private Button[] buttonsArray = new Button[2];
    private int optionNumber = 0;

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

        webView.setPrefSize(Main.WIDTH, Main.HEIGHT);
        root.getChildren().add(webView);

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
            name.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == UP){
                        root.requestFocus();
                        colourText(1);
                        optionNumber = 1;
                    }else if (t.getCode() == DOWN){
                        root.requestFocus();
                        optionNumber = 0;
                        colourText(0);
                    }
                }
            });
            grid.getChildren().add(name);
            root.getChildren().add(grid);
        }

        switch (Main.gameMode) {
            case -1:
                addResultsText();
                webView.getEngine().load("https://www.youtube.com/embed/FiZ_9fNrH38?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0&loop=1&playlist=FiZ_9fNrH38");
                Main.playerScore = 0;
                Main.playerName = null;
                break;
            case 1:
                addStoryAText();
                webView.getEngine().load("https://www.youtube.com/embed/bumX659SFlE?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0&loop=1&playlist=bumX659SFlE");
                Main.gameMode = 2;
                addMenuButton();
                break;
            case 3:
                addStoryBText();
                webView.getEngine().load("https://www.youtube.com/embed/cxYr4V3dNT0?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0&loop=1&playlist=cxYr4V3dNT0");
                Main.gameMode = 4;
                addMenuButton();
                break;
            case 5:
                addStoryCText();
                webView.getEngine().load("https://www.youtube.com/embed/Rlh9bfPTBNs?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0&loop=1&playlist=Rlh9bfPTBNs");//Awaiting approval from author
                Main.gameMode = 6;
                addMenuButton();
                break;
            case 7:
                addWinMessageText();
                webView.getEngine().load("https://www.youtube.com/embed/DmJhGD98lP8?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0");
                ScoreManager.addScore(Main.playerName, Main.playerScore);
                Main.playerScore = 0;
                Main.playerName = null;
                break;
            case 8:
                addLostMessageText();
                webView.getEngine().load("https://www.youtube.com/embed/DmJhGD98lP8?autoplay=1&controls=0&disablekb=1&modestbranding=1&rel=0&showinfo=0");
                Main.playerScore = 0;
                Main.playerName = null;
            default:
                break;
        }
        addExitButton();
        handleInputs();
        storyScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
        Main.draw = false;

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
        String text;
        if (Main.draw) text = "You remain undefeated but so do some of your enemies! Better luck next round!\nCurrent Score: " + Main.playerScore;
        else text = "Well Done!\nCurrent Score: " + Main.playerScore;
        text += "\nIt is now 2602, you have been able to maintain a ceasefire with the invaders for the last 30 years and\n" +
                "keep them at bay orbiting the planet Etruaruta. Intelligence reports suggest that they are planning a\n" +
                "new attack against the planet when the citizens are celebrating Republic Day. Are you ready for the\n" +
                "next invasion?";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 22);

        root.getChildren().add(instructionsText);
    }

    private void addStoryCText() {
        String text;
        if (Main.draw) text = "You remain undefeated but so do your enemies! Better luck next round!\nCurrent Score: " + Main.playerScore;
        else text = "Well Done!\nCurrent Score: " + Main.playerScore;
        text += "\nAfter three months of fierce fighting, the Earthlings and Martians have banded together and sent\n" +
                "you into exile in orbit. The locals have been enslaved and you are facing an eminent loss. The locals\n" +
                "are planning a coup to send out the invaders. Will you be able to defeat the invaders and give\n" +
                "Freedom to the people of Etruaruta?";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 22);

        root.getChildren().add(instructionsText);
    }

    private void addWinMessageText() {
        String text = "";
        if (Main.draw) text = "Having been unable to defeat your enemies you negotiate to form\na peace treaty.\n";
        text += Main.playerName + "Well done you have successfully gained freedom for Etruaruta!!!\n" +
                "Your Score: " + Main.playerScore;
        Text instructionsText = GUIComponent.createText(text, 50, 150, 30);

        root.getChildren().add(instructionsText);
    }

    private void addLostMessageText() {
        String text = "You have been defeated. Better luck next time...";
        Text instructionsText = GUIComponent.createText(text, 50, 150, 30);
        root.getChildren().add(instructionsText);
    }

    private void addResultsText() {
        String text = "Player 1 Score: " + Main.playerScore + "\nPlayer 2 Score: " + Main.multiScore;
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
                closeWebView();
                if (Main.gameMode == 2) {
                    Main.playerName = name.textProperty().getValue();
                }
                sceneManager.goToGameScene(sceneManager);
            }
        });

        menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                storyScene.setCursor(Cursor.HAND); //Change cursor to hand
                resetColours();
                optionNumber = 0;
                colourText(0);
            }
        });
        menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                storyScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });
        if (!(Main.gameMode == 7 || Main.gameMode == -1 || Main.gameMode == 8)){
            buttonsArray[0] = menuButton;
            menuButton.setTextFill(Paint.valueOf("#FF3333"));
        }

        root.getChildren().add(menuButton);
    }

    private void addExitButton() {
        Button returnToMenuButton = GUIComponent.createButton("Return to Main Menu", 30, 540);

        returnToMenuButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Main.gameMode = 0;
                storyScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                sceneManager.goToMenuScene(sceneManager);
                closeWebView();
            }
        });

        returnToMenuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                storyScene.setCursor(Cursor.HAND); //Change cursor to hand
                resetColours();
                colourText(1);
                optionNumber = 1;
            }
        });
        returnToMenuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                storyScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });

        if (Main.gameMode == 7 || Main.gameMode == -1 || Main.gameMode == 8){
            returnToMenuButton.setTextFill(Paint.valueOf("#FF3333"));
            buttonsArray[0] = returnToMenuButton;
        } else {
            buttonsArray[1] = returnToMenuButton;
        }
        root.getChildren().add(returnToMenuButton);
    }

    private void handleInputs(){
        keyPressHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()){
                    case ESCAPE:
                        closeWebView();
                        storyScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                        sceneManager.goToMenuScene(sceneManager);
                        Main.gameMode = 0;
                        break;
                    case ENTER:
                        closeWebView();
                        storyScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                        if (Main.gameMode == -1 || Main.gameMode == 7 || Main.gameMode == 8) {
                            sceneManager.goToMenuScene(sceneManager);
                            Main.gameMode = 0;
                        } else if (optionNumber == 0) {
                            if (Main.gameMode == 2) {
                                Main.playerName = name.textProperty().getValue();
                            }
                            sceneManager.goToGameScene(sceneManager);
                        } else if (optionNumber == 1){
                            sceneManager.goToMenuScene(sceneManager);
                            Main.gameMode = 0;
                        }
                        break;
                    case UP:
                        optionChanger(true);
                        break;
                    case DOWN:
                        optionChanger(false);
                        break;
                }
            }
        };
    }

    private void closeWebView(){
        webView.getEngine().load(null); // Set the webview to null so video doesn't play in background
    }

    private void resetColours(){
        if (Main.gameMode != 7 && Main.gameMode != -1) {
            for (int i = 0; i < buttonsArray.length; i++) {
                buttonsArray[i].setTextFill(Paint.valueOf("#FFFFFF"));
            }
        }
    }

    private void colourText(int optionNumber){
        buttonsArray[optionNumber].setTextFill(Paint.valueOf("#FF3333"));
    }

    private void optionChanger(boolean up){
        if (Main.gameMode != 2) {
            if (optionNumber == 0) {
                optionNumber = 1;
            } else {
                optionNumber = 0;
            }
            resetColours();
            colourText(optionNumber);
        }else{
            resetColours();
            if (optionNumber == 0){
                if (up){
                    optionNumber = -1;
                    name.requestFocus();
                }else{
                    optionNumber = 1;
                    colourText(optionNumber);
                }
            }else if (optionNumber == 1) {
                if (up) {
                    optionNumber = 0;
                    colourText(optionNumber);
                } else {
                    name.requestFocus();
                }
            }
        }
    }
}