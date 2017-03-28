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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import warlords2600.*;

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

        Canvas canvas = new Canvas( 1024, 768 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = renderGame(gc);
        HandleInputs();

        return playNowScene;
    }

    public void HandleInputs() {
        playNowScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
             @Override
             public void handle(KeyEvent keyEvent) {
                 switch(keyEvent.getCode()) {
                     case UP:
                         game.generals[0].paddle.goDown();
                         break;
                     case DOWN:
                         game.generals[0].paddle.goUp();
                         break;
                     case LEFT:
                         game.generals[0].paddle.goLeft();
                         break;
                     case RIGHT:
                         game.generals[0].paddle.goRight();
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
        Image paddleImage = new Image( "paddleA.png" );
        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0 );

        Ball ball = new Ball(0,0);
        Paddle paddleA = new Paddle(0,0);
        General generalA = new General(0,0, paddleA);
        Paddle paddleB = new Paddle();
        General generalB = new General(0, 0, paddleB);
        Brick brick = new Brick();
        ball.setXVelocity(10);
        ball.setYVelocity(10);

        game = new Game(ball, generalA, generalB, brick);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.clearRect(0, 0, 1024,768);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( ballImage, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight() );
                gc.drawImage( paddleImage, game.generals[0].paddle.getXPos(), game.generals[0].paddle.getYPos(), game.generals[0].paddle.getWidth(), game.generals[0].paddle.getHeight());
                System.out.println(game.generals[0].paddle.getYPos());
                game.tick();
            }
        }.start();

        return gc;
    }
}