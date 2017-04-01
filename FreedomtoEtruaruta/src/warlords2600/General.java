package warlords2600;

import EtruarutaGUI.Main;

public class General implements IObject {

    public Paddle paddle;
    public Brick[][] wall;
    private int xPos, yPos, height = 25, width = 25, r, pos = 0;
    private double theta;

    public General() {
        xPos = 0;
        yPos = 0;
        this.paddle = new Paddle();
    }

    public General(int r, double theta, Paddle paddle, Brick[][] wall, int pos) {
        this.r = r;
        this.theta = theta;
        this.paddle = paddle;
        this.wall = wall;
        this.pos = pos;
    }

    public General(int xPos, int yPos, Paddle paddle) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.paddle = paddle;
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

    public int calcXPos() {
        xPos = (int)(r * Math.cos(theta));
        switch (pos) {
            case 0: return xPos;
            case 1: return (Main.WIDTH - xPos);
            case 2: return (Main.WIDTH - xPos);
            case 3: return xPos;
            default: return xPos;
        }
    }

    public int calcYPos() {
        yPos = (int)(r * Math.sin(theta));
        switch (pos) {
            case 0: return yPos;
            case 1: return yPos;
            case 2: return (Main.HEIGHT - yPos);
            case 3: return (Main.HEIGHT - yPos);
            default: return yPos;
        }
    }

    public double getTheta () {
        return theta;
    }

    public int getR () {
        return r;
    }
}
