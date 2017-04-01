package warlords2600;

import warlordstest.IGame;
import EtruarutaGUI.SceneManager;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import EtruarutaGUI.PlayNow;

/**
 * This is the controller class that processes all
 * the game logic and algorithms.
 *
 * @author Adil Bhayani <abha808@aucklanduni.ac.nz>
 * @author Sakayan Sitsabesan <ssit662@aucklanduni.ac.nz>
 * @version 0.1.0
 */

public class Game implements IGame{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    public General[] generals = new General[2];
    public Ball ball;
    public Brick brick1;

    /**
     * Constructor for the Game class controller
     * @param ball Instance of ball in play
     * @param generalA Instance of the first general
     * @param generalB Instance of the second general
     * @param brick Instance of a brick in play
     */
    public Game(Ball ball, General generalA, General generalB, Brick brick) {
        this.ball = ball;
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.brick1 = brick;
    }

    /**
     * Process the ball movements in one game tick, handles
     * all collisions and appropriately moves the ball,
     * declares a player as the winner, destroys walls,
     * kills generals, and handles game timeout.
     */
    public void tick(){
        timeElapsed++;
        boolean ballHit = false;
        int deadCount = 0;

        for (int i = 0; i < generals.length; i++) {
            if (((ball.getXPos() + ball.getWidth() / 2) < (brick1.getXPos() - brick1.getWidth() / 2)) && ((ball.getXPos() + ball.getXVelocity() + ball.getWidth() / 2) > (brick1.getXPos() - brick1.getWidth() / 2))) {
                ball.setXPos(brick1.getXPos() - (ball.getXPos() + ball.getXVelocity() - brick1.getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                ballHit = true;
                brick1.destroyWall();
            }
            if (((ball.getYPos() + ball.getHeight() / 2) < (brick1.getYPos() - brick1.getHeight() / 2)) && ((ball.getYPos() + ball.getYVelocity() + ball.getHeight() / 2) > (brick1.getYPos() - brick1.getHeight() / 2))) {
                ball.setYPos(brick1.getYPos() - (ball.getYPos() + ball.getYVelocity() - brick1.getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                ballHit = true;
                brick1.destroyWall();
            }
            if (((ball.getXPos() + ball.getWidth() / 2) < (generals[i].paddle.getXPos() - generals[i].paddle.getWidth() / 2)) && ((ball.getXPos() + ball.getXVelocity() + ball.getWidth() / 2) > (generals[i].paddle.getXPos() - generals[i].paddle.getWidth() / 2))) {
                ball.setXPos(generals[i].paddle.getXPos() - (ball.getXPos() + ball.getXVelocity() - generals[i].paddle.getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                ballHit = true;
            }
            if (((ball.getYPos() + ball.getHeight() / 2) < (generals[i].paddle.getYPos() - generals[i].paddle.getHeight() / 2)) && ((ball.getYPos() + ball.getYVelocity() + ball.getHeight() / 2) > (generals[i].paddle.getYPos() - generals[i].paddle.getHeight() / 2))) {
                ball.setYPos(generals[i].paddle.getYPos() - (ball.getYPos() + ball.getYVelocity() - generals[i].paddle.getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                ballHit = true;
            }
            if (((ball.getXPos() + ball.getWidth() / 2) < (generals[i].getXPos() - generals[i].getWidth() / 2)) && ((ball.getXPos() + ball.getXVelocity() + ball.getWidth() / 2) > (generals[i].getXPos() - generals[i].getWidth() / 2))) {
                ball.setXPos(generals[i].getXPos() - (ball.getXPos() + ball.getXVelocity() - generals[i].getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                ballHit = true;
                generals[i].killGeneral();
            }
            if (((ball.getYPos() + ball.getHeight() / 2) < (generals[i].getYPos() - generals[i].getHeight() / 2)) && ((ball.getYPos() + ball.getYVelocity() + ball.getHeight() / 2) > (generals[i].getYPos() - generals[i].getHeight() / 2))) {
                ball.setYPos(generals[i].getYPos() - (ball.getYPos() + ball.getYVelocity() - generals[i].getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                ballHit = true;
                generals[i].killGeneral();
            }

            if (generals[i].isDead()) deadCount++;
        }

        if (deadCount + 1 == generals.length) {
            for (int i = 0; i < generals.length; i++) {
                if (!generals[i].isDead()) {
                    generals[i].setWon();
                    isFinished = true;
                }
            }
        }

        if (!ballHit) ball.processBall();

        if (timeElapsed > 3600) {
            isFinished = true;
            // need to update this logic to better represent timeout wins
            // rn warlord 0 wins automatically when timeout occurs
            generals[0].setWon();
        }
    }

    /**
     *
     * @return whether the game has finished
     */
    public boolean isFinished(){
        return isFinished;
    }

    /**
     *
     * @param seconds Time to end of the game in seconds
     */
    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

    /**
     * Handles keyboard inputs and moves the paddle appropriately.
     */
    public void HandleInputs(Scene playNowScene, SceneManager sceneManager) {
        playNowScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
             @Override
             public void handle(KeyEvent keyEvent) {
                 switch(keyEvent.getCode()) {
                     case LEFT:
                         generals[0].paddle.goLeft();
                         break;
                     case RIGHT:
                         generals[0].paddle.goRight();
                         break;
                     case UP:
                         generals[0].paddle.goUp();
                         break;
                     case DOWN:
                         generals[0].paddle.goDown();
                         break;
                 }
             }

        });
    }

}
