package warlords2600;

import warlordstest.IGame;

public class Game implements IGame{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    private Warlord[] warlords = new Warlord[4];
    public Ball ball;

    public Game(Ball ball, Warlord warlordA, Warlord warlordB, Warlord warlordC, Warlord warlordD) {
        this.ball = ball;
        this.warlords[0] = warlordA;
        this.warlords[1] = warlordB;
        this.warlords[2] = warlordC;
        this.warlords[3] = warlordD;
    }

    public void tick(){
        timeElapsed++;
        boolean hitPaddleX = false, hitPaddleY = false;

        for (int i = 0; i < 1; i++) {
            if ((ball.getXPos() < warlords[i].paddle.getXPos()) && ((ball.getXPos() + ball.getXVelocity()) > warlords[i].paddle.getXPos())) {
                ball.setXPos(warlords[i].paddle.getXPos() - (ball.getXPos() + ball.getXVelocity() - warlords[i].paddle.getXPos()));
                ball.setXVelocity(-ball.getXVelocity());
                hitPaddleX = true;
            }
            if ((ball.getYPos() < warlords[i].paddle.getYPos()) && ((ball.getYPos() + ball.getYVelocity()) > warlords[i].paddle.getYPos())) {
                ball.setYPos(warlords[i].paddle.getYPos() - (ball.getYPos() + ball.getYVelocity() - warlords[i].paddle.getYPos()));
                ball.setYVelocity(-ball.getYVelocity());
                hitPaddleY = true;
            }
        }
        if (!hitPaddleX) ball.processBallX();
        if (!hitPaddleY) ball.processBallY();

        if (timeElapsed > 3600) {
            isFinished = true;
            // need to update this logic to better represent timeout wins
            // rn warlord 0 wins automatically when timeout occurs
            warlords[0].setWon(true);
        }
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (120 - seconds) * 60;
    }

}
