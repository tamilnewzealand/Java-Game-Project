package warlords2600;

import warlordstest.IGame;

public class Game implements IGame{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    public General[] generals = new General[2];
    public Ball ball;
    public Brick brick1;

    public Game(Ball ball, General generalA, General generalB, Brick brick) {
        this.ball = ball;
        this.generals[0] = generalA;
        this.generals[1] = generalB;
        this.brick1 = brick;
    }

    public void tick(){
        timeElapsed++;
        boolean ballHit = false;
        int deadCount = 0;

        for (int i = 0; i < generals.length; i++) {
            if (((ball.getXPos() + ball.getWidth() / 2) < (brick1.getXPos() - brick1.getWidth() / 2)) && ((ball.getXPos() + ball.getXVelocity() + ball.getWidth() / 2) > (brick1.getXPos() - brick1.getWidth() / 2))) {
                ball.setXPos(brick1.getXPos() - (ball.getXPos() + ball.getXVelocity() - brick1.getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                ballHit = true;
                brick1.destroyBrick();
            }
            if (((ball.getYPos() + ball.getHeight() / 2) < (brick1.getYPos() - brick1.getHeight() / 2)) && ((ball.getYPos() + ball.getYVelocity() + ball.getHeight() / 2) > (brick1.getYPos() - brick1.getHeight() / 2))) {
                ball.setYPos(brick1.getYPos() - (ball.getYPos() + ball.getYVelocity() - brick1.getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                ballHit = true;
                brick1.destroyBrick();
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

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

}
