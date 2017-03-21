package warlords2600;

import warlordstest.IGame;

/**
 * Created by adilb on 21/03/2017.
 */
public class Game implements IGame{

    private boolean isFinished = false;
    private int timeElapsed = 0;
    Warlord warlordA = new Warlord();


    public void tick(){
        timeElapsed++;
    }

    public boolean isFinished(){
        return isFinished;
    }

    public void setTimeRemaining(int seconds){
        timeElapsed = (3600 - timeElapsed)/60;
    }

}
