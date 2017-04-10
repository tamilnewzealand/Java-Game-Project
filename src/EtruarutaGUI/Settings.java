package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import java.util.*;
import java.io.*;

/**
 * This class presents the settings scene. The user
 * is presented with the settings for configuring
 * advanced options for playing the game.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Settings implements SceneInterface {
    private SceneManager sceneManager;
    private Scene settingsScene;
    private Group root;
    final Slider ballSpeedSetting = new Slider(1, 2, Main.speedMultiplier);
    final Slider numberOfBallSetting = new Slider(1, 3, Main.numOfBalls);
    final Slider numberOfPaddleSetting = new Slider(1, 2, Main.numberOfPaddles);
    final Slider paddleSizeSetting = new Slider(0.5, 2.5, Main.paddleSize);
    private Button menuButton = GUIComponent.createButton("Back to Menu", 244, 580);
    /**
     * Constructor for Settings class
     * @param sceneManager SceneManager currently being used
     */
    public Settings(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the Settings Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        settingsScene = new Scene(root, width, height, Color.AZURE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0 );
        SoundManager.playBackground();
        
        addMenuButton();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(150, 100, 100, 325));
        grid.setVgap(10);
        grid.setHgap(70);

        final Label ballSpeed = new Label("Ball Speed:");
        final Label numberOfBalls = new Label("Number of Balls:");
        final Label numberOfPaddles = new Label("Number of Paddles:");
        final Label paddleSize = new Label("Paddle Size:");

        try {
            final Font theFont = Font.loadFont(new FileInputStream(new File("Kavivanar.TTF")), 24);
            ballSpeed.setFont(theFont);
            numberOfBalls.setFont(theFont);
            numberOfPaddles.setFont(theFont);
            paddleSize.setFont(theFont);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ballSpeed.setTextFill(Color.WHITE);
        GridPane.setConstraints(ballSpeed, 0, 1);
        grid.getChildren().add(ballSpeed);

        numberOfBalls.setTextFill(Color.WHITE);
        GridPane.setConstraints(numberOfBalls, 0, 2);
        grid.getChildren().add(numberOfBalls);

        numberOfPaddles.setTextFill(Color.WHITE);
        GridPane.setConstraints(numberOfPaddles, 0, 3);
        grid.getChildren().add(numberOfPaddles);

        paddleSize.setTextFill(Color.WHITE);
        GridPane.setConstraints(paddleSize, 0, 4);
        grid.getChildren().add(paddleSize);

        ballSpeedSetting.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
               Main.speedMultiplier = new_val.doubleValue();
               menuButton.requestFocus();
            }
        });

        numberOfBallSetting.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                Main.numOfBalls = new_val.doubleValue();
                Main.numberOfBalls = (int)Math.round(Main.numOfBalls);
                menuButton.requestFocus();
            }
        });

        numberOfPaddleSetting.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                Main.numberOfPaddles = new_val.doubleValue();
                menuButton.requestFocus();
            }
        });

        paddleSizeSetting.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                Main.paddleSize = new_val.doubleValue();
                menuButton.requestFocus();
            }
        });

        GridPane.setConstraints(ballSpeedSetting, 1, 1);
        grid.getChildren().add(ballSpeedSetting);

        GridPane.setConstraints(numberOfBallSetting, 1, 2);
        grid.getChildren().add(numberOfBallSetting);

        GridPane.setConstraints(numberOfPaddleSetting, 1, 3);
        grid.getChildren().add(numberOfPaddleSetting);

        GridPane.setConstraints(paddleSizeSetting, 1, 4);
        grid.getChildren().add(paddleSizeSetting);
        root.getChildren().add(grid);

        addTitle();
        configureSliders();

        return settingsScene;
    }

    private void addTitle() {
        Text titleText = GUIComponent.createText("Settings", 392, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addMenuButton() {

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                settingsScene.setCursor(Cursor.HAND);
            }
        });

        menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                settingsScene.setCursor(Cursor.DEFAULT);
            }
        });

        menuButton.setTextFill(Paint.valueOf("#FF3333"));
        menuButton.defaultButtonProperty().bind(menuButton.focusedProperty());
        root.getChildren().add(menuButton);
    }

    private void configureSliders(){
        ballSpeedSetting.setShowTickLabels(true);

        numberOfBallSetting.setShowTickLabels(true);
        numberOfBallSetting.setMinorTickCount(1);
        numberOfBallSetting.setMajorTickUnit(2);
        numberOfBallSetting.setSnapToTicks(true);

        numberOfPaddleSetting.setShowTickLabels(true);
        numberOfPaddleSetting.setMinorTickCount(1);
        numberOfPaddleSetting.setMajorTickUnit(2);
        numberOfPaddleSetting.setSnapToTicks(true);

        paddleSizeSetting.setShowTickLabels(true);
    }
}