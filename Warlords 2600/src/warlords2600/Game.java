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
        ball.processBall();
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (3600 - timeElapsed)/60;
    }

}
