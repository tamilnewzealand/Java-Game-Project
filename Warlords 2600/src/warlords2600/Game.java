package warlords2600;

import warlordstest.IGame;

public class Game implements IGame{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    private Warlord[] warlords = new Warlord[2];
    public Ball ball;
    public Brick brick1;

    public Game(Ball ball, Warlord warlordA, Warlord warlordB, Brick brick) {
        this.ball = ball;
        this.warlords[0] = warlordA;
        this.warlords[1] = warlordB;
        this.brick1 = brick;
    }

    public void tick(){
        timeElapsed++;
        boolean ballHit = false;
        int deadCount = 0;

        for (int i = 0; i < warlords.length; i++) {
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
            if (((ball.getXPos() + ball.getWidth() / 2) < (warlords[i].paddle.getXPos() - warlords[i].paddle.getWidth() / 2)) && ((ball.getXPos() + ball.getXVelocity() + ball.getWidth() / 2) > (warlords[i].paddle.getXPos() - warlords[i].paddle.getWidth() / 2))) {
                ball.setXPos(warlords[i].paddle.getXPos() - (ball.getXPos() + ball.getXVelocity() - warlords[i].paddle.getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                ballHit = true;
            }
            if (((ball.getYPos() + ball.getHeight() / 2) < (warlords[i].paddle.getYPos() - warlords[i].paddle.getHeight() / 2)) && ((ball.getYPos() + ball.getYVelocity() + ball.getHeight() / 2) > (warlords[i].paddle.getYPos() - warlords[i].paddle.getHeight() / 2))) {
                ball.setYPos(warlords[i].paddle.getYPos() - (ball.getYPos() + ball.getYVelocity() - warlords[i].paddle.getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                ballHit = true;
            }
            if (((ball.getXPos() + ball.getWidth() / 2) < (warlords[i].getXPos() - warlords[i].getWidth() / 2)) && ((ball.getXPos() + ball.getXVelocity() + ball.getWidth() / 2) > (warlords[i].getXPos() - warlords[i].getWidth() / 2))) {
                ball.setXPos(warlords[i].getXPos() - (ball.getXPos() + ball.getXVelocity() - warlords[i].getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                ballHit = true;
                warlords[i].killWarlord();
            }
            if (((ball.getYPos() + ball.getHeight() / 2) < (warlords[i].getYPos() - warlords[i].getHeight() / 2)) && ((ball.getYPos() + ball.getYVelocity() + ball.getHeight() / 2) > (warlords[i].getYPos() - warlords[i].getHeight() / 2))) {
                ball.setYPos(warlords[i].getYPos() - (ball.getYPos() + ball.getYVelocity() - warlords[i].getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                ballHit = true;
                warlords[i].killWarlord();
            }

            if (warlords[i].isDead()) deadCount++;
        }

        if (deadCount + 1 == warlords.length) {
            for (int i = 0; i < warlords.length; i++) {
                if (!warlords[i].isDead()) {
                    warlords[i].setWon();
                    isFinished = true;
                }
            }
        }

        if (!ballHit) ball.processBall();

        if (timeElapsed > 3600) {
            isFinished = true;
            // need to update this logic to better represent timeout wins
            // rn warlord 0 wins automatically when timeout occurs
            warlords[0].setWon();
        }
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

}
