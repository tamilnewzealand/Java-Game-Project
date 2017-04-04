package EtruarutaGUI;

import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

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
    final Slider opacityLevel = new Slider(0, 1, 1);
    final Label opacityCaption = new Label("Opacity Level:");
    final Label opacityValue = new Label(Double.toString(opacityLevel.getValue()));

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

        Slider sliderA = new Slider(0, 1, 0.5);

        /*GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(70);

        settingsScene.setRoot(grid);

        GridPane.setConstraints(opacityCaption, 0, 1);
        grid.getChildren().add(opacityCaption);


        opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                //System.out.println(new_val.doubleValue());
                opacityValue.setText(String.format("%.2f", new_val));
            }
        });

        GridPane.setConstraints(opacityLevel, 1, 1);
        grid.getChildren().add(opacityLevel);


        GridPane.setConstraints(opacityValue, 2, 1);
        grid.getChildren().add(opacityValue);
        */
        addTitle();
        addMenuButton();

        return settingsScene;
    }

    private void addTitle() {
        Text titleText = GUIComponent.createText("Settings", 392, 90, 54);

        root.getChildren().add(titleText);
    }

    private void addMenuButton() {
        Button menuButton = GUIComponent.createButton("Back to Menu", 244, 580);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }
}