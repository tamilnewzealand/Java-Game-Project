package EtruarutaGUI;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.Random;
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
    private EventHandler<KeyEvent> handler;
    private boolean paused = false;
    private boolean escaping = false;

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
        if (Main.gameMode == 0) HandleInputsMulti();
        else if (Main.gameMode == 99) HandleTriggers();
        else HandleInputs();

        playNowScene.addEventHandler(KeyEvent.KEY_PRESSED, handler);

        return playNowScene;
    }

    public void HandleInputs() {
        handler = new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean freezed = paused || game.isCountingDown();
                switch(keyEvent.getCode()) {
                    case LEFT:
                        if (!freezed) {
                            if(!game.generals[0].isDead()) {
                                game.generals[0].paddle.moveLeft();
                            }else{
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveLeft();
                                    }
                                }
                            }
                        }
                        break;
                    case RIGHT:
                        if (!freezed) {
                            if(!game.generals[0].isDead()) {
                                game.generals[0].paddle.moveRight();
                            }else{
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveRight();
                                    }
                                }
                            }
                        }
                        break;
                    case UP:
                        if (!freezed) {
                            if(game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveUp();
                                    }
                                }
                            }
                        }
                        break;
                    case DOWN:
                        if (!paused) {
                            if(game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveDown();
                                    }
                                }
                            }
                        }
                        break;
                    case SHIFT:
                        if (!freezed) {
                            if(game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0 && game.markers.get(i).getReady()){
                                        game.generatePowerUp(game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos());
                                        game.markers.get(i).resetReadyCounter();
                                    }
                                }
                            }
                        }
                        break;
                    case ESCAPE:
                        if (paused && escaping) {
                            paused = false;
                            escaping = false;
                        } else{
                            paused = true;
                            escaping = true;
                        }
                        break;
                    case ENTER:
                        if (paused && escaping) {
                            Main.gameMode = 0;
                            game.setFinished(true);
                            playNowScene.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
                            sceneManager.goToMenuScene(sceneManager);
                        }
                        break;
                    case P:
                        if (paused) {
                            paused = false;
                        } else{
                            paused = true;
                        }
                        break;
                }
            }

        };
    }

    public void HandleTriggers() {
        handler = new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean freezed = paused || game.isCountingDown();
                switch(keyEvent.getCode()) {
                    case ESCAPE:
                        if (paused && escaping) {
                            paused = false;
                            escaping = false;
                        } else{
                            paused = true;
                            escaping = true;
                        }
                        break;
                    case ENTER:
                        if (paused && escaping) {
                            Main.gameMode = 0;
                            game.setFinished(true);
                            playNowScene.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
                            sceneManager.goToMenuScene(sceneManager);
                        }
                        break;
                    case P:
                        if (paused) {
                            paused = false;
                        } else{
                            paused = true;
                        }
                        break;
                }
            }

        };
    }

    public void HandleInputsMulti() {
        handler = new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean freezed = paused || game.isCountingDown();
                switch(keyEvent.getCode()) {
                    case LEFT:
                        if (!freezed) {
                            if(!game.generals[2].isDead()) {
                                game.generals[2].paddle.moveLeft();
                            }else{
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveLeft();
                                    }
                                }
                            }
                        }
                        break;
                    case RIGHT:
                        if (!freezed) {
                            if(!game.generals[2].isDead()) {
                                game.generals[2].paddle.moveRight();
                            }else{
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveRight();
                                    }
                                }
                            }
                        }
                        break;
                    case UP:
                        if (!freezed) {
                            if(game.generals[2].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveUp();
                                    }
                                }
                            }
                        }
                        break;
                    case DOWN:
                        if (!freezed) {
                            if(game.generals[2].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveDown();
                                    }
                                }
                            }
                        }
                        break;
                    case SHIFT:
                        if (!freezed) {
                            if(game.generals[2].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0 && game.markers.get(i).getReady()){
                                        game.generatePowerUp(game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos());
                                        game.markers.get(i).resetReadyCounter();
                                    }
                                }
                            }
                        }
                        break;
                    case A:
                        if (!freezed) {
                            if(!game.generals[0].isDead()) {
                                game.generals[0].paddle.moveLeft();
                            }else{
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveLeft();
                                    }
                                }
                            }
                        }
                        break;
                    case D:
                        if (!freezed) {
                            if(!game.generals[0].isDead()) {
                                game.generals[0].paddle.moveRight();
                            }else{
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveRight();
                                    }
                                }
                            }
                        }
                        break;
                    case W:
                        if (!freezed) {
                            if(game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveUp();
                                    }
                                }
                            }
                        }
                        break;
                    case S:
                        if (!freezed) {
                            if(game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0){
                                        game.markers.get(i).moveDown();
                                    }
                                }
                            }
                        }
                        break;
                    case E:
                        if (!freezed) {
                            if(game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size();i++){
                                    if (game.markers.get(i).getPos() == 0 && game.markers.get(i).getReady()){
                                        game.generatePowerUp(game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos());
                                        game.markers.get(i).resetReadyCounter();
                                    }
                                }
                            }
                        }
                        break;
                    case ESCAPE:
                        if (paused && escaping) {
                            paused = false;
                            escaping = false;
                        } else{
                            paused = true;
                            escaping = true;
                        }
                        break;
                    case ENTER:
                        if (paused && escaping) {
                            game.setFinished(true);
                            playNowScene.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
                            sceneManager.goToMenuScene(sceneManager);
                        }
                        break;
                    case P:
                        if (paused) {
                            paused = false;
                        } else{
                            paused = true;
                        }
                        break;
                }
            }

        };
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
        Image markerImages[] = new Image[4];
        Image markerReadyImages[] = new Image[4];

        markerImages[0] = new Image ("xMarkerA.png");
        markerImages[1] = new Image ("xMarkerB.png");
        markerImages[2] = new Image ("xMarkerC.png");
        markerImages[3] = new Image ("xMarkerD.png");

        markerReadyImages[0] = new Image ("xMarkerAReady.png");
        markerReadyImages[1] = new Image ("xMarkerBReady.png");
        markerReadyImages[2] = new Image ("xMarkerCReady.png");
        markerReadyImages[3] = new Image ("xMarkerDReady.png");

        paddleImages[0] = new Image( "paddleA.png" );
        paddleImages[1] = new Image( "paddleB.png" );
        paddleImages[2] = new Image( "paddleC.png" );
        paddleImages[3] = new Image( "paddleD.png" );

        Image space = new Image( "space.png" );
        Image speedImage = new Image ("speedUp.png");
        Image paddleSizeUpImage = new Image ("paddleSizeUp.png");
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

        if (Main.gameMode == 0) game = new Game(ball, generalA, generalB, generalC, generalD, 2);
        else if (Main.gameMode == 99) game = new Game(ball, generalA, generalB, generalC, generalD, 4);
        else game = new Game(ball, generalA, generalB, generalC, generalD, 3);

        SoundManager.playBackground();
        //game.ball.setYPos(game.generals[1].paddle.calcYPos());

        new AnimationTimer()
        {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime)
            {
                if ((currentNanoTime - lastUpdate >= 16666666) && !game.isFinished()) {
                    lastUpdate = currentNanoTime;
                    // Clear the canvas
                    gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                    // background image clears canvas
                    gc.drawImage( space, 0, 0 );

                    for (int i = 0; i < game.powerUps.size(); i++) {
                        if(!game.powerUps.get(i).isHit()) {
                            if (game.powerUps.get(i).getPowerUpName().equals("Speed Up")) {
                                gc.drawImage(speedImage, game.powerUps.get(i).calcXPos(), game.powerUps.get(i).calcYPos(), game.powerUps.get(i).getWidth(), game.powerUps.get(i).getHeight());
                            }else if (game.powerUps.get(i).getPowerUpName().equals("Paddle Size Up")){
                                gc.drawImage(paddleSizeUpImage, game.powerUps.get(i).calcXPos(), game.powerUps.get(i).calcYPos(), game.powerUps.get(i).getWidth(), game.powerUps.get(i).getHeight());
                            }
                        }
                    }

                    if (game.markers.size() > 0) {
                        for (int i = 0; i < game.markers.size(); i++) {
                            if (game.markers.get(i).getReady()){
                                gc.drawImage(markerReadyImages[game.markers.get(i).getPos()], game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos(), game.markers.get(i).getWidth(), game.markers.get(i).getHeight());
                            }else {
                                gc.drawImage(markerImages[game.markers.get(i).getPos()], game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos(), game.markers.get(i).getWidth(), game.markers.get(i).getHeight());
                            }
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

                    if (game.isCountingDown()) {
                        game.countdownTick();
                    }else if (!paused) {
                        game.tick();
                    }

                    if (game.isCountingDown()) gc.fillText(game.getCountdownRemaining(), Main.WIDTH/2, 60);
                    else gc.fillText(game.getTimeRemaining(), Main.WIDTH/2, 60);

                    if (game.getFinished()) {
                        playNowScene.removeEventHandler(KeyEvent.KEY_PRESSED, handler);
                        if (game.generals[0].hasWon()) {
                            switch (Main.gameMode)  {
                                case 2:
                                    Main.gameMode = 3;
                                    sceneManager.goToPlayNowScene(sceneManager);
                                    break;
                                case 4:
                                    Main.gameMode = 5;
                                    sceneManager.goToPlayNowScene(sceneManager);
                                    break;
                                case 6:
                                    Main.gameMode = 7;
                                    sceneManager.goToPlayNowScene(sceneManager);
                                    break;
                                default:
                                    Main.gameMode = 0;
                                    sceneManager.goToMenuScene(sceneManager);
                            }
                        } else {
                            Main.gameMode = 0;
                            sceneManager.goToMenuScene(sceneManager);
                        }
                    }

                    if (paused && !escaping)
                        gc.fillText("Paused", Main.WIDTH/2, Main.HEIGHT/2);
                    }
                    if (paused && escaping) {
                        gc.fillText("Press enter to exit", Main.WIDTH/2, Main.HEIGHT/2);
                    }
                }

        }.start();

        return gc;
    }
}