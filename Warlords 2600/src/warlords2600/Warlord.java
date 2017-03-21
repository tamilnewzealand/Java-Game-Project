package warlords2600;

import warlordstest.IWarlord;

public class Warlord implements IWarlord {

    private int xPos, yPos;
    private boolean dead = false, won = false;

    public void setXPos(int x) {
        xPos = x;
    }

    public void setYpos(int y) {
        yPos = y;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYpos() {
        return yPos;
    }

    public boolean isDead() { return dead; }

    public void killWarlord() { dead = true; }

    public boolean hasWon() { return won; }

    public void setWon(boolean won) { this.won = won; }
}
