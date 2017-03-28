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

        Canvas canvas = new Canvas( Main.WIDTH, Main.HEIGHT );
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
                         game.generals[0].paddle.goUp();
                         break;
                     case DOWN:
                         game.generals[0].paddle.goDown();
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
        Image paddleAImage = new Image( "paddleA.png" );
        Image paddleBImage = new Image( "paddleB.png" );
        Image paddleCImage = new Image( "paddleC.png" );
        Image paddleDImage = new Image( "paddleD.png" );

        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0, Main.WIDTH, Main.HEIGHT);

        Ball ball = new Ball(50,50);
        Paddle paddleA = new Paddle(50,50);
        General generalA = new General(0,0, paddleA);
        Paddle paddleB = new Paddle(Main.WIDTH - 100, 50);
        General generalB = new General(0, 0, paddleB);
        Paddle paddleC = new Paddle(Main.WIDTH - 100, Main.HEIGHT - 75);
        General generalC = new General(0, 0, paddleC);
        Paddle paddleD = new Paddle(50, Main.HEIGHT - 75);
        General generalD = new General(0, 0, paddleD);
        Brick brick = new Brick();
        ball.setXVelocity(5);
        ball.setYVelocity(0);

        game = new Game(ball, generalA, generalB, generalC, generalD, brick);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( ballImage, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight() );
                gc.drawImage( paddleAImage, game.generals[0].paddle.getXPos(), game.generals[0].paddle.getYPos(), game.generals[0].paddle.getWidth(), game.generals[0].paddle.getHeight());
                gc.drawImage( paddleBImage, game.generals[1].paddle.getXPos(), game.generals[1].paddle.getYPos(), game.generals[1].paddle.getWidth(), game.generals[1].paddle.getHeight());
                gc.drawImage( paddleCImage, game.generals[2].paddle.getXPos(), game.generals[2].paddle.getYPos(), game.generals[2].paddle.getWidth(), game.generals[2].paddle.getHeight());
                gc.drawImage( paddleDImage, game.generals[3].paddle.getXPos(), game.generals[3].paddle.getYPos(), game.generals[3].paddle.getWidth(), game.generals[3].paddle.getHeight());
                game.tick();
            }
        }.start();

        return gc;
    }
}