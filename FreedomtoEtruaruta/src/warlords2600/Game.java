package warlords2600;

import EtruarutaGUI.SceneManager;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import warlordstest.IGame;

public class Game implements IGame{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    public General[] generals;
    public Ball ball;

    public Game(Ball ball, General generalA, General generalB, Brick brick) {
        this.ball = ball;
        this.generals = new General[2];
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.generals[0].wall[0][0] = brick;
    }

    public Game(Ball ball, General generalA, General generalB, General generalC, General generalD) {
        this.ball = ball;
        this.generals = new General[4];
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.generals[2] = generalC;
        this.generals[3] = generalD;
    }

    public void tick(){
        timeElapsed++;
        boolean ballHit = false;
        int deadCount = 0;
        //System.out.println("Tick");
        if (!ball.getHitLastTick() && ball.getCollisionCounter() <= 0){
            outerLoop:
            for (int i = 0; i < generals.length; i++) {
                for (int x = generals[i].paddle.calcXPos(); x < (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth()); x++) {
                    for (int y = generals[i].paddle.calcYPos(); y < (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight()); y++) {
                        if (x == generals[i].paddle.calcXPos() || y == generals[i].paddle.calcYPos() || x == (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth()) || y == (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                            if (inBallPath(x, y)) {
                                if (x == generals[i].paddle.calcXPos()) {
                                    ball.setYVelocity(-ball.getYVelocity());
                                    System.out.println(ball.getXPos());
                                    ball.setXPos(ball.getXPos() + ball.getXVelocity());
                                    ball.setYPos(ball.getYPos() + ball.getYVelocity());
                                    System.out.println("A , X: " + ball.getXPos() + " Y: " + ball.getYPos() + " Y Velocity: " + ball.getYVelocity() + " X Velocity: " + ball.getXVelocity());
                                    ballHit = true;
                                    ball.setHitLastTick(true);
                                    break outerLoop;
                                } else if (y == generals[i].paddle.calcYPos()) {
                                    ball.setYVelocity(-ball.getYVelocity());
                                    ball.setXPos(ball.getXPos() + ball.getXVelocity());
                                    System.out.println(ball.getYPos());
                                    ball.setYPos(ball.getYPos() + ball.getYVelocity());
                                    System.out.println("B , X: " + ball.getXPos() + " Y: " + ball.getYPos() + " Y Velocity: " + ball.getYVelocity() + " X Velocity: " + ball.getXVelocity());
                                    ball.setHitLastTick(true);
                                    ballHit = true;
                                    break outerLoop;
                                } else if (x == (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth())) {
                                    ball.setYVelocity(-ball.getYVelocity());
                                    //ball.setXPos(ball.getXPos() - ball.getXVelocity());
                                    ball.setYPos(ball.getYPos() - ball.getYVelocity());
                                    System.out.println("C");
                                    ballHit = true;
                                    ball.setHitLastTick(true);
                                    break outerLoop;
                                } else if (y == (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                                    ball.setYVelocity(-ball.getYVelocity());
                                    //ball.setXPos(ball.getXPos() - ball.getXVelocity());
                                    ball.setYPos(ball.getYPos() - ball.getYVelocity());
                                    System.out.println("D");
                                    ballHit = true;
                                    ball.setHitLastTick(true);
                                    break outerLoop;
                                }
                            }
                        }
                    }
                }
                if (generals[i].isDead()) deadCount++;
            }
        }
        if (deadCount + 1 == generals.length) {
            for (int i = 0; i < generals.length; i++) {
                if (!generals[i].isDead()) {
                    generals[i].setWon();
                    isFinished = true;
                }
            }
        }

        if (!ballHit){
            ball.processBall();
            ball.setHitLastTick(false);
            ball.decrementCounter();
        }

        if (timeElapsed > 3600) {
            isFinished = true;
            // need to update this logic to better represent timeout wins
            // rn warlord 0 wins automatically when timeout occurs
            generals[0].setWon();
        }
    }

    /* Bresenham Line Drawing Algorithm */
    private boolean inBallPath (int xCord, int yCord) {
        int x1 = ball.getXPos();
        int x2 = ball.getXPos() + ball.getWidth();
        int y1 = ball.getYPos();
        int y2 = ball.getYPos() + ball.getHeight();
        int x, y, dx, dy, p, end;

        dx = Math.abs(x1 - x2);
        dy = Math.abs(y1 - y2);
        p = 2 * dy - dx;
        if(x1 > x2) {
            x = x2;
            y = y2;
            end = x1;
        }
        else {
            x = x1;
            y = y1;
            end = x2;
        }
        if ((x == xCord) && (y == yCord)) return true;
        while(x < end) {
            x = x + 1;
            if(p < 0) {
                p = p + 2 * dy;
            }
            else {
                y = y + 1;
                p = p + 2 * (dy - dx);
            }
            if ((x == xCord) && (y == yCord)) return true;
        }
        return false;
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

    public void setFinished(boolean finished){
        isFinished = finished;
    }

    public void HandleInputs(Scene playNowScene, SceneManager sceneManager) {
        playNowScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case LEFT:
                        generals[0].paddle.moveLeft();
                        break;
                    case RIGHT:
                        generals[0].paddle.moveRight();
                        break;
                    case ESCAPE:
                        setFinished(true);
                        sceneManager.goToMenuScene(sceneManager);
                        break;
                }
            }

        });
    }
}
