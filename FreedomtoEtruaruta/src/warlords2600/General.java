package warlords2600;

import warlordstest.IWarlord;

public class General implements IWarlord {

    public Paddle paddle = new Paddle();
    public Brick[][] wall;
    private int xPos, yPos, height = 5, width = 5;

    public General() {
        xPos = 0;
        yPos = 0;
        this.paddle = new Paddle();
    }

    public General(int xPos, int yPos, Paddle paddle) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.paddle = paddle;
    }

    public General(int xPos, int yPos, Paddle paddle, Brick[][] wall) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.paddle = paddle;
        this.wall = wall;
    }


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

    public void killGeneral() {
        dead = true;
    }

    public boolean hasWon() {
        return won;
    }

    public void setWon() {
        this.won = true;
    }
}
