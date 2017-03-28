package warlords2600;

import warlordstest.IWall;

public class Brick implements IWall{

    private int xPos, yPos, height = 25, width = 25, r;
    private double theta;

    public Brick() {
        xPos = 0;
        yPos = 0;
        theta = Math.PI/4;
        r = 10;
    }

    public Brick(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Brick(int r, double theta) {
        this.r = r;
        this.theta = theta;
    }

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

    public void destroyBrick() {
        destroyed = true;
    }

    public int calcXPos() {
        xPos = (int)(r * Math.cos(theta));
        return xPos;
    }

    public int calcYPos() {
        yPos = (int)(r * Math.sin(theta));
        return yPos;
    }

    public double getTheta () {
        return theta;
    }

    public int getR () {
        return r;
    }

}
