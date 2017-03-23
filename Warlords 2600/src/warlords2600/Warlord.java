package warlords2600;

import warlordstest.IWarlord;

public class Warlord implements IWarlord {

    public Paddle paddle = new Paddle();
    private int xPos = 0, yPos = 0, height = 5, width = 5;
    private boolean dead = false, won = false;

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

    public boolean isDead() {
        return dead;
    }

    public void killWarlord() {
        dead = true;
    }

    public boolean hasWon() {
        return won;
    }

    public void setWon() {
        this.won = true;
    }
}
