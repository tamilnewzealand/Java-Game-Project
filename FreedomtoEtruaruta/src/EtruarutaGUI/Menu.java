package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;

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
    private EventHandler<KeyEvent> keyPressHandler;
    private int optionNumber = 0;
    private Button[] buttonsArray = new Button[7];

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
        addInstructionsButton();
        addDemoButton();
        addHighScoresButton();
        addSettingsButton();
        addExitButton();

        handleInputs();
        menuScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);

        return menuScene;
    }


    private void addTitle() {
        Text titleText = GUIComponent.createText("Freedom to Etruaruta", 256, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addStartButton() {
        Button startButton = GUIComponent.createButton("Play Now (1P)", 426, 180);
        startButton.setTextFill(Paint.valueOf("#FF3333"));
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToPlayNowScene(sceneManager);
                menuScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
            }
        });
        startButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 0;
                resetColours();
                colourText(0);
            }
        });
        startButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });
        buttonsArray[0] = startButton;
        root.getChildren().add(startButton);
    }

    private void addMultiplayer2Button() {
        Button multi2Button = GUIComponent.createButton("Multiplayer (2P)", 426, 240);

        multi2Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMultiplayerScene(sceneManager);
                menuScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
            }
        });

        multi2Button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 1;
                resetColours();
                colourText(1);
            }
        });
        multi2Button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });

        buttonsArray[1] = multi2Button;
        root.getChildren().add(multi2Button);
    }

    private void addInstructionsButton() {
        Button instructionsButton = GUIComponent.createButton("Instructions", 426, 300);

        instructionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToInstructionsScene(sceneManager);
                menuScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
            }
        });

        instructionsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 2;
                resetColours();
                colourText(2);
            }
        });
        instructionsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });

        buttonsArray[2] = instructionsButton;
        root.getChildren().add(instructionsButton);
    }

    private void addDemoButton() {
        Button demoButton = GUIComponent.createButton("Demo", 426, 360);

        demoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToDemoScene(sceneManager);
                menuScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
            }
        });

        demoButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 3;
                resetColours();
                colourText(3);
            }
        });
        demoButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });

        buttonsArray[3] = demoButton;
        root.getChildren().add(demoButton);
    }

    private void addHighScoresButton() {
        Button highScoresButton = GUIComponent.createButton("High Scores", 426, 420);

        highScoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToHiScoreScene(sceneManager);
                menuScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
            }
        });

        highScoresButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 4;
                resetColours();
                colourText(4);
            }
        });
        highScoresButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });

        buttonsArray[4] = highScoresButton;
        root.getChildren().add(highScoresButton);
    }

    private void addSettingsButton() {
        Button settingsButton = GUIComponent.createButton("Settings", 426, 480);

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToSettingsScene(sceneManager);
                menuScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
            }
        });

        settingsButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 5;
                resetColours();
                colourText(5);
            }
        });
        settingsButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });

        buttonsArray[5] = settingsButton;
        root.getChildren().add(settingsButton);
    }

    private void addExitButton() {
        Button exitButton = GUIComponent.createButton("Exit", 426, 540);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            }
        });

        exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.HAND); //Change cursor to hand
                optionNumber = 6;
                resetColours();
                colourText(6);
            }
        });
        exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                menuScene.setCursor(Cursor.DEFAULT); //Change cursor to default
            }
        });
        buttonsArray[6] = exitButton;
        root.getChildren().add(exitButton);
    }

    private void handleInputs(){
        keyPressHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP:
                        if (optionNumber > 0) {
                            optionNumber--;
                        } else {
                            optionNumber = 6;
                        }
                        resetColours();
                        colourText(optionNumber);
                        break;
                    case DOWN:
                        if (optionNumber < 6) {
                            optionNumber++;
                        } else {
                            optionNumber = 0;
                        }
                        resetColours();
                        colourText(optionNumber);
                        break;
                    case ENTER:
                        buttonsArray[optionNumber].fire();
                }
            }
        };
    }

    private void resetColours(){
        for (int i = 0; i < buttonsArray.length; i++){
            buttonsArray[i].setTextFill(Paint.valueOf("#FFFFFF"));
        }
    }

    private void colourText(int index){
        buttonsArray[index].setTextFill(Paint.valueOf("#FF3333"));
    }

}