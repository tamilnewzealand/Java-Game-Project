package warlords2600;

import warlordstest.IWarlord;

public class General implements IWarlord {

    public Paddle paddle = new Paddle();
    public Brick[][] wall = new Brick[3][7];
    private int xPos, yPos, height = 5, width = 5;

    public General() {
        xPos = 0;
        yPos = 0;
        this.paddle = new Paddle();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                wall[i][j] = new Brick((2 + 5*i), (2 + 5*j));
            }
        }
    }

    public General(int xPos, int yPos, Paddle paddle) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.paddle = paddle;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                wall[i][j] = new Brick((2 + 5*i), (2 + 5*j));
            }
        }
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
