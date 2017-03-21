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

    boolean isDead() { return dead; }

    void killWarlord() { dead = true; }

    boolean hasWon() { return won; }

    void setWon(boolean won) { this.won = won; }
}
