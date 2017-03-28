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
                     case LEFT:
                         game.generals[0].paddle.moveLeft();
                         break;
                     case RIGHT:
                         game.generals[0].paddle.moveRight();
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
        paddleImages[2] = new Image( "paddleC.png" );
        paddleImages[3] = new Image( "paddleD.png" );

        Image space = new Image( "space.png" );
        gc.drawImage( space, 0, 0, Main.WIDTH, Main.HEIGHT);

        Ball ball = new Ball(Main.WIDTH/2,Main.HEIGHT/2);

        Brick[][] wallA = new Brick[3][5];
        Brick[][] wallB = new Brick[3][5];
        Brick[][] wallC = new Brick[3][5];
        Brick[][] wallD = new Brick[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                wallA[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15);
                wallB[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15);
                wallC[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15);
                wallD[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15);
            }
        }

        Paddle[] paddles = new Paddle[4];
        for (int i = 0; i < 4; i++) {
            paddles[i] = new Paddle();
        }
        General generalA = new General(0, 0, paddles[0], wallA);
        General generalB = new General(0, 0, paddles[1], wallB);
        General generalC = new General(0, 0, paddles[2], wallC);
        General generalD = new General(0, 0, paddles[3], wallD);

        ball.setXVelocity(5);
        ball.setYVelocity(5);

        game = new Game(ball, generalA, generalB, generalC, generalD, wallA[0][0]);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                // background image clears canvas
                gc.drawImage( space, 0, 0 );
                gc.drawImage( ballImage, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight() );
                gc.drawImage( paddleImages[0], game.generals[0].paddle.calcXPos(), game.generals[0].paddle.calcYPos(), game.generals[0].paddle.getWidth(), game.generals[0].paddle.getHeight());
                gc.drawImage( paddleImages[1], Main.WIDTH  - game.generals[1].paddle.calcXPos(), game.generals[1].paddle.calcYPos(), game.generals[1].paddle.getWidth(), game.generals[1].paddle.getHeight());
                gc.drawImage( paddleImages[2], Main.WIDTH - game.generals[2].paddle.calcXPos(), Main.HEIGHT - game.generals[2].paddle.calcYPos(), game.generals[2].paddle.getWidth(), game.generals[2].paddle.getHeight());
                gc.drawImage( paddleImages[3], game.generals[3].paddle.calcXPos(), Main.HEIGHT - game.generals[3].paddle.calcYPos(), game.generals[3].paddle.getWidth(), game.generals[3].paddle.getHeight());
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 5; j++) {
                        gc.drawImage( brickImage, game.generals[0].wall[i][j].calcXPos(), game.generals[0].wall[i][j].calcYPos(), game.generals[0].wall[i][j].getWidth(), game.generals[0].wall[i][j].getHeight());
                        gc.drawImage( brickImage, Main.WIDTH - game.generals[1].wall[i][j].calcXPos(), game.generals[1].wall[i][j].calcYPos(), game.generals[1].wall[i][j].getWidth(), game.generals[1].wall[i][j].getHeight());
                        gc.drawImage( brickImage, Main.WIDTH - game.generals[2].wall[i][j].calcXPos(), Main.HEIGHT - game.generals[2].wall[i][j].calcYPos(), game.generals[2].wall[i][j].getWidth(), game.generals[2].wall[i][j].getHeight());
                        gc.drawImage( brickImage, game.generals[3].wall[i][j].calcXPos(), Main.HEIGHT - game.generals[3].wall[i][j].calcYPos(), game.generals[3].wall[i][j].getWidth(), game.generals[3].wall[i][j].getHeight());
                    }
                }

                game.tick();
            }
        }.start();

        return gc;
    }
}