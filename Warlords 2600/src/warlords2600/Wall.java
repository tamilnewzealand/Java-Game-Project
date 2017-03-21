package warlords2600;

import warlordstest.IWall;

public class Wall implements IWall{

    private int xPos, yPos;
    private boolean destroyed;

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

    public boolean isDestroyed() {
        return destroyed;
    }

}
