package warlords2600;

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

        for (int i = 0; i < 1; i++) {
            if (ball.getXVelocity() > 0) {
                if (ball.getYVelocity() == 0) {
                    boolean yIntersects = false;
                    for (int j = ball.getYPos(); j < (ball.getYPos() + ball.getHeight()); j++) {
                        for (int k = generals[i].paddle.getYPos(); k < (generals[i].paddle.getYPos() + generals[i].paddle.getHeight()); k++) {
                            if (j == k) yIntersects = true;
                        }
                    }
                    if (yIntersects && ((ball.getXPos() + ball.getWidth()) < generals[i].paddle.calcXPos())) {
                        if ((ball.getXPos() + ball.getXVelocity() + ball.getWidth()) >= generals[i].paddle.calcXPos()) {
                            ball.setXPos(generals[i].paddle.calcXPos() - ball.getWidth());
                            ball.setXVelocity(-ball.getXVelocity());
                            ballHit = true;
                        }
                    }
                }
                if (ball.getYVelocity() > 0) {
                    if ((ball.getXPos() + ball.getWidth()) < generals[i].paddle.calcXPos()) {
                        if ((ball.getYPos() + ball.getHeight()) < generals[i].paddle.calcYPos()) {
                            if ((ball.getXPos() + ball.getXVelocity() + ball.getWidth()) >= generals[i].paddle.calcXPos()) {
                                if ((ball.getYPos() + ball.getYVelocity() + ball.getHeight()) >= generals[i].paddle.calcYPos()) {
                                    ball.setXPos(generals[i].paddle.calcXPos() - ball.getWidth());
                                    ball.setYPos(generals[i].paddle.calcYPos() - ball.getHeight());
                                    ball.setXVelocity(-ball.getXVelocity());
                                    ball.setYVelocity(-ball.getYVelocity());
                                    ballHit = true;
                                }
                            }
                        }
                    }
                }
                if (ball.getYVelocity() < 0) {
                    if ((ball.getXPos() + ball.getWidth()) < generals[i].paddle.calcXPos()) {
                        if (ball.getYPos() > (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                            if ((ball.getXPos() + ball.getXVelocity() + ball.getWidth()) >= generals[i].paddle.calcXPos()) {
                                if ((ball.getYPos() + ball.getYVelocity()) <= (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                                    ball.setXPos(generals[i].paddle.calcXPos() - ball.getWidth());
                                    ball.setYPos(generals[i].paddle.calcYPos() - ball.getHeight());
                                    ball.setXVelocity(-ball.getXVelocity());
                                    ball.setYVelocity(-ball.getYVelocity());
                                    ballHit = true;
                                }
                            }
                        }
                    }
                }
            }
            if (ball.getXVelocity() < 0) {
                if (ball.getYVelocity() == 0) {
                    boolean yIntersects = false;
                    for (int j = ball.getYPos(); j < (ball.getYPos() + ball.getHeight()); j++) {
                        for (int k = generals[i].paddle.getYPos(); k < (generals[i].paddle.getYPos() + generals[i].paddle.getHeight()); k++) {
                            if (j == k) yIntersects = true;
                        }
                    }
                    if (yIntersects && (ball.getXPos() > (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth()))) {
                        if ((ball.getXPos() + ball.getXVelocity()) <= (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth())) {
                            ball.setXPos(generals[i].paddle.calcXPos() + generals[i].paddle.getWidth());
                            ball.setXVelocity(-ball.getXVelocity());
                            ballHit = true;
                        }
                    }
                }
                if (ball.getYVelocity() > 0) {
                    if (ball.getXPos() > (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth())) {
                        if ((ball.getYPos() + ball.getHeight()) < generals[i].paddle.calcYPos()) {
                            if ((ball.getXPos() + ball.getXVelocity()) <= (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth())) {
                                if ((ball.getYPos() + ball.getYVelocity() + ball.getHeight()) >= generals[i].paddle.calcYPos()) {
                                    ball.setXPos(generals[i].paddle.calcXPos() + generals[i].paddle.getWidth());
                                    ball.setYPos(generals[i].paddle.calcYPos() - ball.getHeight());
                                    ball.setXVelocity(-ball.getXVelocity());
                                    ball.setYVelocity(-ball.getYVelocity());
                                    ballHit = true;
                                }
                            }
                        }
                    }
                }
                if (ball.getYVelocity() < 0) {
                    if (ball.getXPos() > (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth())) {
                        if (ball.getYPos() > (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                            if ((ball.getXPos() + ball.getXVelocity()) <= (generals[i].paddle.calcXPos() + generals[i].paddle.getWidth())) {
                                if ((ball.getYPos() + ball.getYVelocity()) <= (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                                    ball.setXPos(generals[i].paddle.calcXPos() - ball.getWidth());
                                    ball.setYPos(generals[i].paddle.calcYPos() - ball.getHeight());
                                    ball.setXVelocity(-ball.getXVelocity());
                                    ball.setYVelocity(-ball.getYVelocity());
                                    ballHit = true;
                                }
                            }
                        }
                    }
                }
            }
            if (ball.getXVelocity() == 0) {
                if (ball.getYVelocity() > 0) {
                    boolean xIntersects = false;
                    for (int j = ball.getXPos(); j < (ball.getXPos() + ball.getWidth()); j++) {
                        for (int k = generals[i].paddle.getXPos(); k < (generals[i].paddle.getXPos() + generals[i].paddle.getWidth()); k++) {
                            if (j == k) xIntersects = true;
                        }
                    }
                    if (xIntersects && ((ball.getYPos() + ball.getHeight()) < generals[i].paddle.calcYPos())) {
                        if ((ball.getYPos() + ball.getYVelocity() + ball.getHeight()) >= generals[i].paddle.calcYPos()) {
                            ball.setYPos(generals[i].paddle.calcYPos() - ball.getHeight());
                            ball.setYVelocity(-ball.getYVelocity());
                            ballHit = true;
                        }
                    }
                }
                if (ball.getYVelocity() < 0) {
                    boolean xIntersects = false;
                    for (int j = ball.getXPos(); j < (ball.getXPos() + ball.getWidth()); j++) {
                        for (int k = generals[i].paddle.getXPos(); k < (generals[i].paddle.getXPos() + generals[i].paddle.getWidth()); k++) {
                            if (j == k) xIntersects = true;
                        }
                    }
                    if (xIntersects && (ball.getYPos() > (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight()))) {
                        if ((ball.getYPos() + ball.getYVelocity()) <= (generals[i].paddle.calcYPos() + generals[i].paddle.getHeight())) {
                            ball.setYPos(generals[i].paddle.calcYPos() + generals[i].paddle.getHeight());
                            ball.setYVelocity(-ball.getYVelocity());
                            ballHit = true;
                        }
                    }
                }
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

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

}
