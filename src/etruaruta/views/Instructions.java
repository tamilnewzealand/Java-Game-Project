package etruaruta.views;

import etruaruta.Main;
import etruaruta.GUIComponent;
import etruaruta.controllers.SceneManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * This class presents the instructions scene. The user
 * is presented with the instructions for playing the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
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
        Text titleText = GUIComponent.createText("Instructions", 365, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addInstructionsText() {
        String text = "Single Player Controls:\n" +
                "Press LEFT/RIGHT to move\n" +
                "Press UP/DOWN to change skill or move when ghost\n" +
                "Press Shift to activate skill or generate power-up when ghost\n" +
                "Multiplayer Controls:\n" +
                "Player one:\nUnchanged\n"+
                "Player two:\n"+
                "Press A/D to move\n" +
                "Press W/S to move when ghost\n" +
                "Press E to activate power-up";

        Text instructionsText = GUIComponent.createText(text, 50, 150, 30);
        root.getChildren().add(instructionsText);
    }

    private void addMenuButton() {
        Button menuButton = GUIComponent.createButton("Back to Menu", 30, 590);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                instructionsScene.setCursor(Cursor.HAND);
            }
        });

        menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                instructionsScene.setCursor(Cursor.DEFAULT);
            }
        });

        menuButton.setTextFill(Paint.valueOf("#FF3333"));
        menuButton.defaultButtonProperty().bind(menuButton.focusedProperty());
        root.getChildren().add(menuButton);
    }
}