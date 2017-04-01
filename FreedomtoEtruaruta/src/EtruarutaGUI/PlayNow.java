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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import warlords2600.*;

import java.util.Random;

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
        game.HandleInputs(playNowScene,sceneManager);

        return playNowScene;
    }

    /**
     *
     * @param gc GraphicsContext to draw animation onto
     * @return GraphicsContext with the animation for the game drawn on to
     */
    public GraphicsContext renderGame(GraphicsContext gc) {
        Image ballImage = new Image( "ball.png" );
        Image spedUpBall = new Image( "fastBall.png");
        Image paddleImages[] = new Image[4];
        Image brickImage = new Image ( "brick.png" );
        Image generalImage = new Image ( "general.png" );
        paddleImages[0] = new Image( "paddleA.png" );
        paddleImages[1] = new Image( "paddleB.png" );
        paddleImages[2] = new Image( "paddleC.png" );
        paddleImages[3] = new Image( "paddleD.png" );

        Image space = new Image( "space.png" );
        Image speedImage = new Image ("speedUp.png");
        gc.drawImage( space, 0, 0, Main.WIDTH, Main.HEIGHT);

        Ball ball = new Ball(Main.WIDTH/2,Main.HEIGHT/2);

        Brick[][] wallA = new Brick[3][5];
        Brick[][] wallB = new Brick[3][5];
        Brick[][] wallC = new Brick[3][5];
        Brick[][] wallD = new Brick[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                wallA[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15, 0);
                wallB[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15, 1);
                wallC[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15, 2);
                wallD[i][j] = new Brick(100 + 35*i, 0.3*(j + 1) - 0.15, 3);
            }
        }

        Paddle[] paddles = new Paddle[4];
        for (int i = 0; i < 4; i++) {
            paddles[i] = new Paddle(Math.PI/4, i);
        }
        General generalA = new General(50, Math.PI/4, paddles[0], wallA, 0);
        General generalB = new General(50, Math.PI/4, paddles[1], wallB, 1);
        General generalC = new General(50, Math.PI/4, paddles[2], wallC, 2);
        General generalD = new General(50, Math.PI/4, paddles[3], wallD, 3);

        double randomX = Math.random() * 10 - 5;//Random speed generation
        double randomY = Math.random() * 10 - 5;

        if (randomX > 0){ //Speed boost for ball
            randomX += 8;
        }
        else{
            randomX -= 8;
        }

        if (randomY > 0){
            randomY += 8;
        }
        else{
            randomY -= 8;
        }

        ball.setXVelocity((int)randomX);
        ball.setYVelocity((int)randomY);

        gc.setFill( Color.WHITE );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Kavivanar", 54 );
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont( theFont );

        game = new Game(ball, generalA, generalB, generalC, generalD);

        SoundManager.playBackground();
        //game.ball.setYPos(game.generals[1].paddle.calcYPos());

        new AnimationTimer()
        {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime)
            {
                if (currentNanoTime - lastUpdate >= 16666666) {
                    lastUpdate = currentNanoTime;
                    // Clear the canvas
                    gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                    // background image clears canvas
                    gc.drawImage( space, 0, 0 );

                    for (int i = 0; i < game.speedUps.size(); i++) {
                        if(!game.speedUps.get(i).isHit()) {
                            gc.drawImage(speedImage, game.speedUps.get(i).calcXPos(), game.speedUps.get(i).calcYPos(), game.speedUps.get(i).getWidth(), game.speedUps.get(i).getHeight());
                        }
                    }
                    if (!game.ball.getSpedUp()) {
                        gc.drawImage(ballImage, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight());
                    }else{
                        gc.drawImage(spedUpBall, game.ball.getXPos(), game.ball.getYPos(), game.ball.getWidth(), game.ball.getHeight());

                    }
                    for (int k = 0; k < game.generals.length; k++) {
                        if (!game.generals[k].isDead()) gc.drawImage( paddleImages[k], game.generals[k].paddle.calcXPos(), game.generals[k].paddle.calcYPos(), game.generals[k].paddle.getWidth(), game.generals[k].paddle.getHeight());
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (!game.generals[k].wall[i][j].isDestroyed()) gc.drawImage( brickImage, game.generals[k].wall[i][j].calcXPos(), game.generals[k].wall[i][j].calcYPos(), game.generals[k].wall[i][j].getWidth(), game.generals[k].wall[i][j].getHeight());
                            }
                        }
                        if (!game.generals[k].isDead()) gc.drawImage(generalImage, game.generals[k].calcXPos(), game.generals[k].calcYPos(), game.generals[k].getWidth(), game.generals[k].getHeight());
                    }


                    game.tick();

                    gc.fillText(game.getTimeRemaining(), Main.WIDTH/2, 60);

                    if (game.getFinished()) {
                        sceneManager.goToMenuScene(sceneManager);
                    }

                    if (game.getPaused())
                        gc.fillText("Paused", Main.WIDTH/2, Main.HEIGHT/2);
                    }
                }

        }.start();

        return gc;
    }
}