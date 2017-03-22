package warlords2600;

import warlordstest.IWall;

public class Brick implements IWall{

    private int xPos, yPos;
    private boolean destroyed = false;

    public void setXPos(int x) {
        xPos = x;
    }

    public void setYPos(int y) {
        yPos = y;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroyWall() { destroyed = true; }

}
