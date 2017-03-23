package warlords2600;

import warlordstest.IWall;

public class Brick implements IWall{

    private int xPos = 0, yPos = 0, height = 5, width = 5;
    private boolean destroyed = false;

    public void setXPos(int x) {
        xPos = x;
    }

    public void setYPos(int y) {
        yPos = y;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroyWall() {
        destroyed = true;
    }

}
