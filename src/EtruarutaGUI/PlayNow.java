package EtruarutaGUI;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import warlords2600.*;

/**
 * This class implements the main game. Two paddles and
 * a ball are currently implemented. Left, Right, Up, Down
 * keystrokes are being listened to and they will move the
 * first paddle.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class PlayNow implements SceneInterface {
    private SceneManager sceneManager;
    private Scene playNowScene;
    private Group root;
    private Game game;

    /**
     * Constructor for PlayNow class
     * @param sceneManager SceneManager currently being used
     */
    public PlayNow(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Returns the PlayNow Scene
     */
    @Override
    public Scene init(int width, int height) {
        root = new Group();
        playNowScene = new Scene(root, width, height, Color.ORANGE);

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = renderGame(gc);
        HandleInputs();
        addMenuButton();

        return playNowScene;
    }

    /**
     * Handles keyboard inputs and moves the paddle appropriately.
     */
    public void HandleInputs() {
        playNowScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
             @Override
             public void handle(KeyEvent keyEvent) {
                 switch(keyEvent.getCode()) {
                     case LEFT:
                         game.generals[0].paddle.goLeft();
                         break;
                     case RIGHT:
                         game.generals[0].paddle.goRight();
                         break;
                     case UP:
                         game.generals[0].paddle.goUp();
                         break;
                     case DOWN:
                         game.generals[0].paddle.goDown();
                         break;
                 }
             }

        });
    }

    /**
     *
     * @param gc GraphicsContext to draw animation onto
     * @return GraphicsContext with the animation for the game drawn on to
     */
    public GraphicsContext renderGame(GraphicsContext gc) {
        Image ballImage = new Image( "ball.png" );
        Image paddleImages[] = new Image[4];
        Image brickImage = new Image ( "ball.png" );
        paddleImages[0] = new Image( "paddleA.png" );
        paddleImages[1] = new Image( "paddleB.png" );

        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0, Main.WIDTH, Main.HEIGHT);

        Ball ball = new Ball(10,0);

        Paddle paddleA = new Paddle(Main.WIDTH / 2, Main.HEIGHT  / 4);
        Paddle paddleB = new Paddle(Main.WIDTH / 2, 3 * Main.HEIGHT  / 4);
        General generalA = new General(0, 0, paddleA);
        General generalB = new General(0, 0, paddleB);

        Brick brick = new Brick(0,0);

        ball.setXVelocity(5);
        ball.setYVelocity(5);

        game = new Game(ball, generalA, generalB, brick);

        game.ball.setHeight(25);
        game.ball.setWidth(25);
        game.generals[0].paddle.setSpeed(10);

        game.generals[0].paddle.setHeight(25);
        game.generals[1].paddle.setHeight(25);
        game.generals[0].paddle.setWidth(50);
        game.generals[1].paddle.setWidth(50);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( ballImage, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight() );
                gc.drawImage( paddleImages[0], game.generals[0].paddle.getXPos(), game.generals[0].paddle.getYPos(), game.generals[0].paddle.getWidth(), game.generals[0].paddle.getHeight());
                gc.drawImage( paddleImages[1], game.generals[1].paddle.getXPos(), game.generals[1].paddle.getYPos(), game.generals[1].paddle.getWidth(), game.generals[1].paddle.getHeight());
                game.tick();
            }
        }.start();

        return gc;
    }

    private void addMenuButton() {
        Button menuButton = GUIComponent.createButton("Back to Menu", 30, 540);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sceneManager.goToMenuScene(sceneManager);
            }
        });

        root.getChildren().add(menuButton);
    }
}
