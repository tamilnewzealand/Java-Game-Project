package etruaruta.views;

import etruaruta.Main;
import etruaruta.controllers.Game;
import etruaruta.controllers.SceneManager;
import etruaruta.controllers.SoundManager;
import etruaruta.models.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

import java.io.*;

/**
 * This class implements the main game. Two paddles and
 * a ball are currently implemented. Left, Right, Up, Down
 * keystrokes are being listened to and they will move the
 * first paddle.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.5.0
 */

public class PlayNow implements SceneInterface {
    private SceneManager sceneManager;
    private Scene playNowScene;
    private Group root;
    private Game game;
    private EventHandler<KeyEvent> keyPressHandler;
    private EventHandler<KeyEvent> keyReleaseHandler;
    private boolean paused = false;
    private boolean escaping = false;
    private Image arrowPointer = new Image ("arrowPointer.png");
    private ImageView[] arrows = new ImageView[3];

    /**
     * Constructor for PlayNow class
     *
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

        Canvas canvas = new Canvas(Main.WIDTH, Main.HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc = renderGame(gc);
        HandleInputs();

        playNowScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
        playNowScene.addEventHandler(KeyEvent.KEY_RELEASED, keyReleaseHandler);

        for (int i = 0; i < arrows.length; i++){
            arrows[i] = new ImageView();
            arrows[i].setImage(arrowPointer);
            arrows[i].setVisible(false);
            root.getChildren().add(arrows[i]);
        }

        return playNowScene;
    }

    /**
     * Handles the keyboard input for the game
     */
    private void HandleInputs() {
        keyPressHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean freezed = paused || game.isCountingDown();
                switch (keyEvent.getCode()) {
                    case LEFT:
                        if (!freezed && Main.gameMode != 99 ) {
                            if (!game.generals[0].paddle.isHoldingBall()) {
                                if (Main.numberOfPaddles > 1.50){
                                    if (!game.generals[0].paddleFollower.isHoldingBall()){
                                        game.generalsMovement[0] = "left";
                                    }else{
                                        game.increaseBallHoldAngle();
                                    }
                                }else{
                                    game.generalsMovement[0] = "left";
                                }
                            }else{
                                game.increaseBallHoldAngle();
                            }
                        }
                        break;
                    case RIGHT:
                        if (!freezed && Main.gameMode != 99) {
                            if (!game.generals[0].paddle.isHoldingBall()) {
                                if (Main.numberOfPaddles > 1.50){
                                    if (!game.generals[0].paddleFollower.isHoldingBall()){
                                        game.generalsMovement[0] = "right";
                                    }else{
                                        game.decreaseBallHoldAngle();
                                    }
                                }else{
                                    game.generalsMovement[0] = "right";
                                }
                            }else{
                                game.decreaseBallHoldAngle();
                            }
                        }
                        break;
                    case UP:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[0].isDead()) {
                                game.generalsMovement[0] = "up";
                            }else{
                                if (game.isSinglePlayer()) {
                                    game.generals[0].increaseSkillsIndex();
                                }
                            }
                        }
                        break;
                    case DOWN:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[0].isDead()){
                                game.generalsMovement[0] = "down";
                            }else{
                                if (game.isSinglePlayer()) {
                                    game.generals[0].decreaseSkillsIndex();
                                }
                            }
                        }
                        break;
                    case SHIFT:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[0].isDead()) {
                                for (int i = 0; i < game.markers.size(); i++) {
                                    if (game.markers.get(i).getPos() == 0 && game.markers.get(i).getReady()) {
                                        game.generatePowerUp(game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos());
                                        game.markers.get(i).resetReadyCounter();
                                    }
                                }
                            }else{
                                if(!game.generals[0].isSkillTriggered() && game.isSinglePlayer()){ //If the skill has not been triggered
                                    game.generals[0].triggerSkill(game.balls); // Trigger the currently selected skill
                                    game.skillUsedAtleastOnce = true;
                                }
                            }
                        }
                        break;
                    case ESCAPE:
                        if (paused && escaping) {
                            paused = false;
                            escaping = false;
                        } else {
                            paused = true;
                            escaping = true;
                        }
                        break;
                    case ENTER:
                        if (paused && escaping) {
                            Main.gameMode = 0;
                            game.setFinished();
                            playNowScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
                            playNowScene.removeEventHandler(KeyEvent.KEY_RELEASED, keyReleaseHandler);
                            sceneManager.goToMenuScene(sceneManager);
                        }
                        break;
                    case PAGE_DOWN:
                        endGame();
                        break;
                    case P:
                        if (paused) {
                            paused = false;
                        } else {
                            paused = true;
                        }
                        break;
                    //The following code is for player at the bottom right (index 2)
                    case A:
                        if (!freezed && Main.gameMode != 99 && Main.gameMode == 0){
                            game.generalsMovement[2] = "left";
                        }
                        break;
                    case D:
                        if (!freezed && Main.gameMode != 99 && Main.gameMode == 0){
                            game.generalsMovement[2] = "right";
                        }
                        break;
                    case W:
                        if (!freezed && Main.gameMode != 99 && Main.gameMode == 0) {
                            if (game.generals[2].isDead()) game.generalsMovement[2] = "up";
                        }
                        break;
                    case S:
                        if (!freezed && Main.gameMode != 99 && Main.gameMode == 0) {
                            if (game.generals[2].isDead()) game.generalsMovement[2] = "down";
                        }
                        break;
                    case E:
                        if (!freezed && Main.gameMode != 99 && Main.gameMode  == 0) {
                            if (game.generals[2].isDead()) {
                                for (int i = 0; i < game.markers.size(); i++) {
                                    if (game.markers.get(i).getPos() == 2 && game.markers.get(i).getReady()) {
                                        game.generatePowerUp(game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos());
                                        game.markers.get(i).resetReadyCounter();
                                    }
                                }
                            }
                        }
                        break;
                }
            }

        };

        keyReleaseHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                boolean freezed = paused || game.isCountingDown();
                switch (keyEvent.getCode()) {
                    case LEFT:
                        if (!freezed && Main.gameMode != 99) {
                            game.generalsMovement[0] = "";
                        }
                        break;
                    case RIGHT:
                        if (!freezed && Main.gameMode != 99) {
                            game.generalsMovement[0] = "";
                        }
                        break;
                    case UP:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[0].isDead()) game.generalsMovement[0] = "";
                        }
                        break;
                    case DOWN:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[0].isDead()) game.generalsMovement[0] = "";
                        }
                        break;
                    case A:
                        if (!freezed && Main.gameMode != 99) {
                            game.generalsMovement[2] = "";
                        }
                        break;
                    case D:
                        if (!freezed && Main.gameMode != 99) {
                            game.generalsMovement[2] = "";
                        }
                        break;
                    case W:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[2].isDead()) game.generalsMovement[2] = "";
                        }
                        break;
                    case S:
                        if (!freezed && Main.gameMode != 99) {
                            if (game.generals[2].isDead()) game.generalsMovement[2] = "";
                        }
                        break;
                }
            }

        };
    }


    /**
     * @param gc GraphicsContext to draw animation onto
     * @return GraphicsContext with the animation for the game drawn on to
     */
    public GraphicsContext renderGame(GraphicsContext gc) {
        //Loading the graphics
        Image space = new Image("space.png");
        Image ballImage = new Image("ball.png");
        Image spedUpBall = new Image("fastBall.png");
        Image brickImage = new Image("brick.png");
        Image generalImage = new Image("general.png");
        Image speedImage = new Image("speedUp.png");
        Image paddleSizeUpImage = new Image("paddleSizeUp.png");
        Image explosiveSkill = new Image("explosiveSkill.png");
        Image explosiveSkillCoolDown = new Image("explosiveSkillCoolDown.png");
        Image explosiveBall = new Image ("explosiveBall.png");
        Image ballHoldSkill = new Image ("ballHoldSkill.png");
        Image ballHoldSkillCoolDown = new Image ("ballHoldSkillCoolDown.png");

        Image paddleImages[] = {new Image("paddleA.png"), new Image("paddleB.png"), new Image("paddleC.png"), new Image("paddleD.png")};
        Image markerImages[] = {new Image("xMarkerA.png"), new Image("xMarkerB.png"), new Image("xMarkerC.png"), new Image("xMarkerD.png")};
        Image markerReadyImages[] = {new Image("xMarkerAReady.png"), new Image("xMarkerBReady.png"), new Image("xMarkerCReady.png"), new Image("xMarkerDReady.png")};

        // setting up the font for drawing text onto the canvas
        try {
            final Font theFont = Font.loadFont(new FileInputStream(new File("Kavivanar.TTF")), 54);
            gc.setFill(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(theFont);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }        

        initGame();
        SoundManager.playBackground();

        new AnimationTimer() {
            private long lastUpdate = 0;

            public void handle(long currentNanoTime) {
                // Sets the rendering speed to 60fps
                if ((currentNanoTime - lastUpdate >= 16666666) && !game.isFinished()) {
                    lastUpdate = currentNanoTime;
                    // Clear the canvas
                    gc.clearRect(0, 0, Main.WIDTH, Main.WIDTH);

                    // background image clears canvas
                    gc.drawImage(space, 0, 0, Main.WIDTH, Main.HEIGHT);

                    // draws out all the powerups that are in play at the moment
                    for (int i = 0; i < game.powerUps.size(); i++) {
                        if (!game.powerUps.get(i).isHit()) {
                            if (game.powerUps.get(i).getPowerUpName().equals("Speed Up"))
                                gc.drawImage(speedImage, game.powerUps.get(i).calcXPos(), game.powerUps.get(i).calcYPos(), game.powerUps.get(i).getWidth(), game.powerUps.get(i).getHeight());
                            else if (game.powerUps.get(i).getPowerUpName().equals("Paddle Size Up"))
                                gc.drawImage(paddleSizeUpImage, game.powerUps.get(i).calcXPos(), game.powerUps.get(i).calcYPos(), game.powerUps.get(i).getWidth(), game.powerUps.get(i).getHeight());
                        }
                    }

                    // draws out all the ghost markers for any dead players
                    if (game.markers.size() > 0) {
                        for (int i = 0; i < game.markers.size(); i++) {
                            if (game.markers.get(i).getReady()) gc.drawImage(markerReadyImages[game.markers.get(i).getPos()], game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos(), game.markers.get(i).getWidth(), game.markers.get(i).getHeight());
                            else gc.drawImage(markerImages[game.markers.get(i).getPos()], game.markers.get(i).calcXPos(), game.markers.get(i).calcYPos(), game.markers.get(i).getWidth(), game.markers.get(i).getHeight());
                        }
                    }

                    // draws out all the balls and sped up balls in the game canvas
                    for (int a = 0; a < game.balls.length; a++) {
                        if (game.balls[a].isExplosive()) gc.drawImage(explosiveBall, game.balls[a].getXPos(), game.balls[a].getYPos(), game.balls[a].getWidth(), game.balls[a].getHeight());
                        else if (!game.balls[a].getSpedUp()) gc.drawImage(ballImage, game.balls[a].getXPos(), game.balls[a].getYPos(), game.balls[a].getWidth(), game.balls[a].getHeight());
                        else gc.drawImage(spedUpBall, game.balls[a].getXPos(), game.balls[a].getYPos(), game.balls[a].getWidth(), game.balls[a].getHeight());
                    }

                    // draws out each generals' paddles, general and walls
                    for (int k = 0; k < game.generals.length; k++) {
                        if (!game.generals[k].isDead()) {
                            gc.drawImage(paddleImages[k], game.generals[k].paddle.calcXPos(), game.generals[k].paddle.calcYPos(), game.generals[k].paddle.getWidth(), game.generals[k].paddle.getHeight());
                            if (Main.numberOfPaddles > 1.50)
                                gc.drawImage(paddleImages[k], game.generals[k].paddleFollower.calcXPos(), game.generals[k].paddleFollower.calcYPos(), game.generals[k].paddleFollower.getWidth(), game.generals[k].paddleFollower.getHeight());
                        }
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (!game.generals[k].wall[i][j].isDestroyed())
                                    gc.drawImage(brickImage, game.generals[k].wall[i][j].calcXPos(), game.generals[k].wall[i][j].calcYPos(), game.generals[k].wall[i][j].getWidth(), game.generals[k].wall[i][j].getHeight());
                            }
                        }
                        if (!game.generals[k].isDead())
                            gc.drawImage(generalImage, game.generals[k].calcXPos(), game.generals[k].calcYPos(), game.generals[k].getWidth(), game.generals[k].getHeight());
                    }

                    // check if game is counting down or paused or in play and ticks the appropriate counter
                    if (game.isCountingDown()) game.countdownTick();
                    else if (!paused) game.tick();

                    // shows the remaining time in game or if counting down the time till game starts
                    if (game.isCountingDown()) gc.fillText(game.getCountdownRemaining(), Main.WIDTH / 2, 60);
                    else gc.fillText(game.getTimeRemaining(), Main.WIDTH / 2, 60);

                    // calls the end game method to handle the end of a game
                    if (game.isFinished()) endGame();

                    // Shows the paused text overlay if the game is paused
                    if (paused && !escaping) gc.fillText("Paused", Main.WIDTH / 2, Main.HEIGHT / 2);

                    // Shows the conformation prompt when a player tries to leave the game
                    if (paused && escaping) gc.fillText("Press enter to exit", Main.WIDTH / 2, Main.HEIGHT / 2);

                    // Shows the skill ladder and skills that are being used when in single player mode
                    if (game.isSinglePlayer()) {
                        if (game.generals[0].getCurrentSkill().getSkillName() == "Explosive Ball" && !game.generals[0].isDead()) {
                            if (!game.generals[0].isSkillTriggered()) {
                                gc.drawImage(explosiveSkill, 215, 5, game.generals[0].getCurrentSkill().getWidth(), game.generals[0].getCurrentSkill().getHeight());
                            } else {
                                gc.drawImage(explosiveSkillCoolDown, 215, 5, game.generals[0].getCurrentSkill().getWidth(), game.generals[0].getCurrentSkill().getHeight());
                            }
                        } else if (game.generals[0].getCurrentSkill().getSkillName() == "Ball Hold" && !game.generals[0].isDead()) {
                            if (!game.generals[0].isSkillTriggered()) {
                                gc.drawImage(ballHoldSkill, 215, 5, game.generals[0].getCurrentSkill().getWidth(), game.generals[0].getCurrentSkill().getHeight());
                            } else {
                                gc.drawImage(ballHoldSkillCoolDown, 215, 5, game.generals[0].getCurrentSkill().getWidth(), game.generals[0].getCurrentSkill().getHeight());
                            }
                        }
                    }
                    boolean paddleFollowerHolding = ((Main.numberOfPaddles > 1.50) && (game.generals[0].paddleFollower.isHoldingBall()));

                    // Shows the ball being held by a paddle if the hold skill has been activated
                    if ((game.generals[0].paddle.isHoldingBall() || paddleFollowerHolding) && !game.generals[0].isDead() && game.isSinglePlayer()){
                        arrows[game.arrowsIndex].getTransforms().add(new Rotate(-game.previousAngle, game.getArrowXPivot() , game.getArrowYPivot()));

                        if (game.generals[0].paddle.isHoldingBall()) game.calculateArrowPosition(true);
                        else game.calculateArrowPosition(false);

                        arrows[game.arrowsIndex].setX(game.getArrowXPosition());
                        arrows[game.arrowsIndex].setY(game.getArrowYPosition());

                        arrows[game.arrowsIndex].setVisible(true);
                        arrows[game.arrowsIndex].getTransforms().add(new Rotate(game.getBallHoldAngle(), game.getArrowXPivot(), game.getArrowYPivot()));
                        game.previousAngle = game.getBallHoldAngle();
                    } else {
                        for (int i = 0; i < game.arrowsIndex+1; i ++) {
                            arrows[i].setVisible(false);
                        }
                    }
                }
            }

        }.start();

        return gc;
    }

    /**
     * Instantiates all the game objects necessary for this game
     */
    private void initGame() {
        Brick[][] wallA = new Brick[3][5];
        Brick[][] wallB = new Brick[3][5];
        Brick[][] wallC = new Brick[3][5];
        Brick[][] wallD = new Brick[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                wallA[i][j] = new Brick(100 + 35 * i, 0.15 + 0.317699082 * j, 0);
                wallB[i][j] = new Brick(100 + 35 * i, 0.15 + 0.317699082 * j, 1);
                wallC[i][j] = new Brick(100 + 35 * i, 0.15 + 0.317699082 * j, 2);
                wallD[i][j] = new Brick(100 + 35 * i, 0.15 + 0.317699082 * j, 3);
            }
        }

        Paddle[] paddles = new Paddle[4];
        Paddle[] paddleFollowers = new Paddle[4];
        for (int i = 0; i < 4; i++) {
            paddles[i] = new Paddle(Math.PI / 4, i);
            paddleFollowers[i] = new Paddle(Math.PI / 4, i);
            paddles[i].setHeight((int) (paddles[i].getHeight() * Main.paddleSize));
            paddles[i].setWidth((int) (paddles[i].getWidth() * Main.paddleSize));
            paddleFollowers[i].setHeight((int) (paddleFollowers[i].getHeight() * Main.paddleSize));
            paddleFollowers[i].setWidth((int) (paddleFollowers[i].getWidth() * Main.paddleSize));
        }

        General generalA, generalB, generalC, generalD;

        if (Main.numberOfPaddles > 1.50) {
            generalA = new General(50, Math.PI / 4, paddles[0], paddleFollowers[0], wallA, 0);
            generalB = new General(50, Math.PI / 4, paddles[1], paddleFollowers[1], wallB, 1);
            generalC = new General(50, Math.PI / 4, paddles[2], paddleFollowers[2], wallC, 2);
            generalD = new General(50, Math.PI / 4, paddles[3], paddleFollowers[3], wallD, 3);
        } else {
            generalA = new General(50, Math.PI / 4, paddles[0], wallA, 0);
            generalB = new General(50, Math.PI / 4, paddles[1], wallB, 1);
            generalC = new General(50, Math.PI / 4, paddles[2], wallC, 2);
            generalD = new General(50, Math.PI / 4, paddles[3], wallD, 3);
        }

        Ball[] balls = new Ball[Main.numberOfBalls];

        for (int a = 0; a < Main.numberOfBalls; a++) {
            balls[a] = new Ball(Main.WIDTH / 2, Main.HEIGHT / 2);

            double randomX = Math.random() * 10 - 5; //Random speed generation
            double randomY = Math.random() * 10 - 5;

            if (randomX > 0) { //Speed boost for ball
                randomX += 6;
            } else {
                randomX -= 6;
            }

            if (randomY > 0) {
                randomY += 6;
            } else {
                randomY -= 6;
            }

            balls[a].setXVelocity((int) (Main.speedMultiplier * randomX));
            balls[a].setYVelocity((int) (Main.speedMultiplier * randomY));
        }

        if (Main.gameMode == 0) game = new Game(balls, generalA, generalB, generalC, generalD);
        else if (Main.gameMode == 99) game = new Game(balls, generalA, generalB, generalC, generalD);
        else game = new Game(balls, generalA, generalB, generalC, generalD);
    }

    /**
     * Handles the end of the game. Sets the game mode to the
     * appropriate value and changes the scene to the appropriate scene.
     */
    private void endGame() {
        playNowScene.removeEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
        playNowScene.removeEventHandler(KeyEvent.KEY_RELEASED, keyReleaseHandler);
        game.setFinished();
        if (true || game.generals[0].hasWon()) {
            switch (Main.gameMode) {
                case 0:
                    Main.gameMode = -1;
                    Main.playerScore = (game.generals[0].wallCount() * 10);
                    Main.multiScore = (game.generals[1].wallCount() * 10);
                    sceneManager.goToPlayNowScene(sceneManager);
                    break;
                case 2:
                    Main.gameMode = 3;
                    Main.playerScore += (game.generals[0].wallCount() * 10);
                    sceneManager.goToPlayNowScene(sceneManager);
                    break;
                case 4:
                    Main.gameMode = 5;
                    Main.playerScore += (game.generals[0].wallCount() * 10);
                    sceneManager.goToPlayNowScene(sceneManager);
                    break;
                case 6:
                    Main.gameMode = 7;
                    Main.playerScore += (game.generals[0].wallCount() * 10);
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
}
